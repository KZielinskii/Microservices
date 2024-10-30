package pl.dmcs.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.dmcs.domain.Game;
import pl.dmcs.service.GameService;
import pl.dmcs.service.ScoreService;

@Path("/gameon")
public class GameResource {
    @Inject
    GameService gameService;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Game game = new Game();
        game.setName("HearthStone");
        gameService.save(game);
        return "Goodbye from Quarkus REST";
    }

}
