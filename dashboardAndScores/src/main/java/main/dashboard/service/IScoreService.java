package main.dashboard.service;



import main.dashboard.domain.Score;

import java.util.List;

public interface IScoreService {
    public void save(Score score);
    public List<Score> getScores(String userName,String gameName);
    public Long getGameId(String gameName);
    public List<Score> getTopScores(String gameName);
}
