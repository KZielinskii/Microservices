package main.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="scores")
public class Score {

    @Id
    @GeneratedValue
    Long id;

    String username;

    Long score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    @JsonBackReference
    Game game;

    public Score() {
    }

    public Score(String username, Long score, Game game) {
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

    public Game getGame() {
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
