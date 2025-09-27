package com.samnart.api_gateway.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.ConfigurationService;
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

                for (RateLimitRule rule : allrules) {
                    if (isRuleApplication(request, rules)) {
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

    @Override
    public Mono<Void> resetRateLimit(String identifier) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetRateLimit'");
    }

    @Override
    public Mono<Long> getRemainingRequests(String identifier) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRemainingRequests'");
    }
    

}
