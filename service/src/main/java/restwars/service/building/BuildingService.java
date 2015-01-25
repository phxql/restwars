package restwars.service.building;

import restwars.service.Buildings;
import restwars.service.planet.Planet;
import restwars.service.resource.Resources;
import restwars.service.technology.Technologies;

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
    Buildings findBuildingsOnPlanet(Planet planet);

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
    void manifestBuilding(Planet planet, BuildingType type, int level);

    /**
     * Calculates the build time for the given type and level of building.
     *
     * @param type         Type of building.
     * @param level        Level to build.
     * @param technologies Technologies of the player.
     * @param buildings    Buildings on the planet.
     * @return Build time in rounds.
     */
    long calculateBuildTime(BuildingType type, int level, Technologies technologies, Buildings buildings);

    /**
     * Calculates the build time for the given type and level of building without applying bonuses.
     *
     * @param type  Type of building.
     * @param level Level to build.
     * @return Build time in rounds.
     */
    long calculateBuildTimeWithoutBonuses(BuildingType type, int level);

    /**
     * Calculates the build cost for the given type and level of building.
     *
     * @param type         Type of building.
     * @param level        Level to build.
     * @param technologies
     * @return Build cost.
     */
    Resources calculateBuildCost(BuildingType type, int level, Technologies technologies);

    /**
     * Calculates the build cost for the given type and level of building without applying bonuses.
     *
     * @param type  Type of building.
     * @param level Level to build.
     * @return Build cost.
     */
    Resources calculateBuildCostWithoutBonuses(BuildingType type, int level);

    /**
     * Finishes all construction sites which are done in the current round.
     */
    void finishConstructionSites();
}
