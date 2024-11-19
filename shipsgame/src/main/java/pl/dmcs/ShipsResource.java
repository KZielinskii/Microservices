package pl.dmcs;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.dmcs.Player;
@Path("/getship")
public class ShipsResource {
    Player player = new Player();
    Player ai = new Player();
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        player.initialize();
        player.setupShips();
        ai.initialize();
        ai.setupShips();
        for (int[] row : player.getBoard()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }

        System.out.println("AI Board:");
        for (int[] row : ai.getBoard()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        return "Hello from Quarkus REST";
    }
}
