package com.jb.online_quiz_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.online_quiz_app.entity.Quiz;
import com.jb.online_quiz_app.entity.QuizAttempt;
import com.jb.online_quiz_app.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> 
{
    List<QuizAttempt> findByUserId(Long userId);
    List<QuizAttempt> findByQuizId(Long quizId);
    List<QuizAttempt> findByQuizIdAndUserId(Long quizId, Long userId);
        // Filter attempts by date range (based on endTime)
    List<QuizAttempt> findByEndTimeBetween(LocalDateTime start, LocalDateTime end);

    // Filter by quiz, user, and date range
    List<QuizAttempt> findByQuizIdAndUserIdAndEndTimeBetween(Long quizId, Long userId, LocalDateTime start, LocalDateTime end);
    Optional<QuizAttempt> findByUserIdAndQuizIdAndSubmittedFalse(Long userId, Long quizId);
    Optional<QuizAttempt> findByUserAndQuizAndEndTimeIsNullAndSubmittedFalse(User user, Quiz quiz);
    List<QuizAttempt> findByUserAndQuiz(User user, Quiz quiz);
}