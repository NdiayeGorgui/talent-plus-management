package com.gogo.notification_service.controller;

import com.gogo.notification_service.enums.ReceiverType;
import com.gogo.notification_service.model.Notification;
import com.gogo.notification_service.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    // Récupérer toutes les notifications pour un utilisateur
    @GetMapping("/user/{userId}")
    public List<Notification> getNotifications(
            @PathVariable("userId") Long userId,
            @RequestParam("receiverType") ReceiverType type) {
        return service.getNotifications(userId, type);
    }

    // Récupérer les notifications non lues pour un utilisateur
    @GetMapping("/user/{userId}/unread")
    public List<Notification> getUnreadNotifications(
            @PathVariable("userId") Long userId,
            @RequestParam("receiverType") ReceiverType type) {
        return service.getUnreadNotifications(userId, type);
    }

    // Marquer une notification comme lue
    @PatchMapping("/{id}/read")
    public void markAsRead(@PathVariable("id") Long id) {
        service.markAsRead(id);
    }

    // Ajouter une notification manuellement (optionnel)
    @PostMapping
    public Notification save(@RequestBody Notification notification) {
        return service.save(notification);
    }

    // Récupérer toutes les notifications (admin ou debug)
    @GetMapping
    public List<Notification> getAll() {
        return service.getNotifications(null, null); // ou repo.findAll() si besoin
    }
}
