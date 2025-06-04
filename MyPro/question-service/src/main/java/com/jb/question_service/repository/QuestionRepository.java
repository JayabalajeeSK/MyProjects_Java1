package com.jb.question_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jb.question_service.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>
{
    List<Question> findByDifficultyLevel(String difficultyLevel);
    List<Question> findByCategory(String category);

    
    @Query(value = "SELECT * FROM questions q WHERE q.category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@Param("category") String category, @Param("numQ") int numQ);
}
