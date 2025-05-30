package com.jb.online_quiz_app.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.*;
import com.jb.online_quiz_app.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;


    //---------------------------------------------------------------------------------------------
    // 2. Quiz Management (Admin)
    //---------------------------------------------------------------------------------------------

    /**
     * Create a new quiz
     */
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    /**
     * Update an existing quiz by ID
     */
    public Quiz updateQuiz(Long quizId, Quiz updatedQuiz) {
        Quiz existing = getQuizById(quizId);

        existing.setTitle(updatedQuiz.getTitle());
        existing.setDescription(updatedQuiz.getDescription());
        existing.setDurationMinutes(updatedQuiz.getDurationMinutes());
        existing.setMaxAttempts(updatedQuiz.getMaxAttempts());

        // Update questions if provided
        if (updatedQuiz.getQuestions() != null && !updatedQuiz.getQuestions().isEmpty()) {
            existing.getQuestions().clear();
            for (Question q : updatedQuiz.getQuestions()) {
                q.setQuiz(existing);
                existing.getQuestions().add(q);
            }
        }

        return quizRepository.save(existing);
    }

    /**
     * Delete a quiz by ID
     */
    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new RuntimeException("Quiz not found with ID: " + quizId);
        }
        quizRepository.deleteById(quizId);
    }

    /**
     * Retrieve all quizzes
     */
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    /**
     * Retrieve quiz by ID
     */
    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }


}
