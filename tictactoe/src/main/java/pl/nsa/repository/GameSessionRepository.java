package pl.nsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nsa.domain.GameSessionDomain;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSessionDomain, Long> {

    // Find by sessionId
    GameSessionDomain findBySessionId(String sessionId);

    @Modifying
    @Query("DELETE FROM GameSessionDomain g WHERE g.expirationTime < :now")
    void deleteExpiredSessions(LocalDateTime now);
}
