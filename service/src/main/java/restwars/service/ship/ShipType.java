package restwars.service.ship;

import com.google.common.base.Preconditions;
import restwars.service.resource.Resources;

public enum ShipType {
    MOSQUITO(new Resources(1, 1, 1), 1, 1.0);

    private final Resources buildCost;

    private final long buildTime;

    private final double flightCostModifier;

    ShipType(Resources buildCost, long buildTime, double flightCostModifier) {
        Preconditions.checkArgument(buildTime > 1, "buildTime must be > 1");

        this.buildCost = Preconditions.checkNotNull(buildCost, "buildCost");
        this.buildTime = buildTime;
        this.flightCostModifier = flightCostModifier;
    }

    public Resources getBuildCost() {
        return buildCost;
    }

    public long getBuildTime() {
        return buildTime;
    }

    public double getFlightCostModifier() {
        return flightCostModifier;
    }
}
