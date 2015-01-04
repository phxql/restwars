package restwars.rest.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import restwars.rest.mapper.ShipMapper;
import restwars.restapi.dto.metadata.ShipMetadataResponse;
import restwars.service.ship.ShipType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(value = "/ship", hidden = true)
public class ShipMetadataSubResource {
    @Inject
    public ShipMetadataSubResource() {
    }

    @GET
    @ApiOperation("Lists all ships")
    public List<ShipMetadataResponse> all() {
        return Stream.of(ShipType.values()).map(ShipMapper::fromShipType).collect(Collectors.toList());
    }
}
