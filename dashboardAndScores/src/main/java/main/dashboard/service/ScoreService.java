package main.dashboard.service;



import main.dashboard.domain.Score;
import main.dashboard.repository.IGameRepository;
import main.dashboard.repository.IScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService implements IScoreService {

    IScoreRepository scoreRepository;
    IGameRepository gameRepository;

    @Autowired
    public ScoreService(IScoreRepository scoreRepository, IGameRepository gameRepository) {
        this.scoreRepository = scoreRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void save(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public Long getGameId(String gameName) {
        return gameRepository.findGameByName(gameName).getId();
    }

    @Override
    public List<Score> getScores(String userName,String gameName) {
        Long gameId= gameRepository.findGameByName(gameName).getId();
        List<Score> scores = scoreRepository.findScoresByUsernameAndGame_id(userName, gameId);
        return scores;
    }
    @Override
    public List<Score> getTopScores(String gameName) {
        Long gameId= gameRepository.findGameByName(gameName).getId();
        return scoreRepository.findTop10ByGameIdOrderByScoreDesc(gameId);
    }

}
