package com.jb.online_quiz_app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.QuizAttempt;
import com.jb.online_quiz_app.repository.QuizAttemptRepository;
@Service
public class AttemptService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    // 5. Attempt History

    public List<QuizAttempt> getAttemptsByUser(Long userId) 
    {
        return quizAttemptRepository.findByUserId(userId);
    }

    public List<QuizAttempt> getAttemptsByQuiz(Long quizId) 
    {
        return quizAttemptRepository.findByQuizId(quizId);
    }

    public List<QuizAttempt> getAttemptsByQuizAndUser(Long quizId, Long userId) 
    {
        return quizAttemptRepository.findByQuizIdAndUserId(quizId, userId);
    }

    public List<QuizAttempt> getAttemptsByDateRange(LocalDateTime start, LocalDateTime end) 
    {
        return quizAttemptRepository.findByEndTimeBetween(start, end);
    }

    public List<QuizAttempt> getAttemptsFiltered(Long quizId, Long userId, LocalDateTime start, LocalDateTime end) 
    {
        return quizAttemptRepository.findByQuizIdAndUserIdAndEndTimeBetween(quizId, userId, start, end);
    }
    
}
