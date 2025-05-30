package com.jb.online_quiz_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.Leaderboard;
import com.jb.online_quiz_app.repository.LeaderboardRepository;
@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;
        //---------------------------------------------------------------------------------------------
    // 6. Leaderboards & Rankings
    //---------------------------------------------------------------------------------------------

    public List<Leaderboard> getLeaderboardByQuiz(Long quizId) {
        return leaderboardRepository.findTop10ByQuizIdOrderByScoreDescDurationAsc(quizId);
    }

    public List<Leaderboard> getOverallLeaderboard() {
        return leaderboardRepository.findTop10ByOrderByScoreDescDurationAsc();
    }
    
}
