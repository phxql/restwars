package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import restwars.model.technology.TechnologyType;
import restwars.rest.mapper.PrerequisitesMapper;
import restwars.rest.mapper.ResourcesMapper;
import restwars.restapi.dto.metadata.TechnologiesMetadataResponse;
import restwars.restapi.dto.metadata.TechnologyMetadataResponse;
import restwars.service.mechanics.TechnologyMechanics;
import restwars.service.technology.TechnologyService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Api(value = "/technology", hidden = true)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TechnologyMetadataSubResource {
    private final TechnologyService technologyService;
    private final TechnologyMechanics technologyMechanics;

    @Inject
    public TechnologyMetadataSubResource(TechnologyService technologyService, TechnologyMechanics technologyMechanics) {
        this.technologyMechanics = Preconditions.checkNotNull(technologyMechanics, "technologyMechanics");
        this.technologyService = Preconditions.checkNotNull(technologyService, "technologyService");
    }

    /**
     * Lists metadata for the technology with the given type.
     *
     * @param type  Technology type.
     * @param level Technology level for which the metadata should be returned.
     * @return Metadata for the technology.
     */
    @GET
    @Path("/{type}")
    @ApiOperation("Lists metadata for a technology")
    public TechnologyMetadataResponse one(
            @PathParam("type") @ApiParam(value = "Technology type") String type,
            @QueryParam("level") @ApiParam(value = "Technology level") @DefaultValue("1") int level
    ) {
        int sanitizedLevel = Math.max(level, 1);

        try {
            TechnologyType technologyType = TechnologyType.valueOf(type);

            return getMetadata(technologyType, sanitizedLevel);
        } catch (IllegalArgumentException e) {
            throw new TechnologyTypeNotFoundWebException(e);
        }
    }

    @GET
    @Path("/{type}/range")
    @ApiOperation("Lists metadata for a technology within a level range")
    public TechnologiesMetadataResponse oneRange(
            @PathParam("type") @ApiParam(value = "Technology type") String type,
            @QueryParam("levelFrom") @ApiParam(value = "Start of building level range (inclusive)") int levelFrom,
            @QueryParam("levelTo") @ApiParam(value = "End of building level range (inclusive)") int levelTo
    ) {
        int sanitizedLevelFrom = Math.max(levelFrom, 1);
        int sanitizedLevelTo = Math.max(levelTo, 1);

        if (sanitizedLevelTo < sanitizedLevelFrom) {
            throw new ParameterValueWebException("levelTo must be >= levelFrom");
        }

        TechnologyType technologyType;
        try {
            technologyType = TechnologyType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new TechnologyTypeNotFoundWebException(e);
        }

        List<TechnologyMetadataResponse> result = IntStream.range(sanitizedLevelFrom, sanitizedLevelTo + 1).
                mapToObj(level -> getMetadata(technologyType, level))
                .collect(Collectors.toList());

        return new TechnologiesMetadataResponse(result);
    }

    @GET
    @ApiOperation("Lists metadata for all technologies")
    public TechnologiesMetadataResponse all(@QueryParam("level") @ApiParam(value = "Building level", defaultValue = "1") int level) {
        int sanitizedLevel = Math.max(level, 1);

        return new TechnologiesMetadataResponse(Stream.of(TechnologyType.values())
                .map(t -> getMetadata(t, sanitizedLevel))
                .collect(Collectors.toList()));
    }

    @GET
    @Path("/range")
    @ApiOperation("Lists metadata for all technologies with a level range")
    public TechnologiesMetadataResponse allRange(
            @QueryParam("levelFrom") @ApiParam(value = "Start of building level range (inclusive)") int levelFrom,
            @QueryParam("levelTo") @ApiParam(value = "End of building level range (inclusive)") int levelTo
    ) {
        int sanitizedLevelFrom = Math.max(levelFrom, 1);
        int sanitizedLevelTo = Math.max(levelTo, 1);

        if (sanitizedLevelTo < sanitizedLevelFrom) {
            throw new ParameterValueWebException("levelTo must be >= levelFrom");
        }

        List<TechnologyMetadataResponse> result = IntStream.range(sanitizedLevelFrom, sanitizedLevelTo + 1).
                boxed().flatMap(level -> Stream.of(TechnologyType.values()).map(t -> getMetadata(t, level)))
                .collect(Collectors.toList());

        return new TechnologiesMetadataResponse(result);
    }

    private TechnologyMetadataResponse getMetadata(TechnologyType t, int sanitizedLevel) {
        return new TechnologyMetadataResponse(
                t.name(), sanitizedLevel, technologyService.calculateResearchTimeWithoutBonuses(t, sanitizedLevel),
                ResourcesMapper.fromResources(technologyService.calculateResearchCost(t, sanitizedLevel)),
                t.getDescription(), PrerequisitesMapper.fromPrerequisites(technologyMechanics.getPrerequisites(t)),
                getBuildCostReduction(t, sanitizedLevel), getFlightCostReduction(t, sanitizedLevel)
        );
    }

    @Nullable
    private Double getFlightCostReduction(TechnologyType type, int level) {
        if (type != TechnologyType.COMBUSTION_ENGINE) {
            return null;
        }

        return technologyMechanics.calculateCombustionFlightCostReduction(level) * 100;
    }

    @Nullable
    private Double getBuildCostReduction(TechnologyType type, int level) {
        if (type != TechnologyType.BUILDING_BUILD_COST_REDUCTION) {
            return null;
        }

        return technologyMechanics.calculateBuildCostReduction(level) * 100;
    }
}
