package pl.dmcs;

public class GameManager {
    private Player human;
    private Player ai;
    private int[][] human_board = new int[10][10];
    private int[][] ai_board = new int[10][10];
    public int[][] getAi_board() {
        return ai_board;
    }

    public void setAi_board(int[][] ai_board) {
        this.ai_board = ai_board;
    }

    public int[][] getHuman_board() {
        return human_board;
    }

    public void setHuman_board(int[][] human_board) {
        this.human_board = human_board;
    }

    public Player getAi() {
        return ai;
    }

    public void setAi(Player ai) {
        this.ai = ai;
    }

    public Player getHuman() {
        return human;
    }

    public void setHuman(Player human) {
        this.human = human;
    }
    public void initialize()
    {
        for (int i = 0; i < human_board.length; i++) {
            for (int j = 0; j < human_board[0].length; j++) {
                human_board[i][j] = 0;
                ai_board[i][j] = 0;
            }
        }
    }
}
