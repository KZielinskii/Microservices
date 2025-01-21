package main.pong;

import com.fasterxml.jackson.annotation.JsonBackReference;
import main.dashboard.domain.Game;


public class Score {

    Long id;

    String username;

    Long score;

    main.dashboard.domain.Game game;

    public Score() {
    }

    public Score(String username, Long score, main.dashboard.domain.Game game) {
        this.username = username;
        this.score = score;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public main.dashboard.domain.Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", game=" + game +
                '}';
    }

}
