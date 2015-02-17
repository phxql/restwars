package restwars.service.resource;

import restwars.model.building.BuildingType;
import restwars.model.planet.Planet;
import restwars.model.resource.Resources;
import restwars.model.technology.Technologies;

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
     * @param technologies Technologies.
     * @return Gathered resources in one round.
     */
    Resources calculateGatheredResources(BuildingType type, int level, Technologies technologies);

    /**
     * Calculates the resources which are gathered from the given building type with the given level in one round without applying bonus.
     *
     * @param type  Building type.
     * @param level Level.
     * @return Gathered resources in one round.
     */
    Resources calculateGatheredResourcesWithoutBonus(BuildingType type, int level);
}