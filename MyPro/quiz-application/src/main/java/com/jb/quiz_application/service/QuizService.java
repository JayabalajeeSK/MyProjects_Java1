package com.jb.quiz_application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.quiz_application.entity.Question;
import com.jb.quiz_application.entity.QuestionWrapper;
import com.jb.quiz_application.entity.Quiz;
import com.jb.quiz_application.entity.Response;
import com.jb.quiz_application.repository.QuestionRepository;
import com.jb.quiz_application.repository.QuizRepository;

@Service
public class QuizService 
{
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Quiz createQuiz(String category, int numQ, String title) 
    {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }

    public List<QuestionWrapper> getAllQuestionFromQuiz(Long id) 
    {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> questions = quiz.getQuestions();

        List<QuestionWrapper> questionsForUsers = new ArrayList<>();
        for(Question q: questions)
        {
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUsers.add(questionWrapper);
        }
        return questionsForUsers;
        
    }


    public Integer calculateQuizResult(Long id, List<Response> responses) {
        Quiz quiz = quizRepository.findById(id).orElse(null);
        if (quiz == null) {
            return 0;
        }

        List<Question> questions = quiz.getQuestions();

        // Create a Map of questionId to Question for quick lookup
        Map<Long, Question> questionMap = new HashMap<>();
        for (Question q : questions) 
        {
            questionMap.put(q.getId(), q);
        }

        int right = 0;

        for (Response response : responses) {
            Question question = questionMap.get(response.getId());
            if (question != null) {
                if (response.getResponse() != null && response.getResponse().equals(question.getRightAnswer())) {
                    right++;
                }
            }
        }

        return right;
    }


}
