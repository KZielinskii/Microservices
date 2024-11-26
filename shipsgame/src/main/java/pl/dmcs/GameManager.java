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
    public void startGame()
    {
        Player hum = new Player();
        Player si = new Player();
        hum.initialize();
        hum.setupShips();
        si.initialize();
        si.setupShips();
        setAi(si);
        setHuman(hum);
        initialize();
        setHuman_board(hum.getBoard());
        setAi_board(si.getBoard());
    }
    public String makeMove(int x, int y)
    {
        Player si = getAi();
        Player hum = getHuman();
        printBoard();
        int move_result = si.shootTile(x,y);
        setAi(si);
        setAi_board(si.getBoard());
        setHuman(hum);
        setHuman_board(hum.getBoard());
        printBoard();
        if(si.lostGame())
        {
            return "Human won";
        }
        else if(move_result == 1)
        {
            return "A ship has been hit you have additional move";
        }
        hum.moveRandom();
        setHuman(hum);
        setHuman_board(hum.getBoard());
        if(hum.lostGame())
        {
            printBoard();
            return "AI won";
        }
        return "Your move";
    }
    public int[][] hideShips() {
        int[][] originalBoard = getAi_board();
        int[][] hiddenBoard = new int[originalBoard.length][originalBoard[0].length];
        for (int i = 0; i < originalBoard.length; i++) {
            for (int j = 0; j < originalBoard[0].length; j++) {
                if (originalBoard[i][j] == 1) {
                    hiddenBoard[i][j] = 0;
                } else {
                    hiddenBoard[i][j] = originalBoard[i][j];
                }
            }
        }
        return hiddenBoard;
    }

    public void printBoard()
    {
        System.out.println("Human:\n");
        for (int[] row : getHuman_board()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("AI:\n");
        for (int[] row : getAi_board()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
