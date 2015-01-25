package restwars.rest.resources;

import com.google.common.base.Preconditions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.Authorization;
import io.dropwizard.auth.Auth;
import restwars.rest.mapper.TechnologyMapper;
import restwars.restapi.dto.technology.TechnologyResponse;
import restwars.service.player.Player;
import restwars.service.technology.Technologies;
import restwars.service.technology.TechnologyService;
import restwars.util.Functional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/technology")
@Api(value = "/v1/technology", description = "Technology management", authorizations = {
        @Authorization("basicAuth")
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TechnologyResource {
    private final TechnologyService technologyService;

    @Inject
    public TechnologyResource(TechnologyService technologyService) {
        this.technologyService = Preconditions.checkNotNull(technologyService, "technologyService");
    }

    @GET
    @ApiOperation("Lists all researched technologies")
    public List<TechnologyResponse> getTechnologies(
            @Auth @ApiParam(access = "internal") Player player
    ) {
        Preconditions.checkNotNull(player, "player");

        Technologies technologies = technologyService.findAllForPlayer(player);
        return Functional.mapToList(technologies, TechnologyMapper::fromTechnology);
    }
}
