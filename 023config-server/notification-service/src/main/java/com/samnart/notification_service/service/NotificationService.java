package com.samnart.notification_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.samnart.notification_service.model.Notification;
import com.samnart.notification_service.model.Notification.NotificationStatus;
import com.samnart.notification_service.model.Notification.NotificationType;

@Service
public class NotificationService {
    
    private final Map<String, Notification> notifications = new ConcurrentHashMap<>();

    @Value("${app.notification.email.enabled:true}")
    private boolean emailEnabled;

    @Value("${app.notification.sms.enabled:false}")
    private boolean smsEnabled;

    public List<Notification> getAllNotifications() {
        return new ArrayList<>(notifications.values());
    }

    public Optional<Notification> getNoificationById(Long id) {
        return Optional.ofNullable(notifications.get(id));
    }

    public Notification sendNotification(Notification notification) {
        try {
            if (notification.getType() == NotificationType.EMAIL && emailEnabled) {
                sendEmailNotification(notification);

            } else if (notification.getType() == NotificationType.SMS && smsEnabled) {
                sendSMSNotification(notification);
            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setSentAt(LocalDateTime.now());
        }

        notifications.put(notification.getId(), notification);
        return notification;
    }

    private void sendEmailNotification(Notification notification) {
        System.out.println("Sending EMAIL notification to: " + notification.getRecipient());
        System.out.println("Subject: " + notification.getSubject());
        System.out.println("Message: " + notification.getMessage());

        try {
            Thread.sleep(100);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void sendSMSNotification(Notification notification) {

        System.out.println("Sending SMS notification to: " + notification.getRecipient());
        System.out.println("Message: " + notification.getMessage());
        System.out.println();

        try {
            Thread.sleep(50);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public List<Notification> getNotificationsByRecipient(String recipient) {
        return notifications.values().stream()
            .filter(n -> n.getRecipient().equals(recipient))
            .toList();
    }
}
