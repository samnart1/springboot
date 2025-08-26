package com.samnart.notification_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.samnart.notification_service.model.Notification;

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
            

        }
    }
}
