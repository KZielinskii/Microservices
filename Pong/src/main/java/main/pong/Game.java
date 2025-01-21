package main.pong;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import java.util.List;


public class Game {

    Long id;

    String name;

    private List<Score> scores;

    public Game() {
    }

    public Game(String name, List<Score> scores) {
        this.name = name;
        this.scores = scores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }
}
