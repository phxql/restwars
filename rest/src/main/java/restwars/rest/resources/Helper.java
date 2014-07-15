package restwars.rest.resources;

import com.google.common.base.Preconditions;
import restwars.service.building.BuildingType;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Optional;

public final class Helper {
    private Helper() {
    }

    public static Planet findPlanetWithLocationAndOwner(PlanetService planetService, Location location, Player owner) {
        Preconditions.checkNotNull(planetService, "planetService");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(owner, "owner");

        Optional<Planet> maybePlanet = planetService.findWithLocation(location);
        Planet planet = maybePlanet.orElseThrow(PlanetNotFoundWebException::new);
        if (!planet.isOwnedFrom(owner)) {
            throw new NotYourPlanetWebException();
        }

        return planet;
    }

    public static BuildingType parseBuildingType(String value) {
        Preconditions.checkNotNull(value, "value");

        try {
            return BuildingType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
