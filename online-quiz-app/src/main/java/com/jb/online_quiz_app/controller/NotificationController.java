package com.jb.online_quiz_app.controller;


import com.jb.online_quiz_app.entity.Notification;
import com.jb.online_quiz_app.entity.User;
import com.jb.online_quiz_app.repository.UserRepository;
import com.jb.online_quiz_app.service.NotificationService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController 
{

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserRepository userRepository;

    // POST /api/notifications/send
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) 
    {
        User user = notification.getUser();

        // Validate user exists in DB by username
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isEmpty()) 
        {
            return ResponseEntity.badRequest().body("User with username " + user.getUsername() + " not found");
        }

        // Use full user entity from DB
        User persistedUser = optionalUser.get();

        notificationService.saveAndSendNotification(persistedUser,
                notification.getMessage(),
                notification.getType());

        return ResponseEntity.ok("Notification sent successfully");
    }

    // GET /api/notifications?username=someuser@example.com
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(@RequestParam String username) 
    {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) 
        {
            return ResponseEntity.badRequest().build();
        }

        List<Notification> notifications = notificationService.getNotificationsByUsername(username);

        return ResponseEntity.ok(notifications);
    }
}