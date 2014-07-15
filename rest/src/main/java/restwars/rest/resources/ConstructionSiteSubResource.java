package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.building.ConstructionSiteDTO;
import restwars.rest.resources.param.LocationParam;
import restwars.service.building.BuildingService;
import restwars.service.building.ConstructionSite;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructionSiteSubResource {
    private final PlanetService planetService;
    private final BuildingService buildingService;

    public ConstructionSiteSubResource(PlanetService planetService, BuildingService buildingService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    @GET
    public List<ConstructionSiteDTO> getConstructionSites(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Planet planet = Helper.findPlanetWithLocationAndOwner(planetService, location.getValue(), player);
        List<ConstructionSite> constructionSites = buildingService.findConstructionSitesOnPlanet(planet);

        return constructionSites.stream().map(ConstructionSiteDTO::fromConstructionSite).collect(Collectors.toList());
    }
}
