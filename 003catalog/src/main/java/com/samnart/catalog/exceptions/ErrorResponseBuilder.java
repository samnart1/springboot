package com.samnart.catalog.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class ErrorResponseBuilder {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors;

    public ErrorResponseBuilder timestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ErrorResponseBuilder status(int status) {
        this.status = status;
        return this;
    }

    public ErrorResponseBuilder error(String error) {
        this.error = error;
        return this;
    }

    public ErrorResponseBuilder message(String message) {
        this.message = message; 
        return this;
    }

    public ErrorResponseBuilder validationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
        return this;
    }

    public ErrorResponse build() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(this.timestamp);
        errorResponse.setStatus(this.status);
        errorResponse.setError(this.error);
        errorResponse.setMessage(this.message);
        errorResponse.setValidationErrors(this.validationErrors);
        return errorResponse;
    }
}
