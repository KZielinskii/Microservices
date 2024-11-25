package pl.nsa;


import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SessionManager {
    private final ConcurrentHashMap<String, GameSession> sessions = new ConcurrentHashMap<>();

    public void createSession(String sessionId, GameSession gameSession) {
        sessions.put(sessionId, gameSession);
    }

    public GameSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    @Scheduled(every = "10s") // Runs every 10 seconds
    void removeExpiredObjects() {
        LocalDateTime now = LocalDateTime.now();
        sessions.entrySet().removeIf(entry -> entry.getValue().isExpired(now));
        System.out.println("Removed expired sessions");
    }

}