package restwars.service.resource;

import restwars.service.building.BuildingType;
import restwars.service.planet.Planet;
import restwars.service.technology.Technologies;

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
     * Calculates the resources which are gathered from the given building type with the given level in one round.
     *
     * @param type  Building type.
     * @param level Level.
     * @param technologies
     * @return Gathered resources in one round.
     */
    Resources calculateGatheredResources(BuildingType type, int level, Technologies technologies);
}