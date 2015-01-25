package restwars.service.mechanics;

import restwars.model.building.BuildingType;
import restwars.model.resource.Resources;

import java.util.Map;

/**
 * Planet mechanics.
 */
public interface PlanetMechanics {
    /**
     * Returns the initial buildings on a starter planet.
     *
     * @return Initial buildings on a starter planet.
     */
    Map<BuildingType, Integer> getStarterPlanetBuildings();

    /**
     * Returns the initial resources on a starter planet.
     *
     * @return Initial resources on a starter planet.
     */
    Resources getStarterPlanetResources();

    /**
     * Returns the initial resources on a colonized planet.
     *
     * @return Initial resources on a colonized planet.
     */
    Resources getColonizedPlanetResources();

    /**
     * Returns the initial buildings on a colonized planet.
     *
     * @return Initial buildings on a colonized planet.
     */
    Map<BuildingType, Integer> getColonizedPlanetBuildings();
}
