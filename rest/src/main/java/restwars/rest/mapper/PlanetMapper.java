package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.planet.Planet;
import restwars.model.planet.PlanetWithOwner;
import restwars.restapi.dto.planet.PlanetResponse;
import restwars.restapi.dto.planet.PlanetScanResponse;

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
