package com.jb.online_quiz_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jb.online_quiz_app.entity.Question;
import com.jb.online_quiz_app.entity.Quiz;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> 
{
    List<Question> findByQuizId(Long quizId);
    List<Question> findByQuizIdOrderByIdAsc(Long quizId);
    List<Question> findByQuizOrderByIdAsc(Quiz quiz);
}