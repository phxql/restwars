package restwars.restapi;

import restwars.restapi.dto.player.PlayerRankingResponse;
import restwars.restapi.dto.player.PlayerResponse;
import restwars.restapi.dto.player.PointsResponse;
import restwars.restapi.dto.player.RegisterPlayerRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PlayerResource {
    @GET
    PlayerResponse me();

    @POST
    Response register(RegisterPlayerRequest registration);

    @GET
    @Path("/points")
    PointsResponse points();

    @GET
    @Path("/ranking")
    PlayerRankingResponse playerRanking(int max);
}