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

    String getClientId() { return clientId; }
    void setClientId(String clientId) { this.clientId = clientId; }
    String getApiKey() { return apiKey; }
    void setApiKey(String apiKey) { this.apiKey = apiKey; }
    String getUserAgent() { return userAgent; }
    void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    String getIpAddress() { return ipAddress; }
    void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    String getPath() { return path; }
    void setPath(String path) { this.path = path; }
    String getMethod() { return method; }
    void setMethod(String method) { this.method = method; }
    Map<String, String> getHeaders() { return headers; }
    void setHeaders(Map<String, String> headers) { this.headers = headers; }
    String getUserId() { return userId; }
    void setUsserId(String userId) { this.userId = userId; }
    Long getTimestamp() { return timestamp; }
    void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}
