package pl.dmcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dmcs.domain.GameManagerDomain;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface GameManagerRepository extends JpaRepository<GameManagerDomain, Long> {

    // Find by session ID
    GameManagerDomain findBySessionId(String sessionId);

    // Delete expired game sessions
    @Modifying
    @Query("DELETE FROM GameManagerDomain g WHERE g.expirationTime < :now")
    void deleteExpiredSessions(LocalDateTime now);
}
