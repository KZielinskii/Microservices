package main.pong.controller;

import main.pong.Difficulty;
import main.pong.service.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/pong")
public class PongController {
    PongService pongService;

    @Autowired
    public PongController(PongService pongService){
        this.pongService=pongService;
    }

    @PostMapping("/calculate-end-score")
    public ResponseEntity<Integer> calculateEndScore(@RequestParam int player1Score, @RequestParam int player2Score, @RequestParam(defaultValue = "medium") String difficulty) {
        return ResponseEntity.ok(pongService.calculateEndScore(player1Score, player2Score, Difficulty.valueOf(difficulty.toUpperCase())));
    }
}