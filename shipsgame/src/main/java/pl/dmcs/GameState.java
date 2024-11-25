package pl.dmcs;

public class GameState {
    public int[][] human_board;
    public int[][] ai_board;
    String message;
    public int[][] getBoard_human() {
        return human_board;
    }

    public void setBoard_human(int[][] board_human) {
        this.human_board = board_human;
    }

    public int[][] getBoard_ai() {
        return ai_board;
    }

    public void setBoard_ai(int[][] board_ai) {
        this.ai_board = board_ai;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
