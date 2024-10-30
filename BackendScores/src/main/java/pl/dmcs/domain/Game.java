package pl.dmcs.domain;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue
    Long id;
    String name;
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
    @OneToMany(mappedBy = "game", orphanRemoval = true,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Score> scores;
}
