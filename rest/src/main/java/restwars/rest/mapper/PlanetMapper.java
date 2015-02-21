package restwars.rest.mapper;

import com.google.common.base.Preconditions;
import restwars.model.planet.Planet;
import restwars.model.planet.PlanetWithOwner;
import restwars.model.resource.Resources;
import restwars.restapi.dto.planet.PlanetResponse;
import restwars.restapi.dto.planet.PlanetScanResponse;
import restwars.restapi.dto.planet.PlanetWithResourcesResponse;

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

    public static PlanetWithResourcesResponse fromPlanet(Planet planet, Resources resourcesPerRound) {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(resourcesPerRound, "resourcesPerRound");

        return new PlanetWithResourcesResponse(
                planet.getLocation().toString(), planet.getResources().getCrystals(), planet.getResources().getGas(), planet.getResources().getEnergy(),
                ResourcesMapper.fromResources(resourcesPerRound)
        );
    }

    public static PlanetScanResponse fromPlanetWithOwner(PlanetWithOwner planetWithOwner) {
        return new PlanetScanResponse(planetWithOwner.getPlanet().getLocation().toString(), planetWithOwner.getOwner().getUsername());
    }

}
