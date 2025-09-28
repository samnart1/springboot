package com.samnart.api_gateway.util;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import com.samnart.api_gateway.model.RateLimitRequest;
import com.samnart.api_gateway.model.RateLimitResponse;
import com.samnart.api_gateway.model.RateLimitRule;

import reactor.core.publisher.Mono;

@Component
public class SlidingWindowAlgorithm implements RateLimitAlgorithm {
    
    @Autowired
    private ReactiveRedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisScript<Long> slidingWindowScript;

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    public Mono<RateLimitResponse> isAllowed(RateLimitRequest request, RateLimitRule rule) {
        String key = keyGenerator.generateSlidingWindowKey(keyGenerator.generateKey(request, rule));

        long currentTime = System.currentTimeMillis();
        long windowSizeMs = rule.getWindow().toMillis();
        long limit = rule.getLimit();
        long windowStart = currentTime - windowSizeMs;

        return redisTemplate.execute(slidingWindowScript,
            Arrays.asList(key)
            windowStart, currentTime, limit)
            .cast(Long.class)
            .map(currentCount -> {
                if (currentCount <= limit) {
                    RateLimitResponse response = RateLimitResponse.allowed(limit - currentCount, currentTime + windowSizeMs);
                    response.setAlgorithm("sliding_window");
                    response.setLimit(limit);
                    response.setWindowSize(windowSizeMs);
                    return response;
                } else {
                    // for sliding window, retry after is estimated.
                    long retryAfter = windowSizeMs / limit;
                    RateLimitResponse response = RateLimitResponse.denied("sliding window limit exceeded", retryAfter);
                    response.setAlgorithm("sliding_window");
                    response.setLimit("limit");
                    response.setWindowSize(windowSizeMs);
                    return response;
                }
            })
            .onErrorReturn(RateLimitResponse.denied("Redis error", rule.getWindow().toMillis()));
    }

    @Override
    public String getAlgorithmName() {
        return "sliding_window";
    }


}
