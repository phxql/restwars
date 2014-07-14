package restwars.rest.api.planet;

import com.google.common.base.Preconditions;
import restwars.service.planet.Planet;

public class PlanetDTO {
    private final String location;

    public PlanetDTO(String location) {
        this.location = Preconditions.checkNotNull(location, "location");
    }

    public String getLocation() {
        return location;
    }

    public static PlanetDTO fromPlanet(Planet planet) {
        return new PlanetDTO(planet.getLocation().toString());
    }
}
