package com.jb.online_quiz_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "student_answers")
public class StudentAnswer 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // many student answer linked to one quiz attempt
    @JoinColumn(name="attempt_id")
    private QuizAttempt attempt;

    @ManyToOne //many student answer linked to one question (choice to answers student select for one question)
    @JoinColumn(name="question_id")
    private Question question;

    private String selectedOption; // A OR B OR C OR D
}