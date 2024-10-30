package pl.dmcs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.dmcs.domain.Score;
import pl.dmcs.repository.ScoreRepository;

import java.util.List;

@ApplicationScoped
public class ScoreService implements IScoreService{
    @Inject
    ScoreRepository scoreRepository;
    @Override
    public void save(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public List<Score> getScores(Integer userId,String gameName) {
        List<Score> scores = scoreRepository.getScores(userId, gameName);
        return scores;
    }

}
