package com.jb.question_service.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jb.question_service.entity.Question;
import com.jb.question_service.entity.QuestionWrapper;
import com.jb.question_service.entity.Response;
import com.jb.question_service.repository.QuestionRepository;

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

    //-----------------------------------------------------------------------

    public List<Long> getQuestionsForQuiz(String categoryName, Integer numQuestions) 
    {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(categoryName, numQuestions);
        List<Long> questionIds = new ArrayList<>();
        
        for (Question q : questions) {
            questionIds.add(q.getId());
        }
        
        return questionIds;
    }


    public List<QuestionWrapper> getQuestionsFromID(List<Long> questionIds) 
    {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for (Long id : questionIds) 
        {
            Question question = questionRepository.findById(id).orElse(null);
            if (question != null) 
            {
                QuestionWrapper wrapper = new QuestionWrapper
                (
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
                );
                wrappers.add(wrapper);
            }
        }

        return wrappers;
    }

    public Integer getScore(List<Response> responses) {
        int right = 0;

        for (Response response : responses) {
            Optional<Question> optionalQuestion = questionRepository.findById(response.getId());

            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();

                if (response.getResponse() != null && response.getResponse().equals(question.getRightAnswer())) {
                    right++;
                }
            }
        }

        return right;
    }




}