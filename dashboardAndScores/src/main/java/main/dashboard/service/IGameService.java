package main.dashboard.service;


import main.dashboard.domain.Game;

public interface IGameService {
    public void save(Game game);
    public Game getGameByName(String name);
}
