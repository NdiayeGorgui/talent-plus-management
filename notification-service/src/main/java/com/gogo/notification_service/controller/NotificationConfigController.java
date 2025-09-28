package com.gogo.notification_service.controller;

import com.gogo.notification_service.model.NotificationConfig;
import com.gogo.notification_service.repository.NotificationConfigRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/notifications/config")
public class NotificationConfigController {
    private final NotificationConfigRepository repo;

    public NotificationConfigController(NotificationConfigRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<NotificationConfig> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public NotificationConfig save(@RequestBody NotificationConfig config) {
        return repo.save(config);
    }
}

