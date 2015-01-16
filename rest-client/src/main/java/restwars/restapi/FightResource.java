package restwars.restapi;

import restwars.restapi.dto.ship.FightResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/fight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface FightResource {
    @GET
    @Path("/{id}")
    public FightResponse getFight(@PathParam("id") String id);

    @GET
    @Path("/own")
    List<FightResponse> ownFights(@QueryParam("since") long round);
}

