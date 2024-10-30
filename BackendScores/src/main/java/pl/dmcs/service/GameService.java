package pl.dmcs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.dmcs.domain.Game;
import pl.dmcs.repository.GameRepository;
import pl.dmcs.repository.ScoreRepository;
@ApplicationScoped
public class GameService implements IGameService{
    @Inject
    GameRepository gameRepository;
    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }
}
