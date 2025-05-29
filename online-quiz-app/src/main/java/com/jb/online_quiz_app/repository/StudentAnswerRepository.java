package com.jb.online_quiz_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jb.online_quiz_app.entity.StudentAnswer;
import java.util.List;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> 
{
    List<StudentAnswer> findByAttemptId(Long attemptId);
}