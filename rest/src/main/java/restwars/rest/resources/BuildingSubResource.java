package restwars.rest.resources;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.Auth;
import restwars.rest.api.building.BuildingDTO;
import restwars.rest.resources.param.LocationParam;
import restwars.service.building.Building;
import restwars.service.building.BuildingService;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuildingSubResource {
    private final PlanetService planetService;
    private final BuildingService buildingService;

    public BuildingSubResource(BuildingService buildingService, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    @GET
    public List<BuildingDTO> getBuildings(@Auth Player player, @PathParam("location") LocationParam location) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(location, "location");

        Optional<Planet> maybePlanet = planetService.findWithLocation(location.getValue());
        Planet planet = maybePlanet.orElseThrow(PlanetNotFoundWebException::new);
        if (!planet.isOwnedFrom(player)) {
            throw new NotYourPlanetWebException();
        }

        List<Building> buildings = buildingService.findWithPlanet(planet);

        return buildings.stream().map(BuildingDTO::fromBuilding).collect(Collectors.toList());
    }
}
