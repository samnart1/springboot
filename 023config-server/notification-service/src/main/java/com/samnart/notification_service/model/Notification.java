package com.samnart.notification_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class Notification {
    private String id;
    private String recipient;
    private String subject;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    public Notification() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
    }

    public Notification(String recipient, String subject, String message, NotificationType type) {
        this();
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.type = type;
    }

    enum NotificationType {
        EMAIL, SMS, PUSH
    }

    enum NotificationStatus {
        PENDING, SENT, FAILED, DELIVERED
    }
}
