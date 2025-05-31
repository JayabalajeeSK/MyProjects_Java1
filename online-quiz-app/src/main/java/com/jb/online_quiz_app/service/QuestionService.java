package com.jb.online_quiz_app.service;

import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.Question;
import com.jb.online_quiz_app.entity.Quiz;
import com.jb.online_quiz_app.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizService quizService;

    public Question addQuestionToQuiz(Long quizId, Question question) 
    {
        Quiz quiz = quizService.getQuizById(quizId);
        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

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

    public void deleteQuestion(Long questionId) 
    {
        if (!questionRepository.existsById(questionId)) 
        {
            throw new RuntimeException("Question not found with ID: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }
}
