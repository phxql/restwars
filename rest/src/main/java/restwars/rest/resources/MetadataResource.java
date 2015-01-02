package restwars.rest.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/metadata")
@Api(value = "/v1/metadata", description = "Game metadata")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MetadataResource {
    private final BuildingMetadataSubResource buildingMetadataSubResource;
    private final ShipMetadataSubResource shipMetadataSubResource;
    private final TechnologyMetadataSubResource technologyMetadataSubResource;

    @Inject
    public MetadataResource(BuildingMetadataSubResource buildingMetadataSubResource, ShipMetadataSubResource shipMetadataSubResource, TechnologyMetadataSubResource technologyMetadataSubResource) {
        this.buildingMetadataSubResource = buildingMetadataSubResource;
        this.shipMetadataSubResource = shipMetadataSubResource;
        this.technologyMetadataSubResource = technologyMetadataSubResource;
    }

    @Path("/building")
    @ApiOperation("Building metadata")
    public BuildingMetadataSubResource getBuildingMetadataSubResource() {
        return buildingMetadataSubResource;
    }

    @Path("/ship")
    @ApiOperation("Ship metadata")
    public ShipMetadataSubResource getShipMetadataSubResource() {
        return shipMetadataSubResource;
    }

    @Path("/technology")
    @ApiOperation("Technology metadata")
    public TechnologyMetadataSubResource getTechnologyMetadataSubResource() {
        return technologyMetadataSubResource;
    }
}
