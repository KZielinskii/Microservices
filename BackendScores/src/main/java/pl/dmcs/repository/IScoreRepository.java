package pl.dmcs.repository;

import pl.dmcs.domain.Score;

import java.util.List;


public interface IScoreRepository {
    public void save(Score score);
    public List<Score> getScores(Integer id, String game);
}
