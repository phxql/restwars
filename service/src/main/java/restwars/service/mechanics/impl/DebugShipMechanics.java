package restwars.service.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.model.UniverseConfiguration;
import restwars.model.resource.Resources;
import restwars.model.ship.ShipType;
import restwars.model.techtree.Prerequisites;
import restwars.service.mechanics.ShipMechanics;

/**
 * Debug mechanics for ships. Overrides some mechanics when enabled in universe configuration.
 */
public class DebugShipMechanics implements ShipMechanics {
    private final ShipMechanics delegate;
    private final UniverseConfiguration universeConfiguration;

    public DebugShipMechanics(UniverseConfiguration universeConfiguration, ShipMechanics delegate) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
    }

    @Override
    public Resources getBuildCost(ShipType type) {
        if (universeConfiguration.isFreeShips()) {
            return Resources.NONE;
        } else {
            return delegate.getBuildCost(type);
        }
    }

    @Override
    public int getBuildTime(ShipType type) {
        if (universeConfiguration.isSpeedUpShipConstructions()) {
            return 1;
        } else {
            return delegate.getBuildTime(type);
        }
    }

    @Override
    public double getFlightCostModifier(ShipType type) {
        if (universeConfiguration.isFreeFlights()) {
            return 0;
        } else {
            return delegate.getFlightCostModifier(type);
        }
    }

    @Override
    public double getFlightSpeed(ShipType type) {
        if (universeConfiguration.isSpeedUpFlights()) {
            return Double.MAX_VALUE;
        } else {
            return delegate.getFlightSpeed(type);
        }
    }

    @Override
    public int getAttackPoints(ShipType type) {
        return delegate.getAttackPoints(type);
    }

    @Override
    public int getDefensePoints(ShipType type) {
        return delegate.getDefensePoints(type);
    }

    @Override
    public int getCargoSpace(ShipType type) {
        return delegate.getCargoSpace(type);
    }

    @Override
    public Prerequisites getPrerequisites(ShipType type) {
        if (universeConfiguration.isNoShipPrerequisites()) {
            return Prerequisites.NONE;
        } else {
            return delegate.getPrerequisites(type);
        }
    }
}
