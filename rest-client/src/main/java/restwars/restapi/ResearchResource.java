package restwars.restapi;

import restwars.restapi.dto.technology.ResearchRequest;
import restwars.restapi.dto.technology.ResearchResponse;
import restwars.restapi.dto.technology.ResearchesResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/planet/{location}/research")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ResearchResource {
    @GET
    ResearchesResponse getResearch(@PathParam("location") String location);

    @POST
    ResearchResponse research(@PathParam("location") String location, ResearchRequest data);
}
