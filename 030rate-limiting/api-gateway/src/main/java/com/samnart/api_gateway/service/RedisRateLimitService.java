package com.samnart.api_gateway.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.samnart.api_gateway.config.RateLimitProperties;
import com.samnart.api_gateway.model.RateLimitRequest;
import com.samnart.api_gateway.model.RateLimitResponse;
import com.samnart.api_gateway.model.RateLimitRule;
import com.samnart.api_gateway.util.KeyGenerator;
import com.samnart.api_gateway.util.RateLimitAlgorithm;

import reactor.core.publisher.Mono;

@Service
public class RedisRateLimitService implements RateLimitService {

    private static final Logger logger = LoggerFactory.getLogger(RedisRateLimitService.class);

    @Autowired
    private ReactiveRedisTemplate<String, Object> redisTemplate; 

    @Autowired
    private RateLimitProperties properties;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private KeyGenerator keyGenerator;

    private final Map<String, RateLimitAlgorithm> algorithms;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public RedisRateLimitService(List<RateLimitAlgorithm> algorithmList) {
        this.algorithms = algorithmList.stream()
            .collect(Collectors.toMap(RateLimitAlgorithm::getAlgorithmName, algo -> algo));
    }

    @Override
    public Mono<RateLimitResponse> checkRateLimit(RateLimitRequest request) {
        if (!properties.isEnabled()) {
            return Mono.just(RateLimitResponse.allowed(Long.MAX_VALUE, System.currentTimeMillis()));
        }

        if (isWhitelistedIp(request.getIpAddress())) {
            return Mono.just(RateLimitResponse.allowed(Long.MAX_VALUE, System.currentTimeMillis()));
        }

        if (isExemptPath(request.getPath())) {
            return Mono.just(RateLimitResponse.allowed(Long.MAX_VALUE, System.currentTimeMillis()));
        }

        return getApplicableRules(request)
            .flatMap(rules -> {
                if (rules.isEmpty()) {
                    return Mono.just(RateLimitResponse.allowed(Long.MAX_VALUE, System.currentTimeMillis()));
                }

                //check rules in priority order (highest priority first)
                return checkRulesSequentially(request, rules);
            });
    }

    public Mono<RateLimitResponse> checkRulesSequentially(RateLimitRequest request, List<RateLimitRule> rules) {
        if (rules.isEmpty()) {
            return Mono.just(RateLimitResponse.allowed(Long.MAX_VALUE, System.currentTimeMillis()));
        }

        RateLimitRule rule = rules.get(0);
        RateLimitAlgorithm algorithm = algorithms.get(rule.getAlgorithm());

        if (algorithm == null) {
            logger.warn("Unknown algorithm: {}, falling back to default", rule.getAlgorithm());
            algorithm = algorithms.get(properties.getDefaultAlgorithm());
        }

        return algorithm.isAllowed(request, rule)
            .flatMap(response -> {
                if (!response.isAllowed()) {
                    //rate limit exceeded for this rule
                    return Mono.just(response);
                } else if (rules.size() > 1) {
                    //check next urle
                    return checkRulesSequentially(request, rules.subList(1, rules.size()));
                } else {
                    //all rules passed
                    return Mono.just(response);
                }
            });
    }

    @Override
    public Mono<List<RateLimitRule>> getApplicableRules(RateLimitRequest request) {
        return configurationService.getAllRules()
            .map(allRules -> {
                List<RateLimitRule> applicableRules = new ArrayList<>();

                for (RateLimitRule rule : allRules) {
                    if (isRuleApplicable(request, rule)) {
                        applicableRules.add(rule);
                    }
                }

                applicableRules.sort((r1, r2) -> Integer.compare(r2.getPriority(), r1.getPriority()));

                if (applicableRules.isEmpty()) {
                    applicableRules.add(createGlobalRule());
                }

                return applicableRules;
            })
            .onErrorReturn(List.of(createGlobalRule()));
    }

    private boolean isRuleApplicable(RateLimitRequest request, RateLimitRule rule) {
        if (!rule.isEnabled()) {
            return false;
        }

        if (rule.getExemptIps() != null && rule.getExemptIps().contains(request.getIpAddress())) {
            return false;
        }

        if (rule.getExemptUsers() != null && request.getUserId() != null && rule.getExemptUsers().contains(request.getUserId())) {
            return false;
        }

        if (rule.getPath() != null && !rule.getPath().isEmpty()) {
            if (!pathMatcher.match(rule.getPath(), request.getPath())) {
                return false;
            }
        }

        if (rule.getMethod() != null && !rule.getMethod().isEmpty()) {
            if (!rule.getMethod().equalsIgnoreCase(request.getMethod())) {
                return false;
            }
        }

        return true;
    }

    private RateLimitRule createGlobalRule() {
        RateLimitRule globalRule = new RateLimitRule();
        globalRule.setId("global-default");
        globalRule.setName("Global Default Rule");
        globalRule.setIdentifier("ip");
        globalRule.setLimit(properties.getGlobalRule().getLimit());
        globalRule.setWindow(properties.getGlobalRule().getWindow());
        globalRule.setAlgorithm(properties.getGlobalRule().getAlgorithm());
        globalRule.setPriority(0);
        return globalRule;
    }

    private boolean isWhitelistedIp(String ipAddress) {
        if (properties.getIpWhitelist() == null || ipAddress == null) {
            return false;
        }

        return properties.getIpWhitelist().stream()
            .anyMatch(whitelistEntry -> ipMatches(ipAddress, whitelistEntry));
    }

    private boolean ipMatches(String ipAddress, String pattern) {
        return pattern.equals(ipAddress) || pattern.equals("*");
    }

    private boolean isExemptPath(String path) {
        if (properties.getExemptPaths() == null || path == null) {
            return false;
        }

        return properties.getExemptPaths().stream()
            .anyMatch(exemptPath -> pathMatcher.match(exemptPath, path));
    }

    @Override
    public Mono<Void> resetRateLimit(String identifier) {
        String pattern = "rate_limit:*:" + identifier + "*";
        return redisTemplate.keys(pattern)    
            .flatMap(redisTemplate::delete)
            .then()
            .doOnSuccess(unused -> logger.info("Reset rate limit for identifier: {}", identifier))
            .doOnError(error -> logger.error("Failed to reset rate limit for identifier: {}", identifier));
    }

    @Override
    public Mono<Long> getRemainingRequests(String identifier) {
        return Mono.just(0L);
    }
    

}
