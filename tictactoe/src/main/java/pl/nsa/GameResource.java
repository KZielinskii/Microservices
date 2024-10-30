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
        gameService.initializeGame(Difficulty.valueOf(difficulty.toUpperCase()));
        return Response.ok(gameService.getBoard()).build();
    }

    @POST
    @Path("/move")
    public Response playerMove(PlayerMove move) {
        if (!gameService.makePlayerMove(move)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid move").build();
        }
        if (gameService.checkWinner()) {
            return Response.ok("Player wins!").build();
        }
        gameService.makeAIMove();
        if (gameService.checkWinner()) {
            return Response.ok("AI wins!").build();
        }
        return Response.ok(gameService.getBoard()).build();
    }
}
