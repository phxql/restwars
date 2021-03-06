package restwars.service.mechanics;

import restwars.model.resource.Resources;
import restwars.model.technology.TechnologyType;
import restwars.model.techtree.Prerequisites;

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

    /**
     * Calculates the flight cost reduction for the combustion engine technology with the given level.
     *
     * @param level Level of the combustion engine technology.
     * @return Flight cost reduction.
     */
    double calculateCombustionFlightCostReduction(int level);

    /**
     * Calculates the points for the given technology.
     *
     * @param type  Type of technology.
     * @param level Technology level.
     * @return Points.
     */
    long calculatePointsForTechnology(TechnologyType type, int level);

    /**
     * Calculates the points for the given research.
     *
     * @param type  Type of research.
     * @param level Technology level.
     * @return Points.
     */
    long calculatePointsForResearch(TechnologyType type, int level);
}
