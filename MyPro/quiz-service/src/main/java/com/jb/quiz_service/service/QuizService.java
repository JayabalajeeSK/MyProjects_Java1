package com.jb.quiz_service.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jb.quiz_service.entity.QuestionWrapper;
import com.jb.quiz_service.entity.Quiz;
import com.jb.quiz_service.entity.Response;
import com.jb.quiz_service.feign.QuizInterface;
import com.jb.quiz_service.repository.QuizRepository;

@Service
public class QuizService 
{
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizInterface quizInterface;

    public Quiz createQuiz(String category, int numQ, String title) 
    {
        ResponseEntity<List<Long>> questions = quizInterface.getQuestionsForQuiz(category, numQ);
        
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        
        // ✅ Extract the list from ResponseEntity
        quiz.setQuestionsIds(questions.getBody());

        return quizRepository.save(quiz);
    }


    public List<QuestionWrapper> getAllQuestionFromQuiz(Long id) 
    {
        Quiz quiz = quizRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Long> questionsIds = quiz.getQuestionsIds();

        // Store response in correct type
        ResponseEntity<List<QuestionWrapper>> response = quizInterface.getQuestionsFromId(questionsIds);

        return response.getBody(); // now it's valid
    }


    public Integer calculateQuizResult(Long id, List<Response> responses) 
    {
        Integer score = quizInterface.getScore(responses);
        return score;
    }
}
