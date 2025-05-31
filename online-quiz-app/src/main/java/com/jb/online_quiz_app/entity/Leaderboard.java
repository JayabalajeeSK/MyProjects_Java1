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
@Table(name = "leaderboard")
public class Leaderboard 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // many leaderboard entries can belong to the same quiz
    @JoinColumn(name="quiz_id")
    private Quiz quiz;

    @ManyToOne // many leaderboard entries can be associated with the same user
    @JoinColumn(name="user_id")
    private User user;

    private int score;

    private long duration; // in seconds
}