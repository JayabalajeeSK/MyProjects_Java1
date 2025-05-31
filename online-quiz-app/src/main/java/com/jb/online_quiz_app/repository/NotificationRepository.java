package com.jb.online_quiz_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.online_quiz_app.entity.Notification;
import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> 
{
    List<Notification> findByUserIdOrderBySentAtDesc(Long userId);
    List<Notification> findByUser_Username(String username);
}