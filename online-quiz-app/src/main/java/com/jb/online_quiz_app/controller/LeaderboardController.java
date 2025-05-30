package com.jb.online_quiz_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.online_quiz_app.entity.Leaderboard;
import com.jb.online_quiz_app.service.LeaderboardService;

@RestController
@RequestMapping("/api/quizzes")
public class LeaderboardController 
{

    @Autowired
    private LeaderboardService leaderboardService;
    
    // 6. Leaderboard and Ranking (Public)

    // Get leaderboard for a specific quiz
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/leaderboard/quiz/{id}")
    public ResponseEntity<List<Leaderboard>> getLeaderboardByQuiz(@PathVariable Long id) 
    {
        return ResponseEntity.ok(leaderboardService.getLeaderboardByQuiz(id));
    }

    // Get overall leaderboard (All-time ranking)
    @GetMapping("/leaderboard/overall")
    public ResponseEntity<List<Leaderboard>> getOverallLeaderboard() 
    {
        return ResponseEntity.ok(leaderboardService.getOverallLeaderboard());
    }
    
}
