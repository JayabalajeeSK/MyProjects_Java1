package com.jb.quiz_application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jb.quiz_application.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>
{
    List<Question> findByDifficultyLevel(String difficultyLevel);
    List<Question> findByCategory(String category);
}
