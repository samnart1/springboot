package com.samnart.load_balancer.service;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Service
public class MetricsService {
    
    private final Counter requestCounter;
    private final Counter errorCounter;
    private final Timer requestTimer;

    public MetricsService(MeterRegistry meterRegistry) {
        this.requestCounter = Counter.builder("loadbalancer_requests_total")
            .description("Total number of requests processed by load balancer")
            .register(meterRegistry);

        this.errorCounter = Counter.builder("loadbalancer_errors_total")
            .description("Total number of errors in load balancer")
            .register(meterRegistry);

        this.requestTimer = Timer.builder("loadbalancer_request_duration")
            .description("Request processing time")
            .register(meterRegistry);
    }

    public void incrementRequestCounter() {
        requestCounter.increment();
    }

    public void incrementErrorCounter() {
        errorCounter.increment();
    }

    public Timer.Sample startTimer() {
        return Timer.start();
    }

    public void stopTimer(Timer.Sample sample) {
        sample.stop(requestTimer);
    }
}
