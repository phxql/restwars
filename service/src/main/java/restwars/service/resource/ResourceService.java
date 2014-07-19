package restwars.service.resource;

import restwars.service.planet.Planet;

/**
 * Service to manage resources.
 */
public interface ResourceService {
    /**
     * Gathers resources on all planets.
     */
    void gatherResourcesOnAllPlanets();

    /**
     * Gathers resources for the given planet.
     *
     * @param planet Planet.
     */
    void gatherResources(Planet planet);
}