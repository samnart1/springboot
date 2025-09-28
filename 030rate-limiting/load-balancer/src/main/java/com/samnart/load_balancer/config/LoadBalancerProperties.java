package com.samnart.load_balancer.config;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "load-balancer")
public class LoadBalancerProperties {
    
    private ApiGateway apiGateway = new ApiGateway();
    private HealthCheck healthCheck = new HealthCheck();

    public static class ApiGateway {
        private List<Instance> instances;
        
        public static class Instance {
            private String host;
            private int port;
            private int weight = 1;
            private boolean enabled = true;

            public String getHost() { return host; }
            public void setHost(String host) { this.host = host; }
            public int getPort() { return port; }
            public void setPort(int port) { this.port = port; }
            public int getWeight() { return weight; }
            public void setWeight(int weight) { this.weight = weight; }
            public boolean isEnabled() { return enabled; }
            public void setEnabled(boolean enabled) { this.enabled = enabled; }
        }

        public List<Instance> getInstances() { return instances; }
        public void setInstances(List<Instance> instances) { this.instances = instances; }
    }

    public static class HealthCheck {
        private Duration interval = Duration.ofSeconds(30);
        private Duration timeout = Duration.ofSeconds(30);
        private int failureThreshold = 3;
        private int successThreshold = 2;

        public Duration getInterval() { return interval; }
        public void setInterval(Duration interval) { this.interval = interval; }
        public Duration getTimeout() { return timeout; }
        public void setTimeout(Duration timeout) { this.timeout = timeout; }
        public int getFailureThreshold() { return failureThreshold; }
        public void setFailureThreshold(int failureThreshold) { this.failureThreshold = failureThreshold; }
        public int getSuccessThreshold() { return successThreshold; }
        public void setSuccessThreshold(int successThreshold) { this.successThreshold = successThreshold; }
    }

    public ApiGateway getApiGateway() { return apiGateway; }
    public void setApiGateway(ApiGateway apiGateway) { this.apiGateway = apiGateway; }
    public HealthCheck getHealthCheck() { return healthCheck; }
    public void setHealthCheck(HealthCheck healthCheck) { this.healthCheck = healthCheck; }
}
