package pl.dmcs.domain;

import jakarta.persistence.*;

@Entity
@Table(name="scores")
public class Score {

    @Id
    @GeneratedValue
    Long id;
    String username;
    Long score;
    Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId")
    Game game;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
