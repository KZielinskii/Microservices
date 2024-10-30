package pl.nsa;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
public class GameService {
    private char[][] board = new char[3][3];
    private Difficulty difficulty;

    public void initializeGame(Difficulty difficulty) {
        this.difficulty = difficulty;
        // Initialize empty board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public boolean makePlayerMove(PlayerMove move) {
        if (move.x < 0 || move.x > 2 || move.y < 0 || move.y > 2 || board[move.x][move.y] != '-') {
            return false; // Invalid move
        }
        board[move.x][move.y] = 'X'; // Assume 'X' is the player symbol
        return true;
    }

    public boolean checkWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
        }
        // Check diagonals
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;

        return false;
    }

    public boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') return false; // Empty cell found
            }
        }
        return true; // No empty cells, it's a draw
    }

    public char[][] getBoard() {
        return board;
    }

    public void makeAIMove() {
        if (difficulty == Difficulty.EASY) {
            makeRandomMove();
        } else {
            makeStrategicMove();
        }
    }

    private void makeRandomMove() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        } while (board[x][y] != '-'); // Find an empty spot
        board[x][y] = 'O'; // Assume 'O' is the AI symbol
    }

    private int evaluateBoard() {
        // Check rows and columns for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == 'O') return +10;
                else if (board[i][0] == 'X') return -10;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == 'O') return +10;
                else if (board[0][i] == 'X') return -10;
            }
        }
        // Check diagonals for a win
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'O') return +10;
            else if (board[0][0] == 'X') return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'O') return +10;
            else if (board[0][2] == 'X') return -10;
        }
        return 0; // No winner
    }


    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluateBoard();

        // If AI has won, return score
        if (score == 10) return score - depth;

        // If player has won, return score
        if (score == -10) return score + depth;

        // If no moves left, it's a draw
        if (checkDraw()) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O';
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = '-';
                    }
                }
            }
            return best;
        }
    }

    private void makeStrategicMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestRow = -1, bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'O';
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = '-';

                    if (moveVal > bestVal) {
                        bestRow = i;
                        bestCol = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        board[bestRow][bestCol] = 'O';
    }
}
