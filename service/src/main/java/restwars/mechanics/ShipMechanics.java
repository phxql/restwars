package restwars.mechanics;

import restwars.service.resource.Resources;
import restwars.service.ship.ShipType;
import restwars.service.techtree.Prerequisites;

/**
 * Ship mechanics.
 */
public interface ShipMechanics {
    /**
     * Returns the build cost for the given ship.
     *
     * @param type Ship.
     * @return Build cost.
     */
    Resources getBuildCost(ShipType type);

    /**
     * Returns the build time for the given ship.
     *
     * @param type Ship.
     * @return Build time in rounds.
     */
    int getBuildTime(ShipType type);

    /**
     * Returns the flight cost modifier for the given ship.
     *
     * @param type Ship.
     * @return Flight cost modifier.
     */
    double getFlightCostModifier(ShipType type);

    /**
     * Returns the speed for the given ship.
     *
     * @param type Ship.
     * @return Speed.
     */
    double getFlightSpeed(ShipType type);

    /**
     * Returns the attack points for the given ship.
     *
     * @param type Ship.
     * @return Attack points.
     */
    int getAttackPoints(ShipType type);

    /**
     * Returns the defense points for the given ship.
     *
     * @param type Ship.
     * @return Defense points.
     */
    int getDefensePoints(ShipType type);

    /**
     * Returns the cargo space for the given ship.
     *
     * @param type Ship.
     * @return Cargo space.
     */
    int getCargoSpace(ShipType type);

    /**
     * Returns the prerequisites for the given ship.
     *
     * @param type Ship.
     * @return Prerequisites.
     */
    Prerequisites getPrerequisites(ShipType type);
}
