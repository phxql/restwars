package restwars.service.planet;

import com.google.common.base.Objects;
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

    public static Location parse(String value) {
        Preconditions.checkNotNull(value, "value");

        String[] parts = value.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Cannot parse '" + value + "'");
        }

        int galaxy = Integer.parseInt(parts[0]);
        int solarSystem = Integer.parseInt(parts[1]);
        int planet = Integer.parseInt(parts[2]);

        return new Location(galaxy, solarSystem, planet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location that = (Location) o;

        return Objects.equal(this.galaxy, that.galaxy) &&
                Objects.equal(this.solarSystem, that.solarSystem) &&
                Objects.equal(this.planet, that.planet);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(galaxy, solarSystem, planet);
    }

    @Override
    public String toString() {
        return galaxy + "." + solarSystem + "." + planet;
    }
}
