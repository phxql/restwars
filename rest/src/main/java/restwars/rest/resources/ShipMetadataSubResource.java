package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import restwars.model.ship.ShipType;
import restwars.rest.mapper.ShipMapper;
import restwars.restapi.dto.metadata.ShipMetadataResponse;
import restwars.restapi.dto.metadata.ShipsMetadataResponse;
import restwars.service.mechanics.ShipMechanics;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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


    /**
     * Lists metadata for the ship with the given type.
     *
     * @param type Building type.
     * @return Metadata for the ship.
     */
    @GET
    @Path("/{type}")
    @ApiOperation("Lists metadata for a ship")
    public ShipMetadataResponse one(
            @PathParam("type") @ApiParam(value = "Ship type") String type
    ) {
        try {
            ShipType shipType = ShipType.valueOf(type);

            return getMetadata(shipType);
        } catch (IllegalArgumentException e) {
            throw new ShipTypeNotFoundWebException();
        }
    }

    @GET
    @ApiOperation("Lists metadata all ships")
    public ShipsMetadataResponse all() {
        return new ShipsMetadataResponse(
                Stream.of(ShipType.values()).map(s -> getMetadata(s)).collect(Collectors.toList())
        );
    }

    private ShipMetadataResponse getMetadata(ShipType s) {
        return ShipMapper.fromShipType(s, shipMechanics);
    }
}
