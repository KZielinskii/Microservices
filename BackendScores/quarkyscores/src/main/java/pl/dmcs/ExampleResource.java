package pl.dmcs;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
        scoreService.save(sco);
        return "Hello from Quarkus REST";
    }
}
