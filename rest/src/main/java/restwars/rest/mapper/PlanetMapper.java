package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.planet.PlanetResponse;
import restwars.restapi.dto.planet.PlanetScanResponse;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetWithOwner;

/**
 * Maps planet entities to DTOs and vice versa.
 */
public final class PlanetMapper {
    private PlanetMapper() {
    }

    public static PlanetResponse fromPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return new PlanetResponse(
                planet.getLocation().toString(), planet.getResources().getCrystals(), planet.getResources().getGas(), planet.getResources().getEnergy()
        );
    }

    public static PlanetScanResponse fromPlanetWithOwner(PlanetWithOwner planetWithOwner) {
        return new PlanetScanResponse(planetWithOwner.getPlanet().getLocation().toString(), planetWithOwner.getOwner().getUsername());
    }

}
