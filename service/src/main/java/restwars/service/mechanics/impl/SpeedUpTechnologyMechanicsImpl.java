package restwars.service.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.model.resource.Resources;
import restwars.model.technology.TechnologyType;
import restwars.model.techtree.Prerequisites;
import restwars.service.mechanics.TechnologyMechanics;

public class SpeedUpTechnologyMechanicsImpl implements TechnologyMechanics {
    private final TechnologyMechanics delegate;

    public SpeedUpTechnologyMechanicsImpl(TechnologyMechanics delegate) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
    }

    @Override
    public Resources calculateResearchCost(TechnologyType type, int level) {
        return Resources.NONE;
    }

    @Override
    public int calculateResearchTime(TechnologyType type, int level) {
        return 1;
    }

    @Override
    public Prerequisites getPrerequisites(TechnologyType type) {
        return Prerequisites.NONE;
    }

    @Override
    public double calculateBuildCostReduction(int level) {
        return delegate.calculateBuildCostReduction(level);
    }

    @Override
    public double calculateCombustionFlightCostReduction(int level) {
        return delegate.calculateCombustionFlightCostReduction(level);
    }
}
