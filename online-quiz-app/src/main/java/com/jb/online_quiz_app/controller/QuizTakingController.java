package com.jb.online_quiz_app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.jb.online_quiz_app.entity.Question;
import com.jb.online_quiz_app.entity.StudentAnswer;
import com.jb.online_quiz_app.entity.User;
import com.jb.online_quiz_app.service.QuizTakingService;
@RestController
@RequestMapping("/api/quizzes")
public class QuizTakingController {
    @Autowired
    private QuizTakingService quizTakingService;
        //////////////////////////////////////////////////////////////////////////////////
    // 3. Quiz Taking (Student) and 4. Evaluation & Result Submission
    //////////////////////////////////////////////////////////////////////////////////

    //START QUIZ
    @PostMapping("/{id}/start")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> startQuiz(@PathVariable("id") Long quizId, Authentication authentication) 
    {
        String response = quizTakingService.startQuiz(quizId, authentication);
        return ResponseEntity.ok(response);
    }

    // Get question 
    @GetMapping("/{quizId}/question/showNext")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Question> showNextQuestion(@PathVariable Long quizId, Principal principal) 
    {
        String username = principal.getName();
        Question question = quizTakingService.getNextQuestion(quizId, username);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/{quizId}/question/showPrevious")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Question> showPreviousQuestion(@PathVariable Long quizId, Principal principal) 
    {
        String username = principal.getName();
        Question question = quizTakingService.getPreviousQuestion(quizId, username);
        return ResponseEntity.ok(question);
    }

    // Enter answer
    @PostMapping("/{quizId}/answer")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> answerQuestion(@PathVariable Long quizId,
                                            @AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody StudentAnswer answerRequest) 
    {
        return quizTakingService.answerQuestion(quizId, userDetails, answerRequest);
    }

    //Submit Quizz
    @PostMapping("/{quizId}/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> submitQuiz(@PathVariable Long quizId,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return quizTakingService.submitQuiz(quizId, userDetails);
    }

    
}
