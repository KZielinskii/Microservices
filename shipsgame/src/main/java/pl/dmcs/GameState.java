package pl.dmcs;

public class GameState {
    public int[][] board_human;
    public int[][] board_ai;
    String message;
    public int[][] getBoard_human() {
        return board_human;
    }

    public void setBoard_human(int[][] board_human) {
        this.board_human = board_human;
    }

    public int[][] getBoard_ai() {
        return board_ai;
    }

    public void setBoard_ai(int[][] board_ai) {
        this.board_ai = board_ai;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
