package com.samnart.api_gateway.service;

import java.util.List;

import com.samnart.api_gateway.model.RateLimitRequest;
import com.samnart.api_gateway.model.RateLimitResponse;
import com.samnart.api_gateway.model.RateLimitRule;

import reactor.core.publisher.Mono;

public interface RateLimitService {
    Mono<RateLimitResponse> checkRateLimit(RateLimitRequest request);
    Mono<List<RateLimitRule>> getApplicableRules(RateLimitRequest request);
    Mono<Void> resetRateLimit(String identifier);
    Mono<Long> getRemainingRequests(String identifier);
}
