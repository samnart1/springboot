package com.samnart.api_gateway.model;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class RateLimitRule {
    private String id;
    private String name;
    private String description;
    private String identifier;  // ip, user_id, api_key, endpoint
    private String path;
    private String method;
    private int limit;
    private Duration window;
    private String algorithm;
    private int priority = 0;
    private boolean enabled = true;
    private List<String> exemptIps;
    private List<String> exemptUsers;
    private Map<String, Object> metadata;
    private Long createdAt;
    private Long updatedAt;

    public RateLimitRule() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public RateLimitRule(String identifier, String path, int limit, Duration window, String algorithm) {
        this();
        this.identifier = identifier;
        this.path = path;
        this.limit = limit;
        this.window = window;
        this.algorithm = algorithm;
    }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public void setPath(String path) { this.path = path; }
    public void setMethod(String method) { this.method = method; }
    public void setLimit(int limit) { this.limit = limit; }
    public void setWindow(Duration window) { this.window = window; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setExemptIps(List<String> exemptIps) { this.exemptIps = exemptIps; }
    public void setExemptUsers(List<String> exemptUsers) { this.exemptUsers = exemptUsers; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIdentifier() { return identifier; }
    public String getPath() { return path; }
    public String getMethod() { return method; }
    public int getLimit() { return limit; }
    public Duration getWindow() { return window; }
    public String getAlgorithm() { return algorithm; }
    public int getPriority() { return priority; }
    public boolean isEnabled() { return enabled; }
    public List<String> getExemptIps() { return exemptIps; }
    public List<String> getExemptUsers() { return exemptUsers; }
    public Map<String, Object> getMetadata() { return metadata; }
    public Long getCreatedAt() { return createdAt; }
    public Long getUpdatedAt() { return updatedAt; }
}
