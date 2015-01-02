package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import restwars.rest.api.ResourcesResponse;
import restwars.rest.api.metadata.TechnologyMetadataResponse;
import restwars.service.technology.TechnologyService;
import restwars.service.technology.TechnologyType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(value = "/technology", hidden = true)
public class TechnologyMetadataSubResource {
    private final TechnologyService technologyService;

    @Inject
    public TechnologyMetadataSubResource(TechnologyService technologyService) {
        this.technologyService = Preconditions.checkNotNull(technologyService, "technologyService");
    }

    @GET
    @ApiOperation("Lists all technologies")
    public List<TechnologyMetadataResponse> all(@QueryParam("level") @ApiParam(value = "Building level", defaultValue = "1") int level) {
        int sanitizedLevel = Math.max(level, 1);

        return Stream.of(TechnologyType.values())
                .map(t -> new TechnologyMetadataResponse(
                        t.name(), sanitizedLevel, technologyService.calculateResearchTime(t, sanitizedLevel),
                        ResourcesResponse.fromResources(technologyService.calculateResearchCost(t, sanitizedLevel))
                ))
                .collect(Collectors.toList());
    }
}
