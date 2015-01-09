package restwars.service.building;

import restwars.service.planet.Planet;
import restwars.service.resource.Resources;
import restwars.service.technology.Technology;

import java.util.List;

/**
 * Service to manage buildings.
 */
public interface BuildingService {
    /**
     * Returns the buildings on the given planet.
     *
     * @param planet Planet.
     * @return Buildings.
     */
    List<Building> findBuildingsOnPlanet(Planet planet);

    /**
     * Returns the construction sites on the given planet.
     *
     * @param planet Planet.
     * @return Construction sites.
     */
    List<ConstructionSite> findConstructionSitesOnPlanet(Planet planet);

    /**
     * Constructs the given building on the given planet.
     *
     * @param planet Planet.
     * @param type   Building type.
     * @return Construction site for the building.
     */
    ConstructionSite constructBuilding(Planet planet, BuildingType type) throws BuildingException;

    /**
     * Upgrades the given building on the given planet.
     *
     * @param planet Planet.
     * @param type   Building type.
     * @return Construction site for the building.
     */
    ConstructionSite upgradeBuilding(Planet planet, BuildingType type) throws BuildingException;

    /**
     * Constructs or upgrades the given building on the given planet.
     *
     * @param planet Planet.
     * @param type   Building type.
     * @return Construction site for the building.
     */
    ConstructionSite constructOrUpgradeBuilding(Planet planet, BuildingType type) throws BuildingException;

    /**
     * Adds the given building to the given planet.
     *
     * @param planet Planet.
     * @param type   Building type.
     * @param level  Building level.
     */
    void addBuilding(Planet planet, BuildingType type, int level);

    /**
     * Calculates the build time for the given type and level of building.
     *
     * @param type  Type of building.
     * @param level Level to build.
     * @return Build time in rounds.
     */
    long calculateBuildTime(BuildingType type, int level);

    /**
     * Calculates the build cost for the given type and level of building.
     *
     * @param type  Type of building.
     * @param level Level to build.
     * @param technologies
     * @return Build cost.
     */
    Resources calculateBuildCost(BuildingType type, int level, List<Technology> technologies);

    /**
     * Finishes all construction sites which are done in the current round.
     */
    void finishConstructionSites();
}
