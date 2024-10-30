package pl.dmcs.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.dmcs.domain.Game;
import pl.dmcs.domain.Score;
import pl.dmcs.service.ScoreService;

@Path("/hello")
public class ExampleResource {
    @Inject
    ScoreService scoreService;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Score sco = new Score();
        sco.setUsername("Testing");
        sco.setScore((long)1005);
        sco.setUserId((long)7);
        Game gam = new Game();
        gam.setName("RollingStone");
        gam.setId((long)1);
        sco.setGame(gam);
        scoreService.save(sco);
        return "Hello from Quarkus REST";
    }
}
