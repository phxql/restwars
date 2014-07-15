package restwars.rest.resources;

import com.google.common.base.Preconditions;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

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
}
