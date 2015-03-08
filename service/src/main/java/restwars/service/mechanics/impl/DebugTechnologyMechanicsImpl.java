package restwars.service.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.model.UniverseConfiguration;
import restwars.model.resource.Resources;
import restwars.model.technology.TechnologyType;
import restwars.model.techtree.Prerequisites;
import restwars.service.mechanics.TechnologyMechanics;

/**
 * Debug mechanics for technologies. Overrides some mechanics when enabled in universe configuration.
 */
public class DebugTechnologyMechanicsImpl implements TechnologyMechanics {
    private final UniverseConfiguration universeConfiguration;
    private final TechnologyMechanics delegate;

    public DebugTechnologyMechanicsImpl(UniverseConfiguration universeConfiguration, TechnologyMechanics delegate) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
    }

    @Override
    public Resources calculateResearchCost(TechnologyType type, int level) {
        if (universeConfiguration.isFreeResearches()) {
            return Resources.NONE;
        } else {
            return delegate.calculateResearchCost(type, level);
        }
    }

    @Override
    public int calculateResearchTime(TechnologyType type, int level) {
        if (universeConfiguration.isSpeedUpResearches()) {
            return 1;
        } else {
            return delegate.calculateResearchTime(type, level);
        }
    }

    @Override
    public Prerequisites getPrerequisites(TechnologyType type) {
        if (universeConfiguration.isNoResearchPrerequisites()) {
            return Prerequisites.NONE;
        } else {
            return delegate.getPrerequisites(type);
        }
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
