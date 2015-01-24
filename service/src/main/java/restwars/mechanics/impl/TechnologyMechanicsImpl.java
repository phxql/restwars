package restwars.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.mechanics.TechnologyMechanics;
import restwars.service.resource.Resources;
import restwars.service.technology.TechnologyType;
import restwars.service.techtree.Prerequisites;

public class TechnologyMechanicsImpl implements TechnologyMechanics {
    @Override
    public Resources calculateResearchCost(TechnologyType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case BUILDING_BUILD_COST_REDUCTION:
                return new Resources(
                        200 + (level - 1) * 100,
                        100 + (level - 1) * 50,
                        800 + (level - 1) * 400
                );
            default:
                throw new IllegalArgumentException("Invalid technology: " + type);
        }
    }

    @Override
    public int calculateResearchTime(TechnologyType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case BUILDING_BUILD_COST_REDUCTION:
                return 50 + (level - 1) * 25;
            default:
                throw new IllegalArgumentException("Invalid technology: " + type);
        }
    }

    @Override
    public Prerequisites getPrerequisites(TechnologyType type) {
        Preconditions.checkNotNull(type, "type");

        switch (type) {
            case BUILDING_BUILD_COST_REDUCTION:
                return Prerequisites.NONE;
            default:
                throw new IllegalArgumentException("Invalid technology: " + type);
        }
    }

    @Override
    public double calculateBuildCostReduction(int level) {
        Preconditions.checkArgument(level >= 0, "level must be >= 0");

        return level * 0.02;
    }
}
