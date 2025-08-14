package com.samnart.catalog.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponseBuilder {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors;

    public ErrorResponseBuilder Builder(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
