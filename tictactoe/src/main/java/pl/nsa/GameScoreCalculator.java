package pl.nsa;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;

@ApplicationScoped
public class GameScoreCalculator {

    public GameScoreCalculator() {
    }

    public int calculateScore(GameResult result, Difficulty difficulty, int playerMoves) {
        int baseScore = 0;

        switch (result) {
            case WIN:
                baseScore = 100;
                break;
            case DRAW:
                baseScore = 50;
                break;
            case LOSE:
                baseScore = 0;
                break;
        }

        double difficultyMultiplier = getDifficultyMultiplier(difficulty);

        int moveBonus = calculateMoveBonus(playerMoves);

        return (int) ((baseScore + moveBonus) * difficultyMultiplier);
    }

    private double getDifficultyMultiplier(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return 1.0;
            case MEDIUM:
                return 2.0;
            case HARD:
                return 3.0;
            default:
                return 1.0;
        }
    }

    private int calculateMoveBonus(int playerMoves) {
        if (playerMoves <= 5) {
            return 50;
        } else if (playerMoves <= 7) {
            return 20;
        } else {
            return 0;
        }
    }
}
