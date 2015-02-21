package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import restwars.model.building.BuildingType;
import restwars.model.resource.Resources;
import restwars.rest.mapper.PrerequisitesMapper;
import restwars.rest.mapper.ResourcesMapper;
import restwars.restapi.dto.ResourcesResponse;
import restwars.restapi.dto.metadata.BuildingMetadataResponse;
import restwars.restapi.dto.metadata.BuildingsMetadataResponse;
import restwars.service.building.BuildingService;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.resource.ResourceService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Subresource for building metadata.
 */
@Api(value = "/building", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BuildingMetadataSubResource {
    private final BuildingService buildingService;
    private final BuildingMechanics buildingMechanics;
    private final ResourceService resourceService;

    @Inject
    public BuildingMetadataSubResource(BuildingService buildingService, BuildingMechanics buildingMechanics, ResourceService resourceService) {
        this.resourceService = Preconditions.checkNotNull(resourceService, "resourceService");
        this.buildingMechanics = Preconditions.checkNotNull(buildingMechanics, "buildingMechanics");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    /**
     * Lists metadata for all buildings.
     *
     * @param level Building level for which the resource cost and build time should be returned.
     * @return Metadata for all buildings.
     */
    @GET
    @ApiOperation("Lists all buildings")
    public BuildingsMetadataResponse all(@QueryParam("level") @ApiParam(value = "Building level", defaultValue = "1") int level) {
        int sanitizedLevel = Math.max(level, 1);

        return new BuildingsMetadataResponse(Stream.of(BuildingType.values())
                .map(t -> new BuildingMetadataResponse(
                        t.name(), sanitizedLevel, buildingService.calculateBuildTimeWithoutBonuses(t, sanitizedLevel),
                        ResourcesMapper.fromResources(buildingService.calculateBuildCostWithoutBonuses(t, sanitizedLevel)),
                        t.getDescription(), PrerequisitesMapper.fromPrerequisites(buildingMechanics.getPrerequisites(t)),
                        getResourcesPerRound(t, sanitizedLevel), getBuildingBuildTimeSpeedUp(t, sanitizedLevel),
                        getResearchTimeSpeedup(t, sanitizedLevel), getShipBuildTimeSpeedUp(t, sanitizedLevel)
                ))
                .collect(Collectors.toList()));
    }

    @Nullable
    private ResourcesResponse getResourcesPerRound(BuildingType type, int level) {
        Resources resources = resourceService.calculateGatheredResourcesWithoutBonus(type, level);

        if (resources.isEmpty()) {
            return null;
        }

        return ResourcesMapper.fromResources(resources);
    }

    @Nullable
    private Double getShipBuildTimeSpeedUp(BuildingType type, int level) {
        if (type != BuildingType.SHIPYARD) {
            return null;
        }

        return buildingMechanics.calculateShipBuildTimeSpeedup(level) * 100;
    }

    @Nullable
    private Double getResearchTimeSpeedup(BuildingType type, int level) {
        if (type != BuildingType.RESEARCH_CENTER) {
            return null;
        }

        return buildingMechanics.calculateResearchTimeSpeedup(level) * 100;
    }

    @Nullable
    private Double getBuildingBuildTimeSpeedUp(BuildingType type, int level) {
        if (type != BuildingType.COMMAND_CENTER) {
            return null;
        }

        return buildingMechanics.calculateBuildingBuildTimeSpeedup(level) * 100;
    }
}
