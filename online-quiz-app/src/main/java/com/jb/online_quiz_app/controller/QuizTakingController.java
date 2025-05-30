package com.jb.online_quiz_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.online_quiz_app.entity.Question;
import com.jb.online_quiz_app.entity.Quiz;
import com.jb.online_quiz_app.entity.QuizAttempt;
import com.jb.online_quiz_app.service.QuizTakingService;
@RestController
@RequestMapping("/api/quizzes")
public class QuizTakingController {
    @Autowired
    private QuizTakingService quizTakingService;
        //////////////////////////////////////////////////////////////////////////////////
    // 3. Quiz Taking (Student) and 4. Evaluation & Result Submission
    //////////////////////////////////////////////////////////////////////////////////

    // Start a quiz attempt (Student)
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/{id}/start")
    public ResponseEntity<Map<String, Object>> startQuiz(@PathVariable("id") Long quizId, @RequestBody Map<String, String> body) {
        String username = body.get("username");
        QuizAttempt attempt = quizTakingService.startQuizAttempt(username, quizId);
        Quiz quiz = attempt.getQuiz();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Quiz started. Timer started for " + quiz.getDurationMinutes() + " minutes.");
        response.put("quizTitle", quiz.getTitle());
        response.put("description", quiz.getDescription());
        response.put("durationMinutes", quiz.getDurationMinutes());
        response.put("maxAttempts", quiz.getMaxAttempts());

        return ResponseEntity.ok(response);
    }

    // Get question by index for a particular quiz (Student)
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{quizId}/question/{index}")
    public ResponseEntity<Question> getQuestionByIndex(@PathVariable Long quizId, @PathVariable int index, @RequestParam String username) {
        Question question = quizTakingService.getQuestionByIndex(quizId, index, username);
        return ResponseEntity.ok(question);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/quizzes/{quizId}/questions/{questionId}/answer")
public ResponseEntity<String> saveOrSubmitAnswer(
        @PathVariable Long quizId,
        @PathVariable Long questionId,
        @RequestParam String selectedOption,
        @RequestParam String username) {

    try {
        String response = quizTakingService.saveOrSubmitAnswer(quizId, username, questionId, selectedOption);
        return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

    // Submit quiz attempt (POST)
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/api/quizzes/{quizId}/finalsubmit")
    public ResponseEntity<QuizAttempt> submitAttempt(@PathVariable Long quizId, @RequestParam String username) {
        QuizAttempt attempt = quizTakingService.submitQuizAttemptFinal(username, quizId);
        return ResponseEntity.ok(attempt);
    }

    // Final quiz result (GET) – returns score, pass/fail status
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/api/quizzes/{quizId}/finalsubmit")
    public ResponseEntity<?> finalSubmit(@PathVariable Long quizId, @RequestParam String username) {
        QuizAttempt attempt = quizTakingService.submitQuizAttemptFinal(username, quizId);

        int totalQuestions = attempt.getQuiz().getQuestions().size();
        int passMark = totalQuestions / 2;
        int score = attempt.getScore();
        boolean passed = score >= passMark;

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("quizId", quizId);
        response.put("score", score);
        response.put("totalQuestions", totalQuestions);
        response.put("passMark", passMark);
        response.put("result", passed ? "PASS" : "FAIL");

        return ResponseEntity.ok(response);
    }

    
}
