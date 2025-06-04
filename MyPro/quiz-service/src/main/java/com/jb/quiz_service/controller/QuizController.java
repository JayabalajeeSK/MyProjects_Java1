package com.jb.quiz_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.jb.quiz_service.entity.QuestionWrapper;
import com.jb.quiz_service.entity.Quiz;
import com.jb.quiz_service.entity.QuizDto;
import com.jb.quiz_service.entity.Response;
import com.jb.quiz_service.service.QuizService;

@RestController
@RequestMapping("quiz")
public class QuizController 
{
    @Autowired
    private QuizService quizService;

    @PostMapping("/createQuiz")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto)
    {
        return new ResponseEntity<>(quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle()), HttpStatus.CREATED);
    }

    @GetMapping("/getQuizQuestions/{id}")
    public ResponseEntity<List<QuestionWrapper>> getAllQuestionFromQuiz(@PathVariable Long id) 
    {
        return ResponseEntity.ok(quizService.getAllQuestionFromQuiz(id));
    }


    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Long id, @RequestBody List<Response> responses)
    {
        return ResponseEntity.ok(quizService.calculateQuizResult(id, responses));
        //return quizService.calculateQuizResult(id, responses);
    }
}