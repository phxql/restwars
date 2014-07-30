package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.technology.TechnologyResponse;
import restwars.service.player.Player;
import restwars.service.technology.Technology;
import restwars.service.technology.TechnologyService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/technology")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TechnologyResource {
    private final TechnologyService technologyService;

    public TechnologyResource(TechnologyService technologyService) {
        this.technologyService = Preconditions.checkNotNull(technologyService, "technologyService");
    }

    @GET
    public List<TechnologyResponse> getTechnologies(@Auth Player player) {
        Preconditions.checkNotNull(player, "player");

        List<Technology> technologies = technologyService.findAllForPlayer(player);
        return technologies.stream().map(TechnologyResponse::fromTechnology).collect(Collectors.toList());
    }
}
