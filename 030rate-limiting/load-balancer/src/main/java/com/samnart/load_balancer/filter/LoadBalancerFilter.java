package com.samnart.load_balancer.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.samnart.load_balancer.service.MetricsService;

import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.Mono;

@Component
public class LoadBalancerFilter implements GlobalFilter, Ordered {
    
    private static final Logger logger = LoggerFactory.getLogger(LoadBalancerFilter.class);

    private MetricsService metricsService;

    public LoadBalancerFilter(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Timer.Sample sample = metricsService.startTimer();

        logger.info("Load balancing request: {} {}", request.getMethod(), request.getPath());

        // add custom headers
        ServerHttpRequest modifiedRequest = request.mutate()
            .header("X-Load-Balancer", "custom-load-balancer")
            .header("X-Request-Time", String.valueOf(System.currentTimeMillis()))
            .build();

        metricsService.incrementRequestCounter();

        return chain.filter(exchange.mutate().request(modifiedRequest).build())
            .doOnSuccess(aVoid -> {
                metricsService.stopTimer(sample);
                logger.info("Request completed successfully: {} {}", request.getMethod(), request.getPath());
            })
            .doOnError(throwable -> {
                metricsService.incrementErrorCounter();
                metricsService.stopTimer(sample);
                logger.error("Request failed: {} {} - Error: {}", request.getMethod(), request.getPath(), throwable.getMessage());
            });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
