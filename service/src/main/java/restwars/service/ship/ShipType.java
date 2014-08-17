package restwars.service.ship;

import com.google.common.base.Preconditions;
import restwars.service.resource.Resources;

public enum ShipType {
    MOSQUITO(0, new Resources(1, 1, 1), 1, 1.0, 1),
    COLONY(1, new Resources(1, 1, 1), 1, 1.0, 1);

    private final int id;

    private final Resources buildCost;

    private final long buildTime;

    private final double flightCostModifier;

    private final long speed;

    ShipType(int id, Resources buildCost, long buildTime, double flightCostModifier, long speed) {
        this.id = id;
        Preconditions.checkArgument(buildTime > 0, "buildTime must be > 0");
        Preconditions.checkArgument(speed > 0, "speed must be > 0");

        this.buildCost = Preconditions.checkNotNull(buildCost, "buildCost");
        this.buildTime = buildTime;
        this.flightCostModifier = flightCostModifier;
        this.speed = speed;
    }

    public int getId() {
        return id;
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

    public long getSpeed() {
        return speed;
    }

    public static ShipType fromId(int id) {
        for (ShipType type : ShipType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
