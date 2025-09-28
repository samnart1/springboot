package com.samnart.api_gateway.util;

import org.springframework.stereotype.Component;

import com.samnart.api_gateway.model.RateLimitRequest;
import com.samnart.api_gateway.model.RateLimitRule;

@Component
public class KeyGenerator {
    
    private static final String KEY_PREFIX = "rate_limit";

    public String generateKey(RateLimitRequest request, RateLimitRule rule) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(KEY_PREFIX).append(":");
        keyBuilder.append(rule.getAlgorithm()).append(":");

        switch (rule.getIdentifier().toLowerCase()) {
            case "ip":
                keyBuilder.append("ip:").append(request.getIpAddress());
                break;
            case "user_id":
                keyBuilder.append("user:").append(request.getUserId());
                break;
            case "api_key":
                keyBuilder.append("api_key:").append(request.getApiKey());
                break;
            case "endpoint":
                keyBuilder.append("endpoint:").append(request.getPath());
                break;
            case "global":
                keyBuilder.append("global");
                break;
            default:
                keyBuilder.append("custom").append(rule.getIdentifier());
        }

        if (rule.getPath() != null && !rule.getPath().isEmpty()) {
            keyBuilder.append(":").append(rule.getPath().replaceAll("^a-zA-Z0-9", "_"));
        }

        if (rule.getMethod() != null && !rule.getMethod().isEmpty()) {
            keyBuilder.append(":").append(rule.getMethod());
        }
        
        return keyBuilder.toString();
    }

    public String generateWindowKey(String baseKey, RateLimitRule rule, long timestamp) {
        long windowStart = getWindowStart(timestamp, rule.getWindow().toMillis());
        return baseKey + ":" + windowStart;
    }

    public long getWindowStart(long timestamp, long windowSizeMs) {
        return (timestamp / windowSizeMs) * windowSizeMs;
    }

    public String generateTokenBucketKey(String baseKey) {
        return baseKey + ":bucket";
    }

    public String generateSlidingWindowKey(String baseKey) {
        return baseKey + ":sliding";
    }
}
