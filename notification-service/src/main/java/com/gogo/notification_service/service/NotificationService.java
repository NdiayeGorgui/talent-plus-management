package com.gogo.notification_service.service;

import com.gogo.notification_service.enums.ReceiverType;
import com.gogo.notification_service.model.Notification;
import com.gogo.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repo;

    public Notification save(Notification notification) {
        return repo.save(notification);
    }

    public List<Notification> getNotifications(Long userId, ReceiverType type) {
        return repo.findByReceiverIdAndReceiverType(userId, type);
    }

    public List<Notification> getUnreadNotifications(Long userId, ReceiverType type) {
        return repo.findByReceiverIdAndReceiverTypeAndIsReadFalse(userId, type);
    }

    public void markAsRead(Long id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
        n.setRead(true);
        repo.save(n);
    }

    public List<Notification> getAllNotifications() {
        return repo.findAll();
    }

}
