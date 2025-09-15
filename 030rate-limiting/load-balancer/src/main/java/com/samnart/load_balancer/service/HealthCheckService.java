package com.samnart.load_balancer.service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.samnart.load_balancer.config.LoadBalancerProperties;

@Service
public class HealthCheckService {
    
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckService.class);

    private final WebClient webClient;
    private final LoadBalancerProperties properties;
    private final Map<String, HealthStatus> healthStatusMap = new ConcurrentHashMap<>();

    public HealthCheckService(WebClient.Builder webClientBuilder, LoadBalancerProperties properties) {
        this.webClient = webClientBuilder.build();
        this.properties = properties;
    }

    @Scheduled(fixedDelay = "#{@loadBalancerProperties.healthCheck.interval.toMillis()}")
    public void performHealthChecks() {
        properties.getApiGateway().getInstances().forEach(instance -> {
            String instanceKey = instance.getHost() + ":" + instance.getPort();
            checkInstanceHealth(instanceKey, instance);
        });
    }

    public void checkInstanceHealth(String instanceKey, LoadBalancerProperties.ApiGateway.Instance instance) {
        String healthUrl = String.format("http://%s:%d/actuator/health", instance.getHost(), instance.getPort());

        webClient.get()
            .uri(healthUrl)
            .retrieve()
            .bodyToMono(String.class)
            .timeout(properties.getHealthCheck().getTimeout())
            .doOnSuccess(response -> updateHealthStatus(instanceKey, true))
            .doOnError(error -> {
                logger.warn("Health check failed for instance {}: {}", instanceKey, error.getMessage());
                updateHealthStatus(instanceKey, false);
            })
            .onErrorReturn("")
            .subscribe();
    }

    private void updateHealthStatus(String instanceKey, boolean isHealthy) {
        HealthStatus status = healthStatusMap.computeIfAbsent(instanceKey, k -> new HealthStatus());

        if (isHealthy) {
            status.consecutiveSuccesses++;
            status.consecutiveFailures = 0;
            if (status.consecutiveSuccesses >= properties.getHealthCheck().getSuccessThreshold()) {
                status.isHealthy = true;
            }
        } else {
            status.consecutiveFailures++;
            status.consecutiveSuccesses = 0;
            if (status.consecutiveFailures >= properties.getHealthCheck().getFailureThreshold()) {
                status.isHealthy = false;
            }
        }

        logger.info("Health status for {}: {} (successes: {}, failures: {})", instanceKey, status.isHealthy, status.consecutiveSuccesses, status.consecutiveFailures);
    }

    public boolean isInstanceHealthy(String instanceKey) {
        HealthStatus status = healthStatusMap.get(instanceKey);
        return status = null || status.isHealthy;
    }

    private static class HealthStatus {
        boolean isHealthy = true;
        int consecutiveSuccesses = 0;
        int consecutiveFailures = 0;
    }
}
