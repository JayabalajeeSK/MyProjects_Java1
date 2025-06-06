package com.jb.online_quiz_app.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // 3. Quiz Taking (Student)

    /**
     * Start quiz attempt (only once)
     */
    public String startQuiz(Long quizId, Authentication authentication) 
    {
        if (authentication == null || !authentication.isAuthenticated()) 
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to start the quiz.");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found."));

        // Create a new QuizAttempt
        QuizAttempt quizAttempt = QuizAttempt.builder()
                .user(user)
                .quiz(quiz)
                .startTime(LocalDateTime.now())
                .submitted(false)
                .score(0)
                .build();

        quizAttemptRepository.save(quizAttempt);

        return "Quiz started successfully at: " + quizAttempt.getStartTime();
    }

    /**
     * Fetch question ONE BY ONE BASED ON QUIZ
     */
    private Map<String, Integer> userQuestionIndexMap = new HashMap<>(); // Key = userId:quizId, Value = index
////////////////////////////
    public Question getNextQuestion(Long quizId, String username) 
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizAttempt attempt = quizAttemptRepository.findByUserAndQuizAndEndTimeIsNullAndSubmittedFalse(user, quiz)
                .orElseThrow(() -> new RuntimeException("You haven’t started this quiz or already submitted"));

        if (isTimeOver(attempt)) {
            throw new RuntimeException("Time ended! Submit your answers.");
        }

        List<Question> quizQuestions = questionRepository.findByQuizOrderByIdAsc(quiz); // Only quiz-specific
        String sessionKey = user.getId() + ":" + quizId;
        int index = userQuestionIndexMap.getOrDefault(sessionKey, 0);

        if (index >= quizQuestions.size()) {
            throw new RuntimeException("No more questions");
        }

        Question currentQuestion = quizQuestions.get(index);
        userQuestionIndexMap.put(sessionKey, index + 1); // Move forward

        return sanitizeQuestion(currentQuestion);
    }
