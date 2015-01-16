package restwars.restapi;

import restwars.restapi.dto.GeneralInformationResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RootResource {
    @GET
    GeneralInformationResponse generalInformation();
}
