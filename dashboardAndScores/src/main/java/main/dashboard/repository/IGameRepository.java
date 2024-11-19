package main.dashboard.repository;


import main.dashboard.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long id);
    Game findGameByName(String name);
}
