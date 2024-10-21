package pl.dmcs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.dmcs.domain.Score;
import pl.dmcs.repository.ScoreRepository;

@ApplicationScoped
public class ScoreService implements IScoreService{
    @Inject
    ScoreRepository scoreRepository;
    @Override
    public void save(Score score) {
        scoreRepository.save(score);
    }
}
