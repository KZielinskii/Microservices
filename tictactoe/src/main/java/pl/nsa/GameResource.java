package pl.nsa;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

    @Inject
    GameService gameService;

    @POST
    @Path("/start")
    public Response startGame(@QueryParam("difficulty") String difficulty) {
        System.out.println("Starting game with difficulty: " + difficulty);
        gameService.initializeGame(Difficulty.valueOf(difficulty.toUpperCase()));
        return Response.ok(gameService.getBoard()).build();
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response playerMove(PlayerMove move) {
        System.out.println("działa");
        System.out.println("Player move: " + move.getX() + ", " + move.getY());

        if (!gameService.makePlayerMove(move)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new GameStatus(gameService.getBoard(), "Invalid move"))
                    .build();
        }
        if (gameService.checkWinner()) {
            return Response.ok(new GameStatus(gameService.getBoard(), "Player wins!")).build();
        }

        gameService.makeAIMove();
        if (gameService.checkWinner()) {
            return Response.ok(new GameStatus(gameService.getBoard(), "AI wins!")).build();
        }

        return Response.ok(new GameStatus(gameService.getBoard(), "In progress")).build();
    }
}
