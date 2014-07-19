package restwars.service.resource;

import restwars.service.building.Building;
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

    /**
     * Calculates the resources which are gathered from the given building in one round.
     *
     * @param building Building.
     * @return Gathered resources.
     */
    Resources calculateGatheredResources(Building building);
}