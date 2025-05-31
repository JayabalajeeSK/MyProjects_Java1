package com.jb.online_quiz_app.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //many attempts by one user
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne //many attempts on one / same quiz
    @JoinColumn(name="quiz_id")
    private Quiz quiz;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int score;

    private boolean submitted;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL) //one attempt by multiple student answer
    private List<StudentAnswer> answers;
}