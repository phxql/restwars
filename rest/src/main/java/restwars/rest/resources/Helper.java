package restwars.rest.resources;

import com.google.common.base.Preconditions;
import restwars.service.building.BuildingType;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.technology.TechnologyType;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Helper {
    private Helper() {
    }

    /**
     * Finds the planet with the given location and ensures that is it owned from the given player.
     *
     * @param planetService Planet service.
     * @param location      Location.
     * @param owner         Owner.
     * @return Planet.
     * @throws PlanetNotFoundWebException If the planet wasn't found.
     * @throws NotYourPlanetWebException  If the planet was found, but isn't owned from the given player.
     */
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

    public static TechnologyType parseTechnologyType(String value) {
        Preconditions.checkNotNull(value, "value");

        try {
            return TechnologyType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    public static <From, To> List<To> mapToList(Collection<From> input, Function<From, To> mapper) {
        Preconditions.checkNotNull(input, "input");
        Preconditions.checkNotNull(mapper, "mapper");

        return input.stream().map(mapper).collect(Collectors.toList());
    }
}
