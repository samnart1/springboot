package com.samnart.api_gateway.model;

import java.util.Map;

public class RateLimitRequest {
    private String clientId;
    private String apiKey;
    private String userAgent;
    private String ipAddress;
    private String path;
    private String method;
    private Map<String, String> headers;
    private String userId;
    private Long timestamp;

    public RateLimitRequest() {
        this.timestamp = System.currentTimeMillis();
    }

    public RateLimitRequest(String clientId, String ipAddress, String path, String method) {
        this();
        this.clientId = clientId;
        this.ipAddress = ipAddress;
        this.path = path;
        this.method = method;
    }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public Map<String, String> getHeaders() { return headers; }
    public void setHeaders(Map<String, String> headers) { this.headers = headers; }
    public String getUserId() { return userId; }
    public void setUsserId(String userId) { this.userId = userId; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}
