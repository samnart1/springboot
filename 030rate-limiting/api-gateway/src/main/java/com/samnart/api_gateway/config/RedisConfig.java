package com.samnart.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext.newSerializationContext(stringSerializer);

        RedisSerializationContext<String, Object> context = builder
            .value(jsonSerializer)
            .hashValue(jsonSerializer)
            .hashKey(jsonSerializer)
            .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }
    
    @Bean
    public RedisScript<Long> tokenBucketScript() {
        return RedisScript.of(new ClassPathResource("lua/token_bucket.lua"), Long.class);
    }

    @Bean
    public RedisScript<Long> slidingWindowScript() {
        return RedisScript.of(new ClassPathResource("lua/sliding_window.lua"), Long.class);
    }

    @Bean
    public RedisScript<Long> fixedWindowScript() {
        return RedisScript.of(new ClassPathResource("lua/fixed_window.lua"), Long.class);
    }
}
