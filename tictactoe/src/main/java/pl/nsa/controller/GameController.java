package pl.nsa.controller;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.nsa.*;

import java.util.Arrays;
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
    public Response startGame(@QueryParam("difficulty") String difficulty, @QueryParam("symbol") String symbol, @QueryParam("firstMove") String firstMove) {
        System.out.println("Starting game with difficulty: " + difficulty);
        GameSession gameSession = new GameSession();
        gameSession.setGameScoreCalculator(gameScoreCalculator);
        gameSession.initializeGame(Difficulty.valueOf(difficulty.toUpperCase()));
        gameSession.setPlayerSymbol(symbol.charAt(0));
        String sessionId = UUID.randomUUID().toString();
        sessionManager.createSession(sessionId, gameSession);
        if(firstMove.equals("AI")) {
            gameSession.makeAIMove();
        }
        String[] board = new String[3];
        for (int i = 0; i < 3; i++) {
            board[i] = "\""+ Arrays.toString(gameSession.getBoard()[i]) + "\"";
            board[i] = board[i].replace("[","").replace("]","").replace(",","").replace(" ","");
        }
        return Response.ok("{\"sessionId\": \"" + sessionId+ "\",\"board\":"+ Arrays.toString(board) + "}" ).build();
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response playerMove(@HeaderParam("sessionId") String sessionId, PlayerMove move) {
        System.out.println("działa");
        System.out.println("Player move: " + move.getX() + ", " + move.getY());
        GameSession gameSession = sessionManager.getSession(sessionId);
        if (gameSession == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Session not found").build();
        }
        if (!gameSession.makePlayerMove(move)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new GameStatus(gameSession.getBoard(), "Invalid move", 0))
                    .build();
        }
        gameSession.updateMoveCounter();
        if (gameSession.checkWinner()) {
            gameSession.calculateScore(GameResult.WIN);
            return Response.ok(new GameStatus(gameSession.getBoard(), "Player wins!",gameSession.calculateScore(GameResult.WIN))).build();
        }
        if (gameSession.checkDraw()) {
            gameSession.calculateScore(GameResult.DRAW);
            return Response.ok(new GameStatus(gameSession.getBoard(), "Draw!",gameSession.calculateScore(GameResult.DRAW))).build();
        }

        gameSession.makeAIMove();
        if (gameSession.checkWinner()) {
            return Response.ok(new GameStatus(gameSession.getBoard(), "AI wins!",gameSession.calculateScore(GameResult.LOSE))).build();
        }
        if (gameSession.checkDraw()) {
            gameSession.calculateScore(GameResult.DRAW);
            return Response.ok(new GameStatus(gameSession.getBoard(), "Draw!",gameSession.calculateScore(GameResult.DRAW))).build();
        }
        return Response.ok(new GameStatus(gameSession.getBoard(), "In progress",gameSession.calculateScore(GameResult.INPROGRESS))).build();
    }
}
