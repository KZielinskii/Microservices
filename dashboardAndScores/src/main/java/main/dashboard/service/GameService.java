package main.dashboard.service;


import main.dashboard.domain.Game;
import main.dashboard.repository.IGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements IGameService{
    @Autowired
    IGameRepository gameRepository;
    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }
    @Override
    public Game getGameByName(String name) {
        return gameRepository.findGameByName(name);
    }
}
