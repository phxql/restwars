package restwars.service.location.impl;

import com.google.common.base.Preconditions;
import restwars.model.planet.Location;
import restwars.service.location.LocationFactory;

import java.util.Random;

public class LocationFactoryImpl implements LocationFactory {
    private final Random random = new Random();

    @Override
    public Location random(int maxGalaxy, int maxSolarSystem, int maxPlanet) {
        Preconditions.checkArgument(maxGalaxy > 0, "maxGalaxy must be > 0");
        Preconditions.checkArgument(maxSolarSystem > 0, "maxSolarSystem must be > 0");
        Preconditions.checkArgument(maxPlanet > 0, "maxPlanet must be > 0");

        int galaxy = random.nextInt(maxGalaxy) + 1;
        int solarSystem = random.nextInt(maxSolarSystem) + 1;
        int planet = random.nextInt(maxPlanet) + 1;

        return new Location(galaxy, solarSystem, planet);
    }
}
