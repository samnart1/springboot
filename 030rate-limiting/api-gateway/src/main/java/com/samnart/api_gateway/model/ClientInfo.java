package com.samnart.api_gateway.model;

public class ClientInfo {
    private String clientId;
    private String clientType; // premium, basic, free
    private String ipAddress;
    private String userAgent;
    private String userId;
    private String apiKey;
    private boolean authenticated;
    private Long firstSeenAt;
    private Long lastSeenAt;

    public ClientInfo() {
        this.firstSeenAt = System.currentTimeMillis();
        this.lastSeenAt = System.currentTimeMillis();
    }

    public ClientInfo(String clientId, String ipAddress) {
        this();
        this.clientId = clientId;
        this.ipAddress = ipAddress;
    }

    void setClientId(String clientId) { this.clientId = clientId; }
    void setClientType(String clientType) { this.clientType = clientType; }
    void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    void setUserId(String userId) { this.userId = userId; }
    void setApiKey(String apiKey) { this.apiKey = apiKey; }
    void setAuthenticated(boolean authenticated) { this.authenticated = authenticated; }
    void setFirstSeenAt(Long firstSeenAt) { this.firstSeenAt = firstSeenAt; }
    void setLastSeenAt(Long lastSeenAt) { this.lastSeenAt = lastSeenAt; }
    String getClientId() { return clientId; }
    String getClientyType() { return clientType; }
    String getIpAddress() { return ipAddress; }
    String getUserAgent() { return userAgent; }
    String getUserId() { return userId; }
    String getApiKey() { return apiKey; }
    boolean isAuthenticated() { return authenticated; }
    Long getFirstSeenAt() { return firstSeenAt; }
    Long getLastSeenAt() { return lastSeenAt; }
}
