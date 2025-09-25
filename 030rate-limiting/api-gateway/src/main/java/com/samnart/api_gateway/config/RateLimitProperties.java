package com.samnart.api_gateway.config;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rate-limiting")
public class RateLimitProperties {
    
    private boolean enabled = true;
    private String defaultAlgorithm = "token_bucket";
    private GlobalRule globalRule = new GlobalRule();
    private Map<String, EndpointRule> endpointRules;
    private Map<String, ClientRule> clientRules;
    private List<String> ipWhitelist;
    private List<String> exemptPaths;

    public static class GlobalRule {
        private int limit = 1000;
        private Duration window = Duration.ofHours(1);
        private String algorithm = "token_bucket";

        private int getLimit() { return limit; }
        private void setLimit(int limit) { this.limit = limit; }
        private Duration getWindow() { return window; }
        private void setWindow(Duration window) { this.window = window; }
        private String getAlgorithm() { return algorithm; }
        private void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    }

    public static class EndpointRule {
        private int limit;
        private Duration window;
        private String algorithm;
        private Map<String, Integer> methodLimits;

        private int getLimit() { return limit; }
        private void setLimit(int limit) { this.limit = limit; }
        private Duration getWindow() { return window; }
        private void setWindow(Duration window) { this.window = window; }
        private Map<String, Integer> getMethodLimits() { return methodLimits; }
        private void setMethodLimits(Map<String, Integer> methodLimits) { this.methodLimits = methodLimits; }
        private String getAlgorithm() { return algorithm; }
        private void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    }

    public static class ClientRule {
        private int limit;
        private Duration window;
        private String algorithm;
        private int priority = 0; 

        public int getLimit() { return limit; }
        public void setLimit(int limit) { this.limit = limit; }
        public Duration getWindow() { return window; }
        public void setWindow(Duration window) { this.window = window; }
        public int getPriority() { return priority; }
        public void setPriority(int priority) { this.priority = priority; }
        public String getAlgorithm() { return algorithm; }
        public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getDefaultAlgorithm() { return defaultAlgorithm; }
    public void setDefaultAlgorithm(String defaultAlgorithm) { this.defaultAlgorithm = defaultAlgorithm; }
    public GlobalRule getGlobalRule() { return globalRule; }
    public void setGlobalRule(GlobalRule globalRule) { this.globalRule = globalRule; }
    public Map<String, EndpointRule> getEndpointRules() { return endpointRules; }
    public void setEndpointRules(Map<String, EndpointRule> endpointRules) { this.endpointRules = endpointRules; }
    public Map<String, ClientRule> getClientRules() { return clientRules; }
    public void setClientRules(Map<String, ClientRule> clientRules) { this.clientRules = clientRules; }
    public List<String> getIpWhitelist() { return ipWhitelist; }
    public void setIpWhilelist(List<String> ipWhitelist) { this.ipWhitelist = ipWhitelist; }
    public List<String> getExemptPaths() { return exemptPaths; }
    public void setExemptPaths(List<String> exemptPaths) { this.exemptPaths = exemptPaths; }
}
