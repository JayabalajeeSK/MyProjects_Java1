package com.jb.online_quiz_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.online_quiz_app.entity.Leaderboard;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> 
{
    List<Leaderboard> findByQuizIdOrderByScoreDescDurationAsc(Long quizId);
    List<Leaderboard> findAllByOrderByScoreDescDurationAsc();
    List<Leaderboard> findTop10ByQuizIdOrderByScoreDescDurationAsc(Long quizId);
    List<Leaderboard> findTop10ByOrderByScoreDescDurationAsc();
    Optional<Leaderboard> findByUserIdAndQuizId(Long userId, Long quizId);
}