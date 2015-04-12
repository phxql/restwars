package restwars.service.mechanics;

import restwars.model.resource.Resources;

/**
 * Mechanics for resources.
 */
public interface ResourcesMechanics {
    /**
     * Calculates the points for the given resources.
     *
     * @param resources Resources.
     * @return Points.
     */
    long calculatePointsForResources(Resources resources);
}
