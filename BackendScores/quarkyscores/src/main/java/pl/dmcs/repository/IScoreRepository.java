package pl.dmcs.repository;

import pl.dmcs.domain.Score;


public interface IScoreRepository {
    public void save(Score score);
    public Score[] getScores(int id,String game);
}
