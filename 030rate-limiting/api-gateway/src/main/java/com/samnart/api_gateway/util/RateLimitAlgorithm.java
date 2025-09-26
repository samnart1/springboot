package com.samnart.api_gateway.util;

import com.samnart.api_gateway.model.RateLimitRequest;
import com.samnart.api_gateway.model.RateLimitResponse;
import com.samnart.api_gateway.model.RateLimitRule;

import reactor.core.publisher.Mono;

public interface RateLimitAlgorithm {
    
    Mono<RateLimitResponse> isAllowed(RateLimitRequest request, RateLimitRule rule);

    String getAlgorithmName();
}
