package com.samnart.api_gateway.util;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import com.samnart.api_gateway.model.RateLimitRequest;
import com.samnart.api_gateway.model.RateLimitResponse;
import com.samnart.api_gateway.model.RateLimitRule;

import reactor.core.publisher.Mono;

public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    @Autowired
    private ReactiveRedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisScript<Long> tokenBucketScript;

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    public Mono<RateLimitResponse> isAllowed(RateLimitRequest request, RateLimitRule rule) {
        String key = keyGenerator.generateTokenBucketKey(keyGenerator.generateKey(request, rule));

        long capacity = rule.getLimit();
        long refillRate = calculateRefillRate(rule.getLimit(), rule.getWindow());
        long currentTime = System.currentTimeMillis();

        // return redisTemplate.execute(null, null)
        // return redisTemplate.execute(null, false)

        return redisTemplate.execute(tokenBucketScript,
            Arrays.asList(key),
            capacity, refillRate, currentTime, 1L)
            .cast(Long.class)
            .map(remainingTokens -> {
                if (remainingTokens >= 0) {
                    RateLimitResponse response = RateLimitResponse.allowed(remainingTokens, currentTime + rule.getWindow().toMillis());
                    response.setAllgorithm("token_bucket");
                    response.setLimit(capacity);
                    return response;
                } else {
                    long retryAfter = calculateRetryAfter(refillRate);
                    RateLimitResponse resonse = RateLimitResponse.denied("Rate limit exceeded", retryAfter);
                    response.setAllgorithm("token_bucket");
                    response.setLimit(capacity);
                    return response;
                }
            })
            .onErrorReturn(RateLimitResponse.denied("Redis error", Duration.ofMinutes(1).toMillis()));
    }

    private long calculateRefillRate(int limit, Duration window) {
        return Math.max(1, limit * 1000 / window.toMillis());
    }

    private long calculateRetryAfter(long refillRate) {
        return Math.max(1000, 1000 / refillRate);   
    }

    @Override
    public String getAlgorithmName() {
        return "token_bucket";
    }
    
}
