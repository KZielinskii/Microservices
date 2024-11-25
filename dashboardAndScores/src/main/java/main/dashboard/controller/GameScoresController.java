package main.dashboard.controller;

import main.dashboard.domain.Game;
import main.dashboard.domain.Score;
import main.dashboard.service.GameService;
import main.dashboard.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class GameScoresController {

    public GameService gameService;
    public ScoreService scoreService;

    @Autowired
    public GameScoresController(@Qualifier("gameService") GameService gameService, @Qualifier("scoreService") ScoreService scoreService) {
        this.gameService = gameService;
        this.scoreService = scoreService;
    }

    @PostMapping("/addScore")
    public ResponseEntity<String> addGameScore(@RequestBody Game game) {
        Game gameInstance = gameService.getGameByName(game.getName());
        for(Score score : game.getScores()) {
            score.setGame(gameInstance);
            scoreService.save(score);
        }
        System.out.println("Game score added");
        return ResponseEntity.ok("Game score added");
    }
    @GetMapping("/game-scores")
    public ResponseEntity<List<Score>> getGameScores(@RequestParam String username,@RequestParam String gameName) {
        return ResponseEntity.ok(scoreService.getScores(username, gameName));
    }
    @GetMapping("/top-scores")
    public ResponseEntity<List<Score>> getTopScores(@RequestParam String gameName) {
        return ResponseEntity.ok(scoreService.getTopScores(gameName));
    }
}
