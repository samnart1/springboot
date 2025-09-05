package com.samnart.load_balancer.config;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "load-balancer")
public class LoadBalancerProperties {
    
    private ApiGateway apiGateway = new ApiGateway();
    private HealthCheck healthCheck = new HealthCheck();

    @Data
    public static class ApiGateway {
        private List<Instance> instances;
        
        @Data
        public static class Instance {
            private String host;
            private int port;
            private int weight = 1;
            private boolean enabled = true;
        }
    }

    @Data
    public static class HealthCheck {
        private Duration interval = Duration.ofSeconds(30);
        private Duration timeout = Duration.ofSeconds(30);
        private int failureThreshold = 3;
        private int successThreshold = 2;
    }
}
