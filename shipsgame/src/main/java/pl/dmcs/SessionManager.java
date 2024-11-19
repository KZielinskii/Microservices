package pl.dmcs;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SessionManager {
    private ConcurrentHashMap<String, GameManager> sessions = new ConcurrentHashMap<>();

    public void createSession(String sessionId, GameManager gameSession) {
        sessions.put(sessionId, gameSession);
    }

    public GameManager getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
