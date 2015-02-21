package restwars.restapi;

import restwars.restapi.dto.ship.FightResponse;
import restwars.restapi.dto.ship.FightsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/fight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface FightResource {
    @GET
    @Path("/{id}")
    public FightResponse getFight(@PathParam("id") String id);

    @GET
    @Path("/own")
    FightsResponse ownFights(@QueryParam("since") long round);
}

