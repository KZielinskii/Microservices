package main.dashboard.repository;



import main.dashboard.domain.Game;
import main.dashboard.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IScoreRepository extends JpaRepository<Score, Long> {
    public List<Score> findScoresByUsernameAndGame_id(String username, Long game_id);
    List<Score> findTop10ByGameIdOrderByScoreDesc(Long gameId);
}
