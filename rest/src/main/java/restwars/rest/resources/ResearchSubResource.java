package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.technology.ResearchRequest;
import restwars.rest.api.technology.ResearchResponse;
import restwars.rest.resources.param.LocationParam;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.technology.Research;
import restwars.service.technology.TechnologyService;
import restwars.service.technology.TechnologyType;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchSubResource {
    private final TechnologyService technologyService;
    private final PlanetService planetService;

    public ResearchSubResource(TechnologyService technologyService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.technologyService = Preconditions.checkNotNull(technologyService, "technologyService");
    }

    @GET
    public List<ResearchResponse> getResearch(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        List<Research> researches = technologyService.findResearchesOnPlanet(planet);

        return researches.stream().map(ResearchResponse::fromResearch).collect(Collectors.toList());
    }

    @POST
    public ResearchResponse research(@Auth Player player, @PathParam("location") LocationParam location, @Valid ResearchRequest data) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(data, "data");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);

        TechnologyType type = Helper.parseTechnologyType(data.getType());
        try {
            Research research = technologyService.researchTechnology(player, planet, type);
            return ResearchResponse.fromResearch(research);
        } catch (InsufficientResourcesException e) {
            throw new WebApplicationException(); // TODO: Create real exception
        }
    }
}
