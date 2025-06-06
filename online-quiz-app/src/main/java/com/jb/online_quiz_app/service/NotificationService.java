package com.jb.online_quiz_app.service;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.Notification;
import com.jb.online_quiz_app.entity.Quiz;
import com.jb.online_quiz_app.entity.QuizAttempt;
import com.jb.online_quiz_app.entity.User;
import com.jb.online_quiz_app.repository.NotificationRepository;
// import com.jb.online_quiz_app.repository.QuizRepository;
import com.jb.online_quiz_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService 
{

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    // private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    

    public List<Notification> getNotificationsByUsername(String username) 
    {
        return notificationRepository.findByUser_Username(username);
    }

    // Send quiz result notification & email
    public void sendResultNotification(QuizAttempt attempt) 
    {
        String message = String.format("Dear %s, you scored %d in quiz '%s'.",
                attempt.getUser().getUsername(), // ideally use getEmail() if username != email
                attempt.getScore(),
                attempt.getQuiz().getTitle());

        saveAndSendNotification(attempt.getUser(), message, "RESULT");
    }

    // Notify users about a new quiz
    public void notifyNewQuiz(Quiz quiz) 
    {
        String message = String.format("New quiz '%s' is now available! Don't miss out.",
                quiz.getTitle());

        List<User> users = userRepository.findAll(); // or only active users/students

        for (User user : users) 
        {
            saveAndSendNotification(user, message, "ANNOUNCEMENT");
        }
    }


    public void saveAndSendNotification(User user, String message, String type) 
    {
        Notification notification = Notification.builder()
                                                .user(user)
                                                .message(message)
                                                .type(type)
                                                .sentAt(LocalDateTime.now())
                                                .build();

        notificationRepository.save(notification);

        // send email
        if (user.getUsername() != null && !user.getUsername().isEmpty()) 
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(user.getUsername());  // Use email field here
            mailMessage.setSubject("Quiz Notification: " + type);
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        }
    }

}