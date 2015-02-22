package restwars.service.mechanics.impl;

import com.google.common.base.Preconditions;
import restwars.model.building.BuildingType;
import restwars.model.resource.Resources;
import restwars.model.techtree.Prerequisites;
import restwars.service.mechanics.BuildingMechanics;

public class SpeedUpBuildingMechanicsImpl implements BuildingMechanics {
    private final BuildingMechanics delegate;

    public SpeedUpBuildingMechanicsImpl(BuildingMechanics delegate) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
    }

    @Override
    public Resources calculateBuildCost(BuildingType type, int level) {
        return Resources.NONE;
    }

    @Override
    public Resources calculateCommandCenterResourcesGathered(int level) {
        return delegate.calculateCommandCenterResourcesGathered(level);
    }

    @Override
    public int calculateBuildTime(BuildingType type, int level) {
        return 1;
    }

    @Override
    public int calculateCrystalsGathered(int level) {
        return delegate.calculateCrystalsGathered(level);
    }

    @Override
    public int calculateGasGathered(int level) {
        return delegate.calculateGasGathered(level);
    }

    @Override
    public int calculateEnergyGathered(int level) {
        return delegate.calculateEnergyGathered(level);
    }

    @Override
    public double calculateBuildingBuildTimeSpeedup(int level) {
        return delegate.calculateBuildingBuildTimeSpeedup(level);
    }

    @Override
    public double calculateResearchTimeSpeedup(int level) {
        return delegate.calculateResearchTimeSpeedup(level);
    }

    @Override
    public double calculateShipBuildTimeSpeedup(int level) {
        return delegate.calculateShipBuildTimeSpeedup(level);
    }

    @Override
    public int calculateScanRange(int level) {
        return delegate.calculateScanRange(level);
    }

    @Override
    public int calculateFlightDetectionRange(int level) {
        return delegate.calculateFlightDetectionRange(level);
    }

    @Override
    public double calculateFleetSizeVariance(int level) {
        return delegate.calculateFleetSizeVariance(level);
    }

    @Override
    public Prerequisites getPrerequisites(BuildingType type) {
        return Prerequisites.NONE;
    }
}
