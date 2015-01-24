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

        // TODO: Gameplay - balance this!
        switch (type) {
            case BUILDING_BUILD_COST_REDUCTION:
                return new Resources(1, 1, 1);
            default:
                throw new IllegalArgumentException("Invalid technology: " + type);
        }
    }

    @Override
    public int calculateResearchTime(TechnologyType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        // TODO: Gameplay - balance this!
        switch (type) {
            case BUILDING_BUILD_COST_REDUCTION:
                return 1;
            default:
                throw new IllegalArgumentException("Invalid technology: " + type);
        }
    }

    @Override
    public Prerequisites getPrerequisites(TechnologyType type) {
        Preconditions.checkNotNull(type, "type");

        // TODO: Gameplay - balance this!
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

        if (level == 0) {
            return 0;
        }

        // TODO: Gameplay - balance this!
        return level * 0.01;
    }
}
