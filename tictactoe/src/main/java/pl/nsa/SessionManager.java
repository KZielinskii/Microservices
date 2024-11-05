package pl.nsa;


import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SessionManager {
    private ConcurrentHashMap<String, GameSession> sessions = new ConcurrentHashMap<>();

    public void createSession(String sessionId, GameSession gameSession) {
        sessions.put(sessionId, gameSession);
    }

    public GameSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}