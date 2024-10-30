package pl.nsa;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerMove {
    @JsonProperty("x")
    public int x;
    @JsonProperty("y")
    public int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PlayerMove() {
    }

    public PlayerMove(int x, int y) {
        this.x = x;
        this.y = y;
    }
// Constructor, getters, setters
}
