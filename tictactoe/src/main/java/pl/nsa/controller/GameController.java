package pl.nsa.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.nsa.*;

import java.util.UUID;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameController {

    @Inject
    SessionManager sessionManager;

    @Inject
    GameScoreCalculator gameScoreCalculator;

    @POST
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame(@QueryParam("difficulty") String difficulty, @QueryParam("symbol") String symbol) {
        System.out.println("Starting game with difficulty: " + difficulty);
        GameSession gameSession = new GameSession();
        gameSession.setGameScoreCalculator(gameScoreCalculator);
        gameSession.initializeGame(Difficulty.valueOf(difficulty.toUpperCase()));
        gameSession.setPlayerSymbol(symbol.charAt(0));
        String sessionId = UUID.randomUUID().toString();
        sessionManager.createSession(sessionId, gameSession);
        return Response.ok("{\"sessionId\": \"" + sessionId+ "\"}" ).build();
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response playerMove(@HeaderParam("sessionId") String sessionId, PlayerMove move) {
        System.out.println("działa");
        System.out.println("Player move: " + move.getX() + ", " + move.getY());
        GameSession gameSession = sessionManager.getSession(sessionId);
        System.out.println(gameSession.getGameScoreCalculator());
        if (gameSession == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Session not found").build();
        }
        if (!gameSession.makePlayerMove(move)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new GameStatus(gameSession.getBoard(), "Invalid move"))
                    .build();
        }
        gameSession.updateMoveCounter();
        if (gameSession.checkWinner()) {
            gameSession.calculateScore(GameResult.WIN);
            return Response.ok(new GameStatus(gameSession.getBoard(), "Player wins!")).build();
        }
        if (gameSession.checkDraw()) {
            gameSession.calculateScore(GameResult.DRAW);
            return Response.ok(new GameStatus(gameSession.getBoard(), "Draw!")).build();
        }

        gameSession.makeAIMove();
        if (gameSession.checkWinner()) {
            return Response.ok(new GameStatus(gameSession.getBoard(), "AI wins!")).build();
        }

        return Response.ok(new GameStatus(gameSession.getBoard(), "In progress")).build();
    }
}