/////////////////////
    public Question getPreviousQuestion(Long quizId, String username) 
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizAttempt attempt = quizAttemptRepository.findByUserAndQuizAndEndTimeIsNullAndSubmittedFalse(user, quiz)
                .orElseThrow(() -> new RuntimeException("You haven’t started this quiz or already submitted"));

        if (isTimeOver(attempt)) {
            throw new RuntimeException("Time ended! Submit your answers.");
        }

        List<Question> quizQuestions = questionRepository.findByQuizOrderByIdAsc(quiz);
        String sessionKey = user.getId() + ":" + quizId;
        int index = userQuestionIndexMap.getOrDefault(sessionKey, 0);

        if (index <= 1) 
        {
            userQuestionIndexMap.put(sessionKey, 0); // First question
            return sanitizeQuestion(quizQuestions.get(0));
        }

        userQuestionIndexMap.put(sessionKey, index - 1);
        return sanitizeQuestion(quizQuestions.get(index - 2)); // Show previous question
    }

    private boolean isTimeOver(QuizAttempt attempt) 
    {
        LocalDateTime endTime = attempt.getStartTime().plusMinutes(attempt.getQuiz().getDurationMinutes());
        return LocalDateTime.now().isAfter(endTime);
    }

    private Question sanitizeQuestion(Question question) 
    {
        Question sanitized = Question.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .build();

        // Attach quizId by setting the quiz reference if needed
        sanitized.setQuiz(Quiz.builder().id(question.getQuiz().getId()).build());

        return sanitized;
    }

    /**
     * Save answer or overwrite it if already answered
     */
    public ResponseEntity<?> answerQuestion(Long quizId, UserDetails userDetails, StudentAnswer answerRequest) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Question question = questionRepository.findById(answerRequest.getQuestion().getId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (!question.getQuiz().getId().equals(quizId)) {
            throw new RuntimeException("This question does not belong to the quiz");
        }

        Optional<QuizAttempt> latestAttemptOpt = quizAttemptRepository.findByUserAndQuiz(user, quiz)
                .stream()
                .filter(a -> !a.isSubmitted())
                .max(Comparator.comparing(QuizAttempt::getStartTime));

        if (latestAttemptOpt.isEmpty()) {
            throw new RuntimeException("Quiz not started or already submitted");
        }

        QuizAttempt latestAttempt = latestAttemptOpt.get();

        // Check if time is over
        LocalDateTime deadline = latestAttempt.getStartTime().plusMinutes(quiz.getDurationMinutes());
        if (LocalDateTime.now().isAfter(deadline)) {
            throw new RuntimeException("Time ended! Submit your quiz.");
        }

        // Check if max attempts reached
        long countAttempts = quizAttemptRepository.findByUserAndQuiz(user, quiz).size();
        if (countAttempts >= quiz.getMaxAttempts()) {
            throw new RuntimeException("Maximum attempts reached");
        }

        // Create a new attempt only if previous attempt has same question answered
        boolean alreadyAnswered = latestAttempt.getAnswers().stream()
                .anyMatch(ans -> ans.getQuestion().getId().equals(question.getId()));

        QuizAttempt attemptToUse = latestAttempt;

        if (alreadyAnswered) {
            // Create a new attempt
            QuizAttempt newAttempt = QuizAttempt.builder()
                    .user(user)
                    .quiz(quiz)
                    .startTime(latestAttempt.getStartTime()) // copying same time
                    .submitted(false)
                    .score(0)
                    .build();
            attemptToUse = quizAttemptRepository.save(newAttempt);
        }

        StudentAnswer savedAnswer = StudentAnswer.builder()
                .attempt(attemptToUse)
                .question(question)
                .selectedOption(answerRequest.getSelectedOption())
                .build();

        studentAnswerRepository.save(savedAnswer);

        return ResponseEntity.ok().body(new StudentAnswer(
                savedAnswer.getId(),
                savedAnswer.getAttempt(),
                savedAnswer.getQuestion(),
                savedAnswer.getSelectedOption()
        ));
    }


    public ResponseEntity<?> submitQuiz(Long quizID, UserDetails userDetails) 
    {

        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }
        User loggedInUser = optionalUser.get();

        // get quiz attempt
        Optional<QuizAttempt> optAttempt = quizAttemptRepository.findByUserIdAndQuizIdAndSubmittedFalse(loggedInUser.getId(), quizID);
        if (optAttempt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Quiz not started or already submitted.");
        }

        QuizAttempt attempt = optAttempt.get();
        Quiz quiz = attempt.getQuiz();
        LocalDateTime now = LocalDateTime.now();

        // check if time exceeded
        LocalDateTime startTime = attempt.getStartTime();
        int durationMinutes = quiz.getDurationMinutes();
        if (startTime.plusMinutes(durationMinutes).isBefore(now)) 
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Time ended, please submit your answers.");
        }

        //  get last answer per question
        List<StudentAnswer> answers = attempt.getAnswers();
        Map<Long, StudentAnswer> lastAnswerPerQuestion = new HashMap<>();
        for (StudentAnswer ans : answers) 
        {
            long qId = ans.getQuestion().getId();
            if (!lastAnswerPerQuestion.containsKey(qId) || ans.getId() > lastAnswerPerQuestion.get(qId).getId()) 
            {
                lastAnswerPerQuestion.put(qId, ans);
            }
        }

        //  5 - score calculation
        int score = 0;
        for (StudentAnswer ans : lastAnswerPerQuestion.values()) 
        {
            String selected = ans.getSelectedOption();
            String correct = ans.getQuestion().getCorrectOption();
            if (selected != null && selected.equalsIgnoreCase(correct)) 
            {
                score++;
            }
        }

        // total duration
        long durationSeconds = java.time.Duration.between(startTime, now).getSeconds();

        // update QuizAttempt
        attempt.setEndTime(now);
        attempt.setScore(score);
        attempt.setSubmitted(true);
        quizAttemptRepository.save(attempt);

        // Update or Create Leaderboard
        Optional<Leaderboard> optLeaderboard = leaderboardRepository.findByUserIdAndQuizId(loggedInUser.getId(), quizID);
        Leaderboard leaderboard;
        if (optLeaderboard.isPresent()) 
        {
            leaderboard = optLeaderboard.get();
            leaderboard.setScore(score);
            leaderboard.setDuration(durationSeconds);
        } else {
            leaderboard = Leaderboard.builder()
                    .quiz(quiz)
                    .user(loggedInUser)
                    .score(score)
                    .duration(durationSeconds)
                    .build();
        }
        leaderboardRepository.save(leaderboard);

        // Pass/Fail
        int totalQuestions = quiz.getQuestions().size();
        String passFail = (score >= totalQuestions / 2) ? "pass" : "fail";

        // Schedule email notification

        scheduler.schedule(() -> {
            notificationService.sendResultNotification(attempt);
        }, 10, TimeUnit.SECONDS);

        // ✅ Error Fixed
        Map<String, Object> response = new HashMap<>();
        response.put("score", score);
        response.put("totalQuestions", totalQuestions);
        response.put("passFail", passFail);
        response.put("durationSeconds", durationSeconds);

        return ResponseEntity.ok(response);
    }
    public void sendResultNotification(QuizAttempt attempt) {
        String message = String.format("Dear %s, you scored %d in quiz '%s'.",
                attempt.getUser().getUsername(),
                attempt.getScore(),
                attempt.getQuiz().getTitle());

        saveAndSendNotification(attempt.getUser(), message, "RESULT");
    }

    private void saveAndSendNotification(User user, String message, String type) {
        System.out.println("Sending notification to " + user.getUsername() + ": " + message);
    }

    
}