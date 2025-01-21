package pl.dmcs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.dmcs.GameManager;
import pl.dmcs.domain.GameManagerDomain;
import pl.dmcs.repository.GameManagerRepository;


import java.util.Optional;

@ApplicationScoped
public class GameManagerService {

    @Inject
    GameManagerRepository gameSessionRepository;

    // Create or update a game session
    @Transactional
    public GameManagerDomain saveGameSession(GameManager gameSession, String sessionId) {
        GameManagerDomain gameSessionDomain = new GameManagerDomain(gameSession, sessionId);
        gameSessionRepository.save(gameSessionDomain);
        return gameSessionDomain;
    }

    public void modifyGameSession(GameManager gameSession, String sessionId){
        GameManagerDomain gameSessionDomain = gameSessionRepository.findBySessionId(sessionId);
        GameManagerDomain gameSessionDomain1 = new GameManagerDomain(gameSessionDomain,gameSession,sessionId);
        gameSessionRepository.save(gameSessionDomain1);
    }

    public GameManager findBySessionId(String sessionId) {
        GameManagerDomain gameManagerDomain = gameSessionRepository.findBySessionId(sessionId);
        return gameManagerDomain.toGameManager();
    }

    // Retrieve a game session by ID
    public Optional<GameManagerDomain> findById(Long id) {
        return gameSessionRepository.findById(id);
    }

    // Delete a game session
    public void deleteGameSession(Long id) {
        gameSessionRepository.deleteById(id);
    }

    // Clear expired game sessions
    public void deleteExpiredSessions() {
        deleteExpiredSessions();
    }
}
