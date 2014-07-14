package restwars.service.planet;

import com.google.common.base.Preconditions;

public class Location {
    private final int galaxy;

    private final int solarSystem;

    private final int planet;

    public Location(int galaxy, int solarSystem, int planet) {
        Preconditions.checkArgument(galaxy > 0, "galaxy must be > 0");
        Preconditions.checkArgument(solarSystem > 0, "solarSystem must be > 0");
        Preconditions.checkArgument(planet > 0, "planet must be > 0");

        this.galaxy = galaxy;
        this.solarSystem = solarSystem;
        this.planet = planet;
    }

    public int getGalaxy() {
        return galaxy;
    }

    public int getSolarSystem() {
        return solarSystem;
    }

    public int getPlanet() {
        return planet;
    }

    @Override
    public String toString() {
        return galaxy + "." + solarSystem + "." + planet;
    }
}
