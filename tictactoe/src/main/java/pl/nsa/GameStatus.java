package pl.nsa;

public class GameStatus {
    private char[][] board;
    private String status;

    public GameStatus(char[][] board, String status) {
        this.board = board;
        this.status = status;
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
}
