package pl.dmcs;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.dmcs.Player;
import pl.dmcs.SessionManager;

import java.util.UUID;

@Path("/shipsgame")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipsSessionResource {
    @Inject
    SessionManager sessionManager;
    @POST
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame() {
        GameSession gameSession = new GameSession();
        String sessionId = UUID.randomUUID().toString();
        sessionManager.createSession(sessionId, gameSession);
        return Response.ok(sessionId).build();
    }
}

