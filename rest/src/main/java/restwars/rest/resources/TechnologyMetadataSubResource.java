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
import java.util.stream.Collectors;
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
            @QueryParam("level") @ApiParam(value = "Technology level", defaultValue = "1") int level
    ) {
        int sanitizedLevel = Math.max(level, 1);

        try {
            TechnologyType technologyType = TechnologyType.valueOf(type);

            return getMetadata(technologyType, sanitizedLevel);
        } catch (IllegalArgumentException e) {
            throw new TechnologyTypeNotFoundWebException();
        }
    }

    @GET
    @ApiOperation("Lists metadata for all technologies")
    public TechnologiesMetadataResponse all(@QueryParam("level") @ApiParam(value = "Building level", defaultValue = "1") int level) {
        int sanitizedLevel = Math.max(level, 1);

        return new TechnologiesMetadataResponse(Stream.of(TechnologyType.values())
                .map(t -> getMetadata(t, sanitizedLevel))
                .collect(Collectors.toList()));
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
