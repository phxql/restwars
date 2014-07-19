package restwars.service.building;

import restwars.service.planet.Planet;
import restwars.service.resource.Resources;

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
     * @throws InsufficientResourcesException If the given planet has not enough resources for the construction.
     */
    ConstructionSite constructBuilding(Planet planet, BuildingType type) throws InsufficientResourcesException;

    /**
     * Upgrades the given building on the given planet.
     *
     * @param planet Planet.
     * @param type   Building type.
     * @return Construction site for the building.
     *
     * @throws BuildingNotFoundException If a non-existent building should be upgraded.
     * @throws InsufficientResourcesException If the given planet has not enough resources for the upgrade.
     */
    ConstructionSite upgradeBuilding(Planet planet, BuildingType type) throws BuildingNotFoundException, InsufficientResourcesException;

    /**
     * Constructs or upgrades the given building on the given planet.
     *
     * @param planet Planet.
     * @param type   Building type.
     * @return Construction site for the building.
     * @throws InsufficientResourcesException If the given planet has not enough resources for the upgrade or construction.
     */
    ConstructionSite constructOrUpgradeBuilding(Planet planet, BuildingType type) throws InsufficientResourcesException;

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
     * @return Build cost.
     */
    Resources calculateBuildCost(BuildingType type, int level);

    /**
     * Finishes all construction sites.
     */
    void finishConstructionSites();
}
