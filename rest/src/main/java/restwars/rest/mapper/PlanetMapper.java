package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.restapi.dto.planet.PlanetResponse;
import restwars.service.planet.Planet;

public final class PlanetMapper {
    private PlanetMapper() {
    }

    public static PlanetResponse fromPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return new PlanetResponse(
                planet.getLocation().toString(), planet.getResources().getCrystals(), planet.getResources().getGas(), planet.getResources().getEnergy()
        );
    }

}
