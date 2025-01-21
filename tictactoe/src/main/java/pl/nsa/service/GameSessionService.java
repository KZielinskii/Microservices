package pl.nsa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.nsa.GameScoreCalculator;
import pl.nsa.GameSession;
import pl.nsa.domain.GameSessionDomain;
import pl.nsa.repository.GameSessionRepository;


import java.util.Optional;

@ApplicationScoped
public class GameSessionService {

    @Inject
    GameSessionRepository gameSessionRepository;

    @Inject
    GameScoreCalculator gameScoreCalculator;

    // Create or update a game session
    @Transactional
    public void saveGameSession(GameSession gameSession, String sessionId) {
        GameSessionDomain gameSessionDomain = new GameSessionDomain(gameSession, sessionId);
        gameSessionRepository.save(gameSessionDomain);
    }

    public void modifyGameSession(GameSession gameSession, String sessionId) {
        GameSessionDomain gameSessionDomain = gameSessionRepository.findBySessionId(sessionId);
        GameSessionDomain gameSessionDomain1 = new GameSessionDomain(gameSessionDomain,gameSession,sessionId);
        System.out.println(gameSessionDomain1.toString());
        gameSessionRepository.save(gameSessionDomain1);
    }

    public GameSession toGameSession(GameSessionDomain gameSessionDomain) {
        GameSession gameSession = new GameSession();
        gameSession.setBoard(gameSessionDomain.getBoard());
        gameSession.setDifficulty(gameSessionDomain.getDifficulty());
        gameSession.setPlayerSymbol(gameSessionDomain.getPlayerSymbol());
        gameSession.setAiSymbol(gameSessionDomain.getAiSymbol());
        gameSession.setUserId(gameSessionDomain.getUserId());
        gameSession.setMoveCounter(gameSessionDomain.getMoveCounter());
        gameSession.setExpirationTime(gameSessionDomain.getExpirationTime());
        gameSession.setGameScoreCalculator(gameScoreCalculator);
        return gameSession;
    }

    public GameSession findBySessionId(String sessionId) {
        GameSessionDomain gameSessionDomain = gameSessionRepository.findBySessionId(sessionId);
        System.out.println(gameSessionDomain.toString());
        return this.toGameSession(gameSessionDomain);
    }

    // Retrieve a game session by ID
    public Optional<GameSessionDomain> findById(Long id) {
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
