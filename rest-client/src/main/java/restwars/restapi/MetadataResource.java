package restwars.restapi;


import restwars.restapi.dto.metadata.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/metadata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MetadataResource {
    @GET
    @Path("/building")
    List<BuildingMetadataResponse> allBuildings(@QueryParam("level") int level);

    @GET
    @Path("/ship")
    List<ShipMetadataResponse> allShips();

    @GET
    @Path("/technology")
    List<TechnologyMetadataResponse> allTechnologies(@QueryParam("level") int level);

    @GET
    @Path("/flight/type")
    List<FlightTypeMetadataResponse> allFlightTypes();

    @GET
    @Path("/configuration")
    ConfigurationMetadataResponse getConfiguration();
}
