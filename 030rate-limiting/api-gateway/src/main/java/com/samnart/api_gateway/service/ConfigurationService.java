package com.samnart.api_gateway.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.samnart.api_gateway.config.RateLimitProperties;
import com.samnart.api_gateway.model.RateLimitRule;

import reactor.core.publisher.Mono;

@Service
public class ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

    @Autowired
    private RateLimitProperties properties;

    private final WebClient webClient;
    private final Map<String, RateLimitRule> rulesCache = new ConcurrentHashMap<>();
    private volatile long lastUpdated = 0;

    private ConfigurationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        loadInitialConfiguration();
    }
    
    public Mono<ArrayList<RateLimitRule>> getAllRules() {
        return Mono.fromSupplier(() -> new ArrayList<>(rulesCache.values()))
            .doOnNext(rules -> logger.debug("Retrieved {} rules from cache", rules.size()));
    }

    public Mono<RateLimitRule> getRuleById(String ruleId) {
        return Mono.fromSupplier(() -> rulesCache.get(ruleId));
    }

    @Scheduled(fixedDelay = 60000) // every-minute
    public void refreshConfiguration() {
        logger.debug("Refreshing rate limit configuration");

        loadConfigurationFromService()
            .doOnSuccess(rules -> {
                rulesCache.clear();
                rules.forEach(rule -> rulesCache.put(rule.getId(), rule));
                lastUpdated = System.currentTimeMillis();
                logger.info("Successfully refreshed {} rate limit rules", rules.size());
            })
            .doOnError(error -> logger.error("Failed to refresh configuration", error))
            .subscribe();
    }

    private void loadInitialConfiguration() {
        loadDefaultRules();

        loadConfigurationFromService()
            .doOnSuccess(rules -> {
                if (!rules.isEmpty()) {
                    rulesCache.clear();
                    rules.forEach(rule -> rulesCache.put(rule.getId(), rule));
                    logger.info("Loaded {} rules from configuration service", rules.size());
                }
            })
            .doOnError(error -> logger.warn("Failed to load configuration from service, using defaults", error))
            .subscribe();
    }

    private void loadDefaultRules() {
        //create rules from properties
        if (properties.getEndpointRules() != null) {
            properties.getEndpointRules().forEach((path, endpointRule) -> {
                RateLimitRule rule = new RateLimitRule();
                rule.setId("endpoint-" + path.hashCode());
                rule.setName("Endpoint rule for " + path);
                rule.setIdentifier("endpoint");
                rule.setPath(path);
                rule.setLimit(endpointRule.getLimit());
                rule.setWindow(endpointRule.getWindow());
                rule.setAlgorithm(endpointRule.getAlgorithm());
                rule.setPriority(10);

                rulesCache.put(rule.getId(), rule);
            });
        }
    }

    private Mono<List<RateLimitRule>> loadConfigurationFromService() {
        return webClient.get()
            .uri("lb://rate-limit-config-service/api/rules")
            .retrieve()
            .bodyToFlux(RateLimitRule.class)
            .collectList()
            .timeout(Duration.ofSeconds(10))
            .onErrorReturn(new ArrayList<>());
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public int getRulesCount() {
        return rulesCache.size();
    }
}
