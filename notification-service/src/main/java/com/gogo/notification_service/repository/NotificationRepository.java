package com.gogo.notification_service.repository;

import com.gogo.notification_service.enums.ReceiverType;
import com.gogo.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverIdAndReceiverType(Long receiverId, ReceiverType type);

    List<Notification> findByReceiverIdAndReceiverTypeAndIsReadFalse(Long receiverId, ReceiverType type);

}
