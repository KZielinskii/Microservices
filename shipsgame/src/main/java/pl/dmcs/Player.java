    package pl.dmcs;

    import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Random;


    public class Player implements Serializable {
        public enum Direction {
            UP, RIGHT, DOWN, LEFT
        }
        private int[][] Board = new int[10][10];
        private boolean isBot = false;
        public int[] shipSizes = new int[7];
        public List<Integer> sunkenShips = new ArrayList<>();
        public int[][] getBoard() {
            return Board;
        }

        public void setBoard(int[][] board) {
            Board = board;
        }
        public void initialize()
        {
            this.shipSizes[0] = 5;
            this.shipSizes[1] = 4;
            this.shipSizes[2] = 3;
            this.shipSizes[3] = 3;
            this.shipSizes[4] = 2;
            this.shipSizes[5] = 1;
            this.shipSizes[6] = 1;
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
            for(int i = 0; i < shipSizes.length; i++)
            {
                while(true) {
                    int x_position = rand.nextInt(10);
                    int y_position = rand.nextInt(10);
                    Direction direction = Direction.values()[rand.nextInt(4)];
                    boolean valid = true;
                    //if(y_position+shipSizes[i]>= getBoard()[0].length||x_position+shipSizes[i]>=getBoard().length
                        //|| y_position-shipSizes[i]<0||x_position-shipSizes[i]<0)
                    //{
                       // continue;
                    //}
                    for(int j = 0; j < shipSizes[i]; j++)
                    {
                            switch (direction)
                            {
                                case UP:
                                    if(isTileValid(x_position-j,y_position) == false)
                                        valid = false;
                                    break;
                                case RIGHT:
                                    if(isTileValid(x_position,y_position+j) == false)
                                        valid = false;
                                    break;
                                case DOWN:
                                    if(isTileValid(x_position+j,y_position) == false)
                                        valid = false;
                                    break;
                                case LEFT:
                                    if(isTileValid(x_position,y_position-j) == false)
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
            printShips();
        }
        public boolean isTileValid(int x, int y)
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
        public int shootTile(int x, int y)
        {
            if(x < 0 || x >= Board.length || y < 0 || y >= Board[0].length)
                return -1;
            if(Board[x][y] == 1) {
                Board[x][y] = 2;
                isShipSunken(x,y);
                return 1;
            }
            if(Board[x][y] == 0) {
                Board[x][y] = -1;
                return 0;
            }
            return  -1;
        }
        public boolean lostGame()
        {
            for (int[] ints : Board)
                for (int j = 0; j < Board[0].length; j++) {
                    if (ints[j] == 1)
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
                if(shootTile(x, y)==0)
                    break;
            }
            return true;
        }
        public boolean moveSmarter() {
            for (int x = 0; x < Board.length; x++) {
                for (int y = 0; y < Board[0].length; y++) {
                    if (Board[x][y] == 2) {
                        Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

                        for (Direction direction : directions) {
                            boolean hit = false;
                            int nx = x;
                            int ny = y;

                            do {
                                switch (direction) {
                                    case UP:
                                        nx -= 1;
                                        break;
                                    case RIGHT:
                                        ny += 1;
                                        break;
                                    case DOWN:
                                        nx += 1;
                                        break;
                                    case LEFT:
                                        ny -= 1;
                                        break;
                                }

                                if (!isTileValid(nx, ny) || Board[nx][ny] == -1) {
                                    break;
                                }
                                int result = shootTile(nx, ny);
                                if (result == 1) {
                                    hit = true;
                                } else if (result == 0) {
                                    hit = false;
                                } else {
                                    break;
                                }
                            } while (hit);

                        }
                        return true;
                    }
                }
            }
            return moveRandom();
        }
        private boolean isShipSunken(int x_position, int y_position) {
            List<int[]> shipTiles = collectShipTiles(x_position, y_position);
            for (int[] tile : shipTiles) {
                System.out.println(Arrays.toString(tile));
                if (Board[tile[0]][tile[1]] != 2) {
                    return false;
                }
            }
            for (int[] tile : shipTiles) {
                markSurroundingTiles(tile[0], tile[1]);
            }
            sunkenShips.add(shipTiles.size());
            return true;
        }
        private List<int[]> collectShipTiles(int x, int y) {
            List<int[]> shipTiles = new ArrayList<>();
            boolean[][] visited = new boolean[Board.length][Board[0].length];
            collectShipTilesDFS(x, y, shipTiles, visited);
            return shipTiles;
        }

        private void collectShipTilesDFS(int x, int y, List<int[]> shipTiles, boolean[][] visited) {
            if (x < 0 || x >= Board.length || y < 0 || y >= Board[0].length) {
                return;
            }
            if (visited[x][y] || (Board[x][y] != 2 && Board[x][y] != 1)) {
                return;
            }
            visited[x][y] = true;
            shipTiles.add(new int[]{x, y});
            collectShipTilesDFS(x - 1, y, shipTiles, visited);
            collectShipTilesDFS(x + 1, y, shipTiles, visited);
            collectShipTilesDFS(x, y - 1, shipTiles, visited);
            collectShipTilesDFS(x, y + 1, shipTiles, visited);
        }
        private void markSurroundingTiles(int x, int y) {
            int[][] neighbors = {
                    {x - 1, y - 1}, {x - 1, y}, {x - 1, y + 1},
                    {x, y - 1},               {x, y + 1},
                    {x + 1, y - 1}, {x + 1, y}, {x + 1, y + 1}
            };
            for (int[] neighbor : neighbors) {
                int nx = neighbor[0];
                int ny = neighbor[1];

                if (nx >= 0 && nx < Board.length && ny >= 0 && ny < Board[0].length) {
                    if (Board[nx][ny] != 2) {
                        Board[nx][ny] = -1;
                    }
                }
            }
        }
        public int getScore()
        {
            int score = 0 ;
            for (int i = 0; i < Board.length; i++) {
                for (int j = 0; j < Board[0].length; j++) {
                    if(Board[i][j] == 1)
                        score += 25;
                }
            }
            return score;
        }

        public void printShips()
        {
            for (int[] row : getBoard()) {
                for (int cell : row) {
                    System.out.print(cell + " ");
                }
                System.out.println();
            }
            System.out.println("\n\n\n");
        }

        public boolean validateBoard() {
            boolean[][] visited = new boolean[Board.length][Board[0].length];
            List<Integer> foundShips = new ArrayList<>();

            for (int x = 0; x < Board.length; x++) {
                for (int y = 0; y < Board[0].length; y++) {
                    if (Board[x][y] == 1 && !visited[x][y]) {
                        int shipLength = collectShipLength(x, y, visited);
                        foundShips.add(shipLength);
                    }
                }
            }

            int[] shipCount = new int[shipSizes.length];
            for (int length : foundShips) {
                boolean matched = false;
                for (int i = 0; i < shipSizes.length; i++) {
                    if (shipSizes[i] == length && shipCount[i] < 1) {
                        shipCount[i]++;
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    return false;
                }
            }

            for (int count : shipCount) {
                if (count != 1) {
                    return false;
                }
            }

            return true;
        }

        private int collectShipLength(int x, int y, boolean[][] visited) {
            List<int[]> shipTiles = new ArrayList<>();
            collectShipTilesDFS(x, y, shipTiles, visited);
            boolean horizontal = true, vertical = true;
            int startX = shipTiles.get(0)[0];
            int startY = shipTiles.get(0)[1];

            for (int[] tile : shipTiles) {
                if (tile[0] != startX) {
                    horizontal = false;
                }
                if (tile[1] != startY) {
                    vertical = false;
                }
            }
            if (!horizontal && !vertical) {
                return -1;
            }

            for (int[] tile : shipTiles) {
                int tx = tile[0];
                int ty = tile[1];
                if (!areSurroundingTilesEmpty(tx, ty,shipTiles.size())) {
                    return -1;
                }
            }

            return shipTiles.size();
        }

        private boolean areSurroundingTilesEmpty(int x, int y,int size) {
            int[][] neighbors = {
                    {x - 1, y - 1}, {x - 1, y}, {x - 1, y + 1},
                    {x, y - 1},               {x, y + 1},
                    {x + 1, y - 1}, {x + 1, y}, {x + 1, y + 1}
            };
            int maximum_neighbours = 0;
            int current_neighbours = 0;
            if(size>2)
                maximum_neighbours = 2;
            if(size==2)
                maximum_neighbours = 1;
            for (int[] neighbor : neighbors) {
                int nx = neighbor[0];
                int ny = neighbor[1];
                if (nx >= 0 && nx < Board.length && ny >= 0 && ny < Board[0].length) {
                    if (Board[nx][ny] == 1) {
                           current_neighbours++;
                    }
                }
            }
            System.out.println(current_neighbours+" "+maximum_neighbours);
            if (current_neighbours > maximum_neighbours) {
                return false;
            }
            return true;
        }

    }

