package restwars.service.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.model.building.BuildingType;
import restwars.model.resource.Resources;
import restwars.model.techtree.Prerequisites;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.mechanics.ResourcesMechanics;
import restwars.util.MathExt;

import javax.inject.Inject;

/**
 * Default implementation of {@link BuildingMechanics}.
 */
public class BuildingMechanicsImpl implements BuildingMechanics {
    private final ResourcesMechanics resourcesMechanics;

    @Inject
    public BuildingMechanicsImpl(ResourcesMechanics resourcesMechanics) {
        this.resourcesMechanics = resourcesMechanics;
    }

    @Override
    public Resources calculateCommandCenterResourcesGathered(int level) {
        return new Resources(2, 1, 8);
    }

    @Override
    public Resources calculateBuildCost(BuildingType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case COMMAND_CENTER:
                return new Resources(
                        200 + (level - 1) * 100,
                        100 + (level - 1) * 50,
                        800 + (level - 1) * 400
                );
            case CRYSTAL_MINE:
                return new Resources(
                        100 + (level - 1) * 50,
                        50 + (level - 1) * 25,
                        400 + (level - 1) * 200
                );
            case GAS_REFINERY:
                return new Resources(
                        100 + (level - 1) * 50,
                        50 + (level - 1) * 25,
                        400 + (level - 1) * 200
                );
            case SOLAR_PANELS:
                return new Resources(
                        100 + (level - 1) * 50,
                        50 + (level - 1) * 25,
                        400 + (level - 1) * 200
                );
            case RESEARCH_CENTER:
                return new Resources(
                        200 + (level - 1) * 100,
                        100 + (level - 1) * 50,
                        800 + (level - 1) * 400
                );
            case SHIPYARD:
                return new Resources(
                        300 + (level - 1) * 150,
                        150 + (level - 1) * 75,
                        1200 + (level - 1) * 600
                );
            case TELESCOPE:
                return new Resources(
                        100 + (level - 1) * 50,
                        50 + (level - 1) * 25,
                        400 + (level - 1) * 200
                );
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
                return 50 + (level - 1) * 25;
            case CRYSTAL_MINE:
                return 30 + (level - 1) * 10;
            case GAS_REFINERY:
                return 30 + (level - 1) * 10;
            case SOLAR_PANELS:
                return 30 + (level - 1) * 10;
            case RESEARCH_CENTER:
                return 50 + (level - 1) * 25;
            case SHIPYARD:
                return 100 + (level - 1) * 50;
            case TELESCOPE:
                return 50 + (level - 1) * 10;
            default:
                throw new IllegalArgumentException("Unknown building type " + type);
        }
    }

    @Override
    public int calculateCrystalsGathered(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return 10 + (level - 1) * 5;
    }

    @Override
    public int calculateGasGathered(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return MathExt.ceilInt(5 + (level - 1) * 2.5);
    }

    @Override
    public int calculateEnergyGathered(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return 40 + (level - 1) * 20;
    }

    @Override
    public double calculateBuildingBuildTimeSpeedup(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return Math.min(1, (level - 1) * 0.1);
    }

    @Override
    public double calculateResearchTimeSpeedup(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return Math.min(1, (level - 1) * 0.1);
    }

    @Override
    public double calculateShipBuildTimeSpeedup(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return Math.min(1, (level - 1) * 0.1);
    }

    @Override
    public int calculateScanRange(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return level - 1;
    }

    @Override
    public int calculateFlightDetectionRange(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 0;
        }

        return level + 1;
    }

    @Override
    public Prerequisites getPrerequisites(BuildingType type) {
        Preconditions.checkNotNull(type, "type");

        // TODO: Gameplay - balance this!
        switch (type) {
            case COMMAND_CENTER:
                return Prerequisites.NONE;
            case CRYSTAL_MINE:
                return Prerequisites.building(BuildingType.COMMAND_CENTER, 1);
            case GAS_REFINERY:
                return Prerequisites.building(BuildingType.COMMAND_CENTER, 1);
            case SOLAR_PANELS:
                return Prerequisites.building(BuildingType.COMMAND_CENTER, 1);
            case RESEARCH_CENTER:
                return Prerequisites.building(BuildingType.COMMAND_CENTER, 1);
            case SHIPYARD:
                return Prerequisites.buildings(
                        new Prerequisites.Building(BuildingType.COMMAND_CENTER, 1),
                        new Prerequisites.Building(BuildingType.RESEARCH_CENTER, 1),
                        new Prerequisites.Building(BuildingType.TELESCOPE, 1)
                );
            case TELESCOPE:
                return Prerequisites.building(BuildingType.COMMAND_CENTER, 1);
            default:
                throw new IllegalArgumentException("Unknown building type " + type);
        }
    }

    @Override
    public long calculatePointsForBuilding(BuildingType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        long cost = 0;
        for (int i = 1; i <= level; i++) {
            cost += resourcesMechanics.calculatePointsForResources(calculateBuildCost(type, level));
        }

        return cost;
    }

    @Override
    public double calculateFleetSizeVariance(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        if (level == 0) {
            return 1;
        }

        // TODO: Gameplay - This reaches 0.0 on level 21, more levels aren't worth anything.
        return Math.max(1.0 - (level - 1) * 0.05, 0.0);
    }
}
