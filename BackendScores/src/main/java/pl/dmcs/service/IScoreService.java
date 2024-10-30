package pl.dmcs.service;

import pl.dmcs.domain.Score;

import java.util.List;

public interface IScoreService {
    public void save(Score score);
    public List<Score> getScores(Integer userId,String gameName);
}
