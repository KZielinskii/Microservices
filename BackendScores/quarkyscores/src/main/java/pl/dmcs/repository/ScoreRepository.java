package pl.dmcs.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import pl.dmcs.domain.Score;
@ApplicationScoped
public class ScoreRepository implements IScoreRepository {
    @Inject
    EntityManager em;
    @Override
    @Transactional
    public void save(Score score) {
        em.persist(score);
    }

    @Override
    public Score[] getScores(int id, String game) {
        return new Score[0];
    }
}
