package main.pong.service;

import main.pong.Difficulty;
import org.springframework.stereotype.Service;

import static main.pong.Difficulty.*;

@Service
public class PongService {

    public int calculateEndScore(int player1Score, int player2Score, Difficulty difficulty) {
        int baseScore = player1Score + player2Score;
        int difficultyMultiplier = switch (difficulty) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case HARD -> 3;
            default -> 1;
        };

        return baseScore * difficultyMultiplier;
    }
}
