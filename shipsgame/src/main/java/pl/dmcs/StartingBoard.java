package pl.dmcs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartingBoard {
    @JsonProperty("board")
    public int [][] player_board = new int [10][10];
    public int[][] getPlayer_board() {
        return player_board;
    }

    public void setPlayer_board(int[][] player_board) {
        this.player_board = player_board;
    }
}
