package pl.dmcs.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import pl.dmcs.domain.Game;
@ApplicationScoped
public class GameRepository implements IGameRepository{
    @Inject
    EntityManager em;
    @Override
    @Transactional
    public void save(Game game) {
        em.persist(game);
    }
}
