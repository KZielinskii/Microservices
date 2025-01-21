package main.pong.controller;


import main.pong.Difficulty;
import main.pong.Game;
import main.pong.Score;
import main.pong.service.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
            @RequestParam(defaultValue = "medium") String difficulty,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        System.out.println("Authorization header: " + authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing or invalid token");
        }

        String token = authorizationHeader.substring(7);
        System.out.println("Received request:");
        System.out.println("playerName: " + playerName);
        System.out.println("player1Score: " + player1Score);
        System.out.println("player2Score: " + player2Score);
        System.out.println("difficulty: " + difficulty);

        try {
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

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Game> requestEntity = new HttpEntity<>(game, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

}
