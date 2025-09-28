package com.samnart.api_gateway.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.samnart.api_gateway.model.RateLimitRule;

import reactor.core.publisher.Mono;

@Service
public class ConfigurationService {
    
    public Mono<List<RateLimitRule>> getAllRules() {
        return Mono.fromSupplier(() -> new ArrayList<>(rulesCache.values()))
            .doOnNext(rules -> logger.debug("Retrieved {} rules from cache", rules.size()));
    }
}
