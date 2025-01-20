package main.pong.controller;

import main.dashboard.domain.Game;
import main.dashboard.domain.Score;
import main.pong.Difficulty;
import main.pong.service.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pong")
public class PongController {
    private final PongService pongService;

    @Autowired
    public PongController(PongService pongService) {
        this.pongService = pongService;
    }

    @PostMapping("/calculate-and-save-score")
    public ResponseEntity<String> calculateAndSaveScore(
            @RequestParam String playerName,
            @RequestParam int player1Score,
            @RequestParam int player2Score,
            @RequestParam(defaultValue = "medium") String difficulty) {

        int endScore = pongService.calculateEndScore(player1Score, player2Score, Difficulty.valueOf(difficulty.toUpperCase()));

        Game game = new Game();
        game.setName("PONG");

        Score score = new Score();
        score.setUsername(playerName);
        score.setScore((long) endScore);

        List<Score> scores = new ArrayList<>();
        scores.add(score);
        game.setScores(scores);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/dashboard/addScore";
        ResponseEntity<String> response = restTemplate.postForEntity(url, game, String.class);

        return ResponseEntity.ok(response.getBody());
    }
}
