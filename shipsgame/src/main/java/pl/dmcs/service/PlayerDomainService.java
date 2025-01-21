package pl.dmcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dmcs.domain.PlayerDomain;
import pl.dmcs.repository.PlayerDomainRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerDomainService {

    private final PlayerDomainRepository playerDomainRepository;

    @Autowired
    public PlayerDomainService(PlayerDomainRepository playerDomainRepository) {
        this.playerDomainRepository = playerDomainRepository;
    }

    // Save a new PlayerDomain
    public PlayerDomain savePlayer(PlayerDomain playerDomain) {
        return playerDomainRepository.save(playerDomain);
    }

    // Retrieve a PlayerDomain by ID
    public Optional<PlayerDomain> getPlayerById(Long id) {
        return playerDomainRepository.findById(id);
    }

    // Retrieve all PlayerDomain records
    public List<PlayerDomain> getAllPlayers() {
        return playerDomainRepository.findAll();
    }

    // Update an existing PlayerDomain
    public PlayerDomain updatePlayer(Long id, PlayerDomain updatedPlayer) {
        return playerDomainRepository.findById(id)
                .map(existingPlayer -> {
                    existingPlayer.setBoard(updatedPlayer.getBoard());
                    existingPlayer.setBot(updatedPlayer.isBot());
                    existingPlayer.setShipSizes(updatedPlayer.getShipSizes());
                    existingPlayer.setSunkenShips(updatedPlayer.getSunkenShips());
                    return playerDomainRepository.save(existingPlayer);
                })
                .orElseThrow(() -> new IllegalArgumentException("PlayerDomain with ID " + id + " not found"));
    }

    // Delete a PlayerDomain by ID
    public void deletePlayer(Long id) {
        if (playerDomainRepository.existsById(id)) {
            playerDomainRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("PlayerDomain with ID " + id + " does not exist");
        }
    }
}
