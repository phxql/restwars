package restwars.mechanics;

import restwars.service.resource.Resources;
import restwars.service.technology.TechnologyType;
import restwars.service.techtree.Prerequisites;

/**
 * Technology mechanics.
 */
public interface TechnologyMechanics {
    /**
     * Calculates the research cost for the given technology and level.
     *
     * @param type  Technology.
     * @param level Level. Must be > 0.
     * @return Research cost.
     */
    Resources calculateResearchCost(TechnologyType type, int level);

    /**
     * Calculates the research time for the given technology and level.
     *
     * @param type  Technology.
     * @param level Level. Must be > 0.
     * @return Research time in rounds.
     */
    int calculateResearchTime(TechnologyType type, int level);

    /**
     * Returns the prerequisites for the given technology.
     *
     * @param type Technology.
     * @return Prerequisites.
     */
    Prerequisites getPrerequisites(TechnologyType type);

    /**
     * Calculates the build cost reduction for the build cost reduction technology with the given level.
     *
     * @param level Level of the build cost reduction technology.
     * @return Build cost reduction.
     */
    double calculateBuildCostReduction(int level);
}
