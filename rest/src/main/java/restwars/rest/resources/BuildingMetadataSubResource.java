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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    @ApiOperation("Lists metadata for all buildings")
    public BuildingsMetadataResponse all(@QueryParam("level") @ApiParam(value = "Building level") @DefaultValue("1") int level) {
        int sanitizedLevel = Math.max(level, 1);

        return new BuildingsMetadataResponse(Stream.of(BuildingType.values())
                .map(t -> getMetadata(t, sanitizedLevel)).collect(Collectors.toList()));
    }

    @GET
    @Path("/range")
    @ApiOperation("Lists metadata for all buildings within a level range")
    public BuildingsMetadataResponse all(
            @QueryParam("levelFrom") @ApiParam(value = "Start of building level range (inclusive)") int levelFrom,
            @QueryParam("levelTo") @ApiParam(value = "End of building level range (inclusive)") int levelTo
    ) {
        int sanitizedLevelFrom = Math.max(levelFrom, 1);
        int sanitizedLevelTo = Math.max(levelTo, 1);

        if (sanitizedLevelTo < sanitizedLevelFrom) {
            throw new ParameterValueWebException("levelTo must be >= levelFrom");
        }

        List<BuildingMetadataResponse> result = IntStream.range(sanitizedLevelFrom, sanitizedLevelTo + 1).
                boxed().flatMap(level -> Stream.of(BuildingType.values()).map(t -> getMetadata(t, level)))
                .collect(Collectors.toList());

        return new BuildingsMetadataResponse(result);
    }

    /**
     * Lists metadata for the building with the given type.
     *
     * @param type  Building type.
     * @param level Building level for which the building metadata should be returned.
     * @return Metadata for the building.
     */
    @GET
    @Path("/{type}")
    @ApiOperation("Lists metadata for a building")
    public BuildingMetadataResponse one(
            @PathParam("type") @ApiParam(value = "Building type") String type,
            @QueryParam("level") @ApiParam(value = "Building level") @DefaultValue("1") int level
    ) {
        int sanitizedLevel = Math.max(level, 1);

        try {
            BuildingType buildingType = BuildingType.valueOf(type);

            return getMetadata(buildingType, sanitizedLevel);
        } catch (IllegalArgumentException e) {
            throw new BuildingTypeNotFoundWebException();
        }
    }

    @GET
    @Path("/{type}/range")
    @ApiOperation("Lists metadata for a building within a level range")
    public BuildingsMetadataResponse oneRange(
            @PathParam("type") @ApiParam(value = "Building type") String type,
            @QueryParam("levelFrom") @ApiParam(value = "Start of building level range (inclusive)") int levelFrom,
            @QueryParam("levelTo") @ApiParam(value = "End of building level range (inclusive)") int levelTo
    ) {
        int sanitizedLevelFrom = Math.max(levelFrom, 1);
        int sanitizedLevelTo = Math.max(levelTo, 1);

        if (sanitizedLevelTo < sanitizedLevelFrom) {
            throw new ParameterValueWebException("levelTo must be >= levelFrom");
        }

        BuildingType buildingType;
        try {
            buildingType = BuildingType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new BuildingTypeNotFoundWebException();
        }

        List<BuildingMetadataResponse> result = IntStream.range(sanitizedLevelFrom, sanitizedLevelTo + 1).
                mapToObj(level -> getMetadata(buildingType, level))
                .collect(Collectors.toList());

        return new BuildingsMetadataResponse(result);
    }

    private BuildingMetadataResponse getMetadata(BuildingType type, int level) {
        return new BuildingMetadataResponse(
                type.name(), level, buildingService.calculateBuildTimeWithoutBonuses(type, level),
                ResourcesMapper.fromResources(buildingService.calculateBuildCostWithoutBonuses(type, level)),
                type.getDescription(), PrerequisitesMapper.fromPrerequisites(buildingMechanics.getPrerequisites(type)),
                getResourcesPerRound(type, level), getBuildingBuildTimeSpeedUp(type, level),
                getResearchTimeSpeedup(type, level), getShipBuildTimeSpeedUp(type, level)
        );
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
