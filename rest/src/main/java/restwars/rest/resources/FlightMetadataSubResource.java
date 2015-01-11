package restwars.rest.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import restwars.rest.mapper.FlightMapper;
import restwars.restapi.dto.metadata.FlightTypeMetadataResponse;
import restwars.service.ship.FlightType;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(value = "/flight", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlightMetadataSubResource {
    @Inject
    public FlightMetadataSubResource() {
    }

    @GET
    @Path("/type")
    @ApiOperation("Lists all flight types")
    public List<FlightTypeMetadataResponse> flightTypes() {
        return Stream.of(FlightType.values()).map(FlightMapper::fromFlightType).collect(Collectors.toList());
    }
}
