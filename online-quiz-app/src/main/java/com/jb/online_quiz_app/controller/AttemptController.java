package com.jb.online_quiz_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.online_quiz_app.entity.QuizAttempt;
import com.jb.online_quiz_app.service.AttemptService;

@RestController
@RequestMapping("/api/quizzes")
public class AttemptController 
{
    @Autowired
    private AttemptService attemptService;

    // 5. Attempt History (Admin & Student)

    // Get attempts (by quiz, user, or both)
    @GetMapping("/attempts")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<List<QuizAttempt>> getAttemptsByUser(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long quizId) {

        if (userId != null && quizId != null) {
            return ResponseEntity.ok(attemptService.getAttemptsByQuizAndUser(quizId, userId));
        } else if (userId != null) {
            return ResponseEntity.ok(attemptService.getAttemptsByUser(userId));
        } else if (quizId != null) {
            return ResponseEntity.ok(attemptService.getAttemptsByQuiz(quizId));
        } else {
            return ResponseEntity.badRequest().build(); // No parameters passed
        }
    }
    
}
