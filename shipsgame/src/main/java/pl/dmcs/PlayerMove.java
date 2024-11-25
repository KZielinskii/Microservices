package pl.dmcs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerMove {
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    PlayerMove()
    {
        this.x = 0;
        this.y = 0;
    }
    PlayerMove(int x, int y) {
        this.x = x;
        this.y = y;
    }
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
}
