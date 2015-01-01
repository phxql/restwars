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

    @Inject
    public MetadataResource(BuildingMetadataSubResource buildingMetadataSubResource) {
        this.buildingMetadataSubResource = buildingMetadataSubResource;
    }

    @Path("/building")
    @ApiOperation("Building metadata")
    public BuildingMetadataSubResource getBuildingMetadataSubResource() {
        return buildingMetadataSubResource;
    }
}
