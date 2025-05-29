package com.jb.online_quiz_app.service;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.Notification;
import com.jb.online_quiz_app.entity.Quiz;
import com.jb.online_quiz_app.entity.QuizAttempt;
import com.jb.online_quiz_app.entity.User;
import com.jb.online_quiz_app.repository.NotificationRepository;
import com.jb.online_quiz_app.repository.QuizRepository;
import com.jb.online_quiz_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService 
{

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

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

    // // Scheduled reminder for quizzes ending soon (e.g., 24 hours before deadline)
    // @Scheduled(cron = "0 0 9 * * ?") // every day at 9 AM server time
    // public void sendQuizDeadlineReminders() {
    //     LocalDateTime now = LocalDateTime.now();
    //     LocalDateTime in24Hours = now.plusHours(24);

    //     List<Quiz> quizzesEndingSoon = quizRepository.findByDeadlineBetween(now, in24Hours);

    //     for (Quiz quiz : quizzesEndingSoon) {
    //         List<User> enrolledUsers = userRepository.findUsersEnrolledInQuiz(quiz.getId()); 
    //         // Implement this method or adapt based on your user-quiz relationship

    //         String message = String.format("Reminder: Quiz '%s' ends on %s. Submit your attempt soon!",
    //                 quiz.getTitle(),
    //                 quiz.getDeadline().toString());

    //         for (User user : enrolledUsers) {
    //             saveAndSendNotification(user, message, "REMINDER");
    //         }
    //     }
    // }

    // Helper method to save notification and send email
    private void saveAndSendNotification(User user, String message, String type) 
    {
        // Save notification in DB
        Notification notification = Notification.builder()
                                                .user(user)
                                                .message(message)
                                                .type(type)
                                                .sentAt(LocalDateTime.now())
                                                .build();

        notificationRepository.save(notification);

        // Send email
        if (user.getUsername() != null && !user.getUsername().isEmpty()) 
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(user.getUsername());  // Use email field here
            mailMessage.setSubject("Quiz Notification: " + type);
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        }
    }

    // Scheduled task to announce new quizzes created in last 24 hours
    @Scheduled(cron = "0 0 10 * * ?")  // every day at 10:00 AM
    public void scheduledNotifyNewQuizzes() 
    {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        // Find quizzes created in last 24 hours - make sure to add 'createdAt' field in Quiz entity
        List<Quiz> newQuizzes = quizRepository.findByCreatedAtBetween(yesterday, now);

        for (Quiz quiz : newQuizzes) 
        {
            notifyNewQuiz(quiz);
        }
    }
}