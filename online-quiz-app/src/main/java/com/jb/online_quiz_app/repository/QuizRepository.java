package com.jb.online_quiz_app.repository;

// import java.time.LocalDateTime;
// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jb.online_quiz_app.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> 
{
    //List<Quiz> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end); //cheanged
}