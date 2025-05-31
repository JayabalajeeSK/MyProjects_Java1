package com.jb.online_quiz_app.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jb.online_quiz_app.entity.Quiz;
import com.jb.online_quiz_app.service.QuizService;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // 2. Quiz Management (Accessible only by ADMIN)

    // Create a new quiz
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) 
    {
        return ResponseEntity.ok(quizService.createQuiz(quiz));
    }

    // Update an existing quiz by ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) 
    {
        return ResponseEntity.ok(quizService.updateQuiz(id, quiz));
    }

    // Delete a quiz by ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) 
    {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    // Get all quizzes -public
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<List<Quiz>> getAllQuizzes() 
    {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    // Get a quiz by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) 
    {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }
}
