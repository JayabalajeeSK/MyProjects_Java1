package com.jb.online_quiz_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.online_quiz_app.entity.Question;
import com.jb.online_quiz_app.service.QuestionService;

@RestController
@RequestMapping("/api/quizzes")
public class QuestionController 
{

    @Autowired
    private QuestionService questionService;

    // Add, Update, Delete Questions (Admin Only)

    // Add question to a quiz
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/questions")
    public ResponseEntity<Question> addQuestion(@PathVariable Long id, @RequestBody Question question) 
    {
        return ResponseEntity.ok(questionService.addQuestionToQuiz(id, question));
    }

    // Update question by ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) 
    {
        return ResponseEntity.ok(questionService.updateQuestion(id, question));
    }

    // Delete question by ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) 
    {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
    
}
