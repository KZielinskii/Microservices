package pl.dmcs.domain;

import org.jboss.resteasy.reactive.server.model.DelegatingServerRestHandler;

import java.util.Random;


public class Player {
    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
    private int[][] Board = new int[10][10];
    private boolean isBot = false;
    public int[] shipSizes = new int[5];

    public int[][] getBoard() {
        return Board;
    }

    public void setBoard(int[][] board) {
        Board = board;
    }
    public void initialize()
    {
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board[0].length; j++) {
                Board[i][j] = 0;
            }
        }
    }
    /// Active ships are the 1s in the array, sunken parts of the ships are 2
    /// empty tiles that were shot are -1, to win the board can't have any 1s in it.
    /// The ships are 1 ship - 5 tiles, 1 ship - 4 tiles , 2 ships - 3 tiles 1 ship 2 tiles
    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }
    public int[] getShipSizes() {
        return shipSizes;
    }

    public void setShipSizes(int[] shipSizes) {
        this.shipSizes = shipSizes;
    }

    public void setupShips()
    {
        Random rand = new Random();
        this.shipSizes[0] = 5;
        this.shipSizes[1] = 4;
        this.shipSizes[2] = 3;
        this.shipSizes[3] = 3;
        this.shipSizes[4] = 2;
        for(int i = 0; i < shipSizes.length; i++)
        {
            while(true) {
                int x_position = rand.nextInt(10);
                int y_position = rand.nextInt(10);
                Direction direction = Direction.values()[rand.nextInt(4)];
                boolean valid = true;
                if(y_position+shipSizes[i]>= getBoard()[0].length||x_position+shipSizes[i]>=getBoard().length
                    || y_position-shipSizes[i]<0||x_position-shipSizes[i]<0)
                    continue;
                for(int j = 0; j < shipSizes[i]; j++)
                {
                        switch (direction)
                        {
                            case UP:
                                if(isTileValid(x_position-j,y_position,direction) == false)
                                    valid = false;
                            break;
                            case RIGHT:
                                if(isTileValid(x_position,y_position+j,direction) == false)
                                    valid = false;
                                break;
                            case DOWN:
                                if(isTileValid(x_position+j,y_position,direction) == false)
                                    valid = false;
                                break;
                            case LEFT:
                                if(isTileValid(x_position,y_position-j,direction) == false)
                                    valid = false;
                                break;
                        }
                }
                if(valid)
                {
                    for(int j = 0; j < shipSizes[i]; j++)
                    {
                        switch (direction) {
                            case UP:
                                Board[x_position-j][y_position] = 1;
                                break;
                            case RIGHT:
                                Board[x_position][y_position+j] = 1;
                                break;
                            case DOWN:
                                Board[x_position+j][y_position] = 1;
                                break;
                            case LEFT:
                                Board[x_position][y_position-j] = 1;
                                break;
                        }
                    }
                    break;
                }
            }
        }
    }
    public boolean isTileValid(int x, int y, Direction direction)
    {
        if (x < 0 || x >= Board.length || y < 0 || y >= Board[0].length) {
            return false;
        }
        int[][] neighbors = {
                {x - 1, y - 1}, {x - 1, y}, {x - 1, y + 1},
                {x, y - 1},                 {x, y + 1},
                {x + 1, y - 1}, {x + 1, y}, {x + 1, y + 1}
        };

        for (int[] neighbor : neighbors) {
            int nx = neighbor[0];
            int ny = neighbor[1];
            if (nx >= 0 && nx < Board.length && ny >= 0 && ny < Board[0].length) {
                if (Board[nx][ny] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean shootTile(int x, int y)
    {
        if(x < 0 || x >= Board.length || y < 0 || y >= Board[0].length)
            return false;
        if(Board[x][y] == 1) {
            Board[x][y] = 2;
            return true;
        }
        if(Board[x][y] == 0) {
            Board[x][y] = -1;
            return true;
        }
        return  false;
    }
    public boolean lostGame()
    {
        for(int i = 0; i < Board.length; i++)
            for(int j = 0; j < Board[0].length; j++)
            {
                if(Board[i][j] == 1)
                    return false;
            }
        return true;
    }
    public boolean moveRandom()
    {
        Random rand = new Random();
        while (true) {
            int x = rand.nextInt(Board.length);
            int y = rand.nextInt(Board[0].length);
            if(shootTile(x, y))
                break;
        }
        return true;
    }
    public int getScore()
    {
        int score = 0;
        return score;
    }
}
