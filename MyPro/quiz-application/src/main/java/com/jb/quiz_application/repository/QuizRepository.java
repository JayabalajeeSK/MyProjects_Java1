package com.jb.quiz_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.quiz_application.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
