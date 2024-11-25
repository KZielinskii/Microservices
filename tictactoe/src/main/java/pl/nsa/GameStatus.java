package pl.nsa;

public class GameStatus {
    private char[][] board;
    private String status;
    private Integer score;

    public GameStatus(char[][] board, String status, Integer score) {
        this.board = board;
        this.status = status;
        this.score = score;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
