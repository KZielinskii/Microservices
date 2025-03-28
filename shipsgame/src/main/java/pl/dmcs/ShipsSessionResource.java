package pl.dmcs;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.dmcs.service.GameManagerService;


import java.rmi.ServerError;
import java.util.UUID;

@Path("/shipsgame")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipsSessionResource {
    @Inject
    SessionManager sessionManager;

    @Inject
    GameManagerService gameManagerService;

    @POST
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startGame(StartingBoard startingBoard,@QueryParam("difficulty")String difficulty) {
        GameManager gameSession = new GameManager();
        String sessionId = UUID.randomUUID().toString();
        if(difficulty.equals("HARD"))
            gameSession.startGame("HARD");
        else
            gameSession.startGame(null);
        if(startingBoard != null) {
            if(startingBoard.player_board != null) {
                if (!gameSession.getHuman().validateBoard())
                    return Response.status(Response.Status.NOT_ACCEPTABLE).build();
                gameSession.getHuman().setBoard(startingBoard.getPlayer_board());
                gameSession.setHuman_board(startingBoard.getPlayer_board());
            }

        }

        sessionManager.createSession(sessionId, gameSession);
        GameStart gameStart = new GameStart();
        gameStart.setSessionId(sessionId);
        gameStart.setHuman_board(gameSession.getHuman_board());

        gameManagerService.saveGameSession(gameSession,sessionId);
        return Response.ok(gameStart).build();
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeMove(@HeaderParam("sessionId") String sessionId, PlayerMove playerMove) {
        GameManager gameSession = sessionManager.getSession(sessionId);
        if (gameSession == null) {
            gameSession = gameManagerService.findBySessionId(sessionId);
            if(gameSession == null)
                return Response.status(Response.Status.BAD_REQUEST).entity("Session not found").build();
        }
        String result = gameSession.makeMove(playerMove.getX(), playerMove.getY());
        GameState gameState = new GameState();
        gameState.setBoard_human(gameSession.getHuman_board());
        gameState.setBoard_ai(gameSession.hideShips());
        gameState.setMessage(result);
        gameManagerService.modifyGameSession(gameSession,sessionId);
        System.out.println(sessionId);
        if (result.equals("Human won")) {
            gameState.setScore(gameSession.calculateScore(gameSession.getHuman()));
            System.out.println(gameState.getScore());
            return Response.ok(gameState).build();
        }
        if (result.equals("AI won")) {
            gameState.setScore(0);
            return Response.ok(gameState).build();
        }

        return Response.ok(gameState).build();
    }

}

