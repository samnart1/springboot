package com.samnart.api_gateway.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;

@Service
public class MetricsService {
    
    private final Counter requestCounter;
    private final Counter rateLimitExceededCounter;
    private final Counter rateLimitAllowedCounter;
    private final Timer rateLimitCheckTimer;
    private final AtomicLong activeConnections = new AtomicLong(0);
}
