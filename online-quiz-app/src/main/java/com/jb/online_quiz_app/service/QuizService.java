package com.jb.online_quiz_app.service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService 
{
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final NotificationService notificationService;

//---------------------------------------------------------------------------------------------
//2. Quiz Management (Admin)
//---------------------------------------------------------------------------------------------   

//////////////////////////////////////////quiz/////////////////////////////////////

    // Create a quiz
    public Quiz createQuiz(Quiz quiz) 
    {
        return quizRepository.save(quiz);
    }

    // Update a quiz
    public Quiz updateQuiz(Long quizId, Quiz updatedQuiz) 
    {
        Quiz existing = getQuizById(quizId);

        existing.setTitle(updatedQuiz.getTitle());
        existing.setDescription(updatedQuiz.getDescription()); // Update description
        existing.setDurationMinutes(updatedQuiz.getDurationMinutes());
        existing.setMaxAttempts(updatedQuiz.getMaxAttempts());

        // Optional: if you want to update questions, handle it separately with proper cascade logic
        // Update questions if provided
        if (updatedQuiz.getQuestions() != null && !updatedQuiz.getQuestions().isEmpty()) 
        {
            // Clear existing questions (optional: avoid duplicates or stale data)
            existing.getQuestions().clear();

            for (Question q : updatedQuiz.getQuestions()) 
            {
                q.setQuiz(existing); // Maintain relationship
                existing.getQuestions().add(q);
            }
        }
        return quizRepository.save(existing);
    }


    // Delete a quiz
    public void deleteQuiz(Long quizId) 
    {
        quizRepository.deleteById(quizId);
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() 
    {
        return quizRepository.findAll();
    }

    // Get quiz by ID
    public Quiz getQuizById(Long quizId) 
    {
        return quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

///////////////////////////////////question////////////////////////////////////////

    // Add question to quiz
    public Question addQuestionToQuiz(Long quizId, Question question) 
    {
        Quiz quiz = getQuizById(quizId);

        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

    // Update question
    public Question updateQuestion(Long questionId, Question updated) 
    {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        
        question.setQuestionText(updated.getQuestionText());
        question.setOptionA(updated.getOptionA());
        question.setOptionB(updated.getOptionB());
        question.setOptionC(updated.getOptionC());
        question.setOptionD(updated.getOptionD());
        question.setCorrectOption(updated.getCorrectOption());
        return questionRepository.save(question);
    }

    // Delete question
    public void deleteQuestion(Long questionId) 
    {
        questionRepository.deleteById(questionId);
    }

//---------------------------------------------------------------------------------------------
//3. Quiz Taking (Student)
//---------------------------------------------------------------------------------------------   

    // 1 start quiz attempt
    public QuizAttempt startQuizAttempt(User user, Long quizId) 
    {
        Quiz quiz = getQuizById(quizId);

        QuizAttempt attempt = QuizAttempt.builder()
                                         .quiz(quiz)
                                         .user(user)
                                         .startTime(LocalDateTime.now())
                                         .submitted(false)
                                         .score(0)
                                         .build();
        return quizAttemptRepository.save(attempt);
    }

    // 2️ Get one question at a time by index for quiz
    public Question getQuestionByIndex(Long quizId, int index) 
    {
        List<Question> questions = questionRepository.findByQuizIdOrderByIdAsc(quizId);

        if (index < 0 || index >= questions.size()) 
        {
            throw new RuntimeException("Invalid question index");
        }
        return questions.get(index);
    }

    // 3️ Save student's answer progress (without submitting quiz)
    public StudentAnswer saveAnswerProgress(StudentAnswer answer) 
    {
        return studentAnswerRepository.save(answer);
    }

    // 4️ Submit quiz attempt manually or auto-submit on timeout
    public QuizAttempt submitQuizAttempt(Long attemptId, List<StudentAnswer> answers) 
    {
        QuizAttempt attempt = quizAttemptRepository.findById(attemptId).orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (attempt.isSubmitted()) 
        {
            throw new RuntimeException("Attempt already submitted");
        }

        int score = 0;
        for (StudentAnswer ans : answers) 
        {
            ans.setAttempt(attempt);
            studentAnswerRepository.save(ans);

            if (ans.getQuestion().getCorrectOption().equalsIgnoreCase(ans.getSelectedOption())) 
            {
                score++;
            }
        }

        attempt.setScore(score);
        attempt.setEndTime(LocalDateTime.now());
        attempt.setSubmitted(true);
        
        quizAttemptRepository.save(attempt);

        // Update leaderboard after submission
        updateLeaderboard(attempt);

        // Notify user about their quiz result
        notificationService.sendResultNotification(attempt);

        return attempt;
    }


//--------------------------------------------------------------------------------------
//4. Evaluation & Results
//--------------------------------------------------------------------------------------

    private void updateLeaderboard(QuizAttempt attempt) 
    {
        // Define pass mark (e.g., 5 out of 10)
        int passMark = 5;
        boolean isPassed = attempt.getScore() >= passMark;
        String status = isPassed ? "Pass" : "Fail";

        // Optional: Print or log pass/fail status
        System.out.println("User: " + attempt.getUser().getUsername() +
                        " | Score: " + attempt.getScore() +
                        " | Status: " + status);

        // Build and save leaderboard entry
        Leaderboard leaderboardEntry = Leaderboard.builder()
                .quiz(attempt.getQuiz())
                .user(attempt.getUser())
                .score(attempt.getScore())
                .duration(Duration.between(attempt.getStartTime(), attempt.getEndTime()).getSeconds())
                .build();

        leaderboardRepository.save(leaderboardEntry);
    }


//---------------------------------------------------------------------------
// 5. Attempt History
//---------------------------------------------------------------------------

    // Student: View past attempts
    public List<QuizAttempt> getAttemptsByUser(Long userId) 
    {
        return quizAttemptRepository.findByUserId(userId);
    }

    // Admin: View attempts by quiz
    public List<QuizAttempt> getAttemptsByQuiz(Long quizId) 
    {
        return quizAttemptRepository.findByQuizId(quizId);
    }

    // Admin: Filter attempts by date/user/quiz
    public List<QuizAttempt> getAttemptsByQuizAndUser(Long quizId, Long userId) 
    {
        return quizAttemptRepository.findByQuizIdAndUserId(quizId, userId);
    }

//-------------------------------------------------------------------------
// 6. Leaderboards & Rankings
//-------------------------------------------------------------------------

    // Top scorers per quiz
    public List<Leaderboard> getLeaderboardByQuiz(Long quizId) 
    {
        return leaderboardRepository.findTop10ByQuizIdOrderByScoreDescDurationAsc(quizId);
    }

    // Global leaderboard
    public List<Leaderboard> getOverallLeaderboard() 
    {
        return leaderboardRepository.findTop10ByOrderByScoreDescDurationAsc();
    }

    // Filter attempts by date range only
    public List<QuizAttempt> getAttemptsByDateRange(LocalDateTime start, LocalDateTime end) 
    {
        return quizAttemptRepository.findByEndTimeBetween(start, end);
    }

    // Filter attempts by quiz, user, and date range
    public List<QuizAttempt> getAttemptsFiltered(Long quizId, Long userId, LocalDateTime start, LocalDateTime end) 
    {
        return quizAttemptRepository.findByQuizIdAndUserIdAndEndTimeBetween(quizId, userId, start, end);
    }
}