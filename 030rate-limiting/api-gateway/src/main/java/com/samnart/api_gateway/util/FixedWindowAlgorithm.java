package com.samnart.api_gateway.util;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import com.samnart.api_gateway.model.RateLimitRequest;
import com.samnart.api_gateway.model.RateLimitResponse;
import com.samnart.api_gateway.model.RateLimitRule;

import reactor.core.publisher.Mono;

@Component
public class FixedWindowAlgorithm implements RateLimitAlgorithm {

    @Autowired
    private ReactiveRedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisScript<Long> fixedWindowScript;

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    public Mono<RateLimitResponse> isAllowed(RateLimitRequest request, RateLimitRule rule) {
        String baseKey = keyGenerator.generateKey(request, rule);
        long currentTime = System.currentTimeMillis();
        String windowKey = keyGenerator.generateWindowKey(baseKey, rule, currentTime);

        long windowSizeMs = rule.getWindow().toMillis();
        long limit = rule.getLimit();

        return redisTemplate.execute(fixedWindowScript,
            Arrays.asList(windowKey),
            limit, windowSizeMs, currentTime)
            .cast(Long.class)
            .map(currentCount -> {
                long windowStart = (currentTime / windowSizeMs) * windowSizeMs;
                long windowEnd = windowStart + windowSizeMs;

                if (currentCount <= limit) {
                    RateLimitResponse response = RateLimitResponse.allowed(limit - currentCount, windowEnd);
                    response.setAlgorithm("fixed_window");
                    response.setLimit(limit);
                    response.setWindowSize(windowSizeMs);
                    return response;
                } else {
                    long retryAfter = windowEnd - currentTime;
                    RateLimitResponse response = RateLimitResponse.denied("Fixed window limit exceeded", retryAfter);
                    response.setAlgorithm("fixed_window");
                    response.setLimit(limit);
                    response.setWindowSize(windowSizeMs);
                    return response;
                }
            })
            .onErrorReturn(RateLimitResponse.denied("Redis error", rule.getWindow().toMillis()));
    }

    @Override
    public String getAlgorithmName() {
        return "fixed_window";
    }
    
}
