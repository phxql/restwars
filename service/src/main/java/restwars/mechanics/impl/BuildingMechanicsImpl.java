package restwars.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.mechanics.BuildingMechanics;
import restwars.service.building.BuildingType;
import restwars.service.resource.Resources;

public class BuildingMechanicsImpl implements BuildingMechanics {
    @Override
    public Resources calculateBuildCost(BuildingType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case COMMAND_CENTER:
                return new Resources(1, 1, 1);
            case CRYSTAL_MINE:
                return new Resources(1, 1, 1);
            case GAS_REFINERY:
                return new Resources(1, 1, 1);
            case RESEARCH_CENTER:
                return new Resources(1, 1, 1);
            case SHIPYARD:
                return new Resources(1, 1, 1);
            case SOLAR_PANELS:
                return new Resources(1, 1, 1);
            case TELESCOPE:
                return new Resources(1, 1, 1);
            default:
                throw new IllegalArgumentException("Unknown building type " + type);
        }
    }

    @Override
    public int calculateBuildTime(BuildingType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case COMMAND_CENTER:
                return 1;
            case CRYSTAL_MINE:
                return 1;
            case GAS_REFINERY:
                return 1;
            case RESEARCH_CENTER:
                return 1;
            case SHIPYARD:
                return 1;
            case SOLAR_PANELS:
                return 1;
            case TELESCOPE:
                return 1;
            default:
                throw new IllegalArgumentException("Unknown building type " + type);
        }
    }

    @Override
    public int calculateCrystalsGathered(int level) {
        return level;
    }

    @Override
    public int calculateGasGathered(int level) {
        return level;
    }

    @Override
    public int calculateEnergyGathered(int level) {
        return level;
    }

    @Override
    public double calculateBuildingBuildTimeSpeedup(int level) {
        return 0;
    }

    @Override
    public double calculateResearchTimeSpeedup(int level) {
        return 0;
    }

    @Override
    public double calculateShipBuildTimeSpeedup(int level) {
        return 0;
    }

    @Override
    public int calculateFlightDetectionRange(int level) {
        return 0;
    }

    @Override
    public double calculateFleetSizeVariance(int level) {
        return 0;
    }
}
