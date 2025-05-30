package com.jb.online_quiz_app.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.Leaderboard;
import com.jb.online_quiz_app.entity.Question;
import com.jb.online_quiz_app.entity.Quiz;
import com.jb.online_quiz_app.entity.QuizAttempt;
import com.jb.online_quiz_app.entity.StudentAnswer;
import com.jb.online_quiz_app.entity.User;
import com.jb.online_quiz_app.repository.LeaderboardRepository;
import com.jb.online_quiz_app.repository.QuestionRepository;
import com.jb.online_quiz_app.repository.QuizAttemptRepository;
import com.jb.online_quiz_app.repository.QuizRepository;
import com.jb.online_quiz_app.repository.StudentAnswerRepository;
import com.jb.online_quiz_app.repository.UserRepository;

@Service
public class QuizTakingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired 
    private NotificationService notificationService;

    // 3. Quiz Taking (Student)

    /**
     * Start quiz attempt (only once)
     */
    public QuizAttempt startQuizAttempt(String username, Long quizId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not registered."));

        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found."));

        // Check for unsubmitted existing attempt
        Optional<QuizAttempt> existing = quizAttemptRepository
            .findByUserIdAndQuizIdAndSubmittedFalse(user.getId(), quizId);

        if (existing.isPresent()) {
            throw new RuntimeException("Quiz already started. Please continue the attempt.");
        }

        QuizAttempt attempt = QuizAttempt.builder()
            .quiz(quiz)
            .user(user)
            .startTime(LocalDateTime.now())
            .submitted(false)
            .score(0)
            .build();

        return quizAttemptRepository.save(attempt);
    }

    /**
     * Fetch question by index
     */
    public Question getQuestionByIndex(Long quizId, int index, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not registered."));

        quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("Quiz not found."));

        quizAttemptRepository.findByUserIdAndQuizIdAndSubmittedFalse(user.getId(), quizId)
            .orElseThrow(() -> new RuntimeException("You must start the quiz first."));

        List<Question> questions = questionRepository.findByQuizIdOrderByIdAsc(quizId);

        if (index < 0 || index >= questions.size()) {
            throw new RuntimeException("Invalid question index.");
        }

        return questions.get(index);
    }

    /**
     * Save answer or overwrite it if already answered
     */
    public String saveOrSubmitAnswer(Long quizId, String username, Long questionId, String selectedOption) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found."));

        QuizAttempt attempt = quizAttemptRepository.findByUserIdAndQuizIdAndSubmittedFalse(user.getId(), quizId)
            .orElseThrow(() -> new RuntimeException("Start the quiz before submitting."));

        Quiz quiz = attempt.getQuiz();
        LocalDateTime startTime = attempt.getStartTime();
        LocalDateTime endTime = startTime.plusMinutes(quiz.getDurationMinutes());

        if (LocalDateTime.now().isAfter(endTime)) {
            return "Time ended, cannot save answer. Please submit the quiz.";
        }

        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Question not found."));

        if (!question.getQuiz().getId().equals(quizId)) {
            throw new RuntimeException("Question does not belong to the selected quiz.");
        }

        // Save or overwrite answer
        StudentAnswer existingAnswer = studentAnswerRepository
            .findByAttemptIdAndQuestionId(attempt.getId(), questionId)
            .orElse(null);

        if (existingAnswer != null) {
            existingAnswer.setSelectedOption(selectedOption);
            studentAnswerRepository.save(existingAnswer);
        } else {
            StudentAnswer answer = StudentAnswer.builder()
                .attempt(attempt)
                .question(question)
                .selectedOption(selectedOption)
                .build();
            studentAnswerRepository.save(answer);
        }

        return "Answer saved successfully.";
    }

    /**
     * Final quiz submission and evaluation
     */
    public QuizAttempt submitQuizAttemptFinal(String username, Long quizId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        QuizAttempt attempt = quizAttemptRepository.findByUserIdAndQuizIdAndSubmittedFalse(user.getId(), quizId)
            .orElseThrow(() -> new RuntimeException("No active attempt found for this user and quiz"));

        if (attempt.isSubmitted()) {
            throw new RuntimeException("Attempt already submitted");
        }

        List<StudentAnswer> answers = studentAnswerRepository.findByAttemptId(attempt.getId());

        int score = 0;
        for (StudentAnswer ans : answers) {
            if (ans.getQuestion().getCorrectOption().equalsIgnoreCase(ans.getSelectedOption())) {
                score++;
            }
        }

        attempt.setScore(score);
        attempt.setEndTime(LocalDateTime.now());
        attempt.setSubmitted(true);
        quizAttemptRepository.save(attempt);

        // Update leaderboard and notify
        updateLeaderboard(attempt);
        notificationService.sendResultNotification(attempt);

        return attempt;
    }

    //---------------------------------------------------------------------------------------------
    // 4. Evaluation & Leaderboard (link)
    //---------------------------------------------------------------------------------------------

    private void updateLeaderboard(QuizAttempt attempt) {
        int passMark = 5; // Make configurable if needed
        boolean isPassed = attempt.getScore() >= passMark;
        String status = isPassed ? "Pass" : "Fail";

        System.out.println("User: " + attempt.getUser().getUsername() +
                           " | Score: " + attempt.getScore() +
                           " | Status: " + status);

        Leaderboard leaderboardEntry = Leaderboard.builder()
            .quiz(attempt.getQuiz())
            .user(attempt.getUser())
            .score(attempt.getScore())
            .duration(Duration.between(attempt.getStartTime(), attempt.getEndTime()).getSeconds())
            .build();

        leaderboardRepository.save(leaderboardEntry);
    }

    
}
