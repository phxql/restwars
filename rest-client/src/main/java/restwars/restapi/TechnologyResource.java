package restwars.restapi;

import restwars.restapi.dto.technology.TechnologiesResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/technology")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TechnologyResource {
    @GET
    TechnologiesResponse getTechnologies();
}
