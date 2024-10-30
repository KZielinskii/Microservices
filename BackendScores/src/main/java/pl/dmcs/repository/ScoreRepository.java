package pl.dmcs.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import pl.dmcs.domain.Score;

import java.util.ArrayList;
import java.util.List;

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
    public List<Score> getScores(Integer userId, String gameName) {
        List<Score> scores = em.createQuery("SELECT sc FROM Score sc JOIN sc.game g WHERE sc.userId = :userId AND g.name = :gameName", Score.class)
                .setParameter("userId",  userId)
                .setParameter("gameName", gameName)
                .getResultList();
        System.out.println("TESTING");
        System.out.println(scores);
        return scores;
    }


}
