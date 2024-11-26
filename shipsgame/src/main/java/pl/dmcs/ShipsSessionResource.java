package pl.dmcs;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/shipsgame")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipsSessionResource {
    @Inject
    SessionManager sessionManager;
    @GET
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame() {
        GameManager gameSession = new GameManager();
        String sessionId = UUID.randomUUID().toString();
        gameSession.startGame();
        sessionManager.createSession(sessionId, gameSession);
        GameStart gameStart = new GameStart();
        gameStart.setSessionId(sessionId);
        gameStart.setHuman_board(gameSession.getHuman_board());
        return Response.ok(gameStart).build();
    }
    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeMove(@HeaderParam("sessionId") String sessionId, PlayerMove playerMove) {
        GameManager gameSession = sessionManager.getSession(sessionId);
        if (gameSession == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Session not found").build();
        }
        String result = gameSession.makeMove(playerMove.getX(), playerMove.getY());
        GameState gameState = new GameState();
        gameState.setBoard_human(gameSession.getHuman_board());
        gameState.setBoard_ai(gameSession.hideShips());
        gameState.setMessage(result);
        if (result.equals("Human won")) {
            return Response.ok(gameState).build();
        }
        if (result.equals("AI won")) {
            return Response.ok(gameState).build();
        }

        return Response.ok(gameState).build();
    }

}

