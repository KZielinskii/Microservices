package pl.dmcs.endpoints;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.dmcs.service.ScoreService;

@Path("/testing")
public class TestScoreResource {
    @Inject
    ScoreService scoreService;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        scoreService.getScores(7,"HearthStone");
        return "Hello from Quarkus REST";
    }
}