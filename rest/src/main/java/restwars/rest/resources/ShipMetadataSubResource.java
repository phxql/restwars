package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import restwars.rest.mapper.ShipMapper;
import restwars.restapi.dto.metadata.ShipMetadataResponse;
import restwars.service.mechanics.ShipMechanics;
import restwars.service.ship.ShipType;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(value = "/ship", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShipMetadataSubResource {
    private final ShipMechanics shipMechanics;

    @Inject
    public ShipMetadataSubResource(ShipMechanics shipMechanics) {
        this.shipMechanics = Preconditions.checkNotNull(shipMechanics, "shipMechanics");
    }

    @GET
    @ApiOperation("Lists all ships")
    public List<ShipMetadataResponse> all() {
        return Stream.of(ShipType.values()).map(s -> ShipMapper.fromShipType(s, shipMechanics)).collect(Collectors.toList());
    }
}
