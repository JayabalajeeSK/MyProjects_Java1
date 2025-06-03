package com.jb.quiz_application.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jb.quiz_application.entity.Question;
import com.jb.quiz_application.repository.QuestionRepository;

@Service
public class QuestionService 
{
    @Autowired
    private QuestionRepository questionRepository;

    public Question addQuestion(Question question)
    {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question updatedQuestion)
    {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("id: +"+id+" is Exist"));

        question.setCategory(updatedQuestion.getCategory());
        question.setQuestionTitle(updatedQuestion.getQuestionTitle());
        question.setOption1(updatedQuestion.getOption1());
        question.setOption2(updatedQuestion.getOption2());
        question.setOption3(updatedQuestion.getOption3());
        question.setOption4(updatedQuestion.getOption4());
        question.setRightAnswer(updatedQuestion.getRightAnswer());
        question.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
        return questionRepository.save(question);
    }

    public void deleteQuestionById(Long id)
    {
        questionRepository.findById(id).orElseThrow(() -> new RuntimeException("id: +"+id+" is Exist"));
        questionRepository.deleteById(id);
    }

    public List<Question> getAllQuestions() 
    {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByDifficultyLevel(String difficultyLevel) 
    {
        return questionRepository.findByDifficultyLevel(difficultyLevel);
    }

    public List<Question> getQuestionsByCategory(String category) 
    {
        return questionRepository.findByCategory(category);
    }
}