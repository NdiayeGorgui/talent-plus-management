package com.gogo.notification_service.repository;

import com.gogo.notification_service.model.NotificationConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationConfigRepository extends JpaRepository<NotificationConfig, Long> {
}
