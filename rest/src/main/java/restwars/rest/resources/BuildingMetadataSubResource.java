package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import restwars.rest.mapper.ResourcesMapper;
import restwars.restapi.dto.metadata.BuildingMetadataResponse;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(value = "/building", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BuildingMetadataSubResource {
    private final BuildingService buildingService;

    @Inject
    public BuildingMetadataSubResource(BuildingService buildingService) {
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    @GET
    @ApiOperation("Lists all buildings")
    public List<BuildingMetadataResponse> all(@QueryParam("level") @ApiParam(value = "Building level", defaultValue = "1") int level) {
        int sanitizedLevel = Math.max(level, 1);

        return Stream.of(BuildingType.values())
                .map(t -> new BuildingMetadataResponse(
                        t.name(), sanitizedLevel, buildingService.calculateBuildTimeWithoutBonuses(t, sanitizedLevel),
                        ResourcesMapper.fromResources(buildingService.calculateBuildCostWithoutBonuses(t, sanitizedLevel)),
                        t.getDescription()
                ))
                .collect(Collectors.toList());
    }
}
