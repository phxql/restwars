package restwars.service.mechanics.impl;

import restwars.model.resource.Resources;
import restwars.service.mechanics.ResourcesMechanics;

/**
 * Default implementation for {@link ResourcesMechanics}.
 */
public class ResourcesMechanicsImpl implements ResourcesMechanics {

    private static final int CRYSTAL_MULTIPLIER = 4;
    private static final int GAS_MULTIPLIER = 8;

    @Override
    public long calculatePointsForResources(Resources resources) {
        return resources.getCrystals() * CRYSTAL_MULTIPLIER + resources.getGas() * GAS_MULTIPLIER + resources.getEnergy();
    }
}
