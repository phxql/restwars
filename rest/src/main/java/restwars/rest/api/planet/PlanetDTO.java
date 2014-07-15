package restwars.rest.api.planet;

import com.google.common.base.Preconditions;
import restwars.service.planet.Planet;

public class PlanetDTO {
    private final String location;

    private final long crystal;

    private final long gas;

    private final long energy;

    public PlanetDTO(String location, long crystal, long gas, long energy) {
        this.location = location;
        this.crystal = crystal;
        this.gas = gas;
        this.energy = energy;
    }

    public String getLocation() {
        return location;
    }

    public long getCrystal() {
        return crystal;
    }

    public long getGas() {
        return gas;
    }

    public long getEnergy() {
        return energy;
    }

    public static PlanetDTO fromPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return new PlanetDTO(
                planet.getLocation().toString(), planet.getCrystals(), planet.getGas(), planet.getEnergy()
        );
    }
}
