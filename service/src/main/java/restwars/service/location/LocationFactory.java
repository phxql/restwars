package restwars.service.location;

import restwars.service.planet.Location;

/**
 *
 */
public interface LocationFactory {
    /**
     * Creates a random location.
     *
     * @param maxGalaxy      Maximum number for the galaxy.
     * @param maxSolarSystem Maximum number for the solar system.
     * @param maxPlanet      Maximum number for the planet.
     * @return Random location.
     */
    Location random(int maxGalaxy, int maxSolarSystem, int maxPlanet);
}
