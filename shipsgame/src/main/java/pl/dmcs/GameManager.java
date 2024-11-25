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

    }
    public String makeMove(int x, int y)
    {
        Player si = getAi();
        Player hum = getHuman();
        int move_result = si.shootTile(x,y);
        setAi(si);
        setAi_board(si.getBoard());
        setHuman(hum);
        setHuman_board(hum.getBoard());
        if(si.lostGame())
        {
            return "Human won";
        }
        else if(move_result == 1)
        {
            return "A ship has been hit you have additional move";
        }
        hideShips(0);
        hum.moveRandom();
        setHuman(hum);
        setHuman_board(hum.getBoard());
        if(hum.lostGame())
        {
            printBoard();
            return "AI won";
        }
        printBoard();
        return "Your move";
    }
    public void hideShips(int player_type)
    {
        if(player_type == 1)
        {
            int[][]board = getHuman_board();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if(board[i][j] == 1)
                        board[i][j] = 0;
                }
            }
            setHuman_board(board);
        }
        else {
            int[][]board = getAi_board();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if(board[i][j] == 1)
                        board[i][j] = 0;
                }
            }
            setAi_board(board);
        }
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
