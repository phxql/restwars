package restwars.service.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.model.resource.Resources;
import restwars.model.ship.ShipType;
import restwars.model.techtree.Prerequisites;
import restwars.service.mechanics.ShipMechanics;

public class SpeedUpShipMechanics implements ShipMechanics {
    private final ShipMechanics delegate;

    public SpeedUpShipMechanics(ShipMechanics delegate) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
    }

    @Override
    public Resources getBuildCost(ShipType type) {
        return Resources.NONE;
    }

    @Override
    public int getBuildTime(ShipType type) {
        return 1;
    }

    @Override
    public double getFlightCostModifier(ShipType type) {
        return 0;
    }

    @Override
    public double getFlightSpeed(ShipType type) {
        return Double.MAX_VALUE;
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
        return Prerequisites.NONE;
    }
}
