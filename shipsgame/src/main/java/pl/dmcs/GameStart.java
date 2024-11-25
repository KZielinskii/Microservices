package pl.dmcs;

public class GameStart {
    public int [][] human_board;
    String sessionId;

    public int[][] getHuman_board() {
        return human_board;
    }

    public void setHuman_board(int[][] human_board) {
        this.human_board = human_board;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
