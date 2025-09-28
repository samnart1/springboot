package com.samnart.api_gateway.model;

public class RateLimitResponse {

    private boolean allowed;
    private long remainingRequests;
    private long resetTime;
    private long retryAfter;
    private String algorithm;
    private String reason;
    private long limit;
    private long windowSize;

    public RateLimitResponse() {}
     
    public RateLimitResponse(boolean allowed, long remainingRequests, long resetTime) {
        this.allowed = allowed;
        this.remainingRequests = remainingRequests;
        this.resetTime = resetTime;
    }

    public static RateLimitResponse allowed(long remainingRequests, long resetTime) {
        return new RateLimitResponse(true, remainingRequests, resetTime);
    }

    public static RateLimitResponse denied(String reason, long retryAfter) {
        RateLimitResponse response = new RateLimitResponse(false, 0, 0);
        response.setReason(reason);
        response.setRetryAfter(retryAfter);
        return response;
    }

    public void setAllowed(boolean allowed) { this.allowed = allowed; }
    public void setRemainingRequests(long remainingRequests) { this.remainingRequests = remainingRequests; }
    public void setResetTime(long resetTime) { this.resetTime = resetTime; }
    public void setRetryAfter(long retryAfter) { this.retryAfter = retryAfter; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public void setReason(String reason) { this.reason = reason; }
    public void setLimit(long limit) { this.limit = limit; }
    public void setWindowSize(long windowSize) { this.windowSize = windowSize; }
    public boolean isAllowed() { return allowed; }
    public long getRemainingRequests() { return remainingRequests; }
    public long getResetTime() { return resetTime; }
    public long getRetryAfter() { return retryAfter; }
    public String getAlgorithm() { return algorithm; }
    public String getReason() { return reason; }
    public long getLimit() { return limit; }
    public long getWindowSize() { return windowSize; }
}
