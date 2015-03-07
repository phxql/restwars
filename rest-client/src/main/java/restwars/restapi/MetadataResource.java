package restwars.restapi;


import restwars.restapi.dto.metadata.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/metadata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MetadataResource {
    @GET
    @Path("/building")
    BuildingsMetadataResponse allBuildings(@QueryParam("level") int level);

    @GET
    @Path("/building/{type}")
    BuildingMetadataResponse building(@PathParam("type") String type, @QueryParam("level") int level);

    @GET
    @Path("/ship/{type}")
    ShipMetadataResponse ship(@PathParam("type") String type);

    @GET
    @Path("/ship")
    ShipsMetadataResponse allShips();

    @GET
    @Path("/technology")
    TechnologiesMetadataResponse allTechnologies(@QueryParam("level") int level);

    @GET
    @Path("/flight/type")
    FlightTypesMetadataResponse allFlightTypes();

    @GET
    @Path("/event/type")
    EventTypesMetadataResponse allEventTypes();

    @GET
    @Path("/configuration")
    ConfigurationMetadataResponse getConfiguration();
}
