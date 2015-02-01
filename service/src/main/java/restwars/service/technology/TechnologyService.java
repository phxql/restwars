package restwars.service.technology;

import restwars.model.building.Buildings;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.resource.Resources;
import restwars.model.technology.Research;
import restwars.model.technology.Technologies;
import restwars.model.technology.Technology;
import restwars.model.technology.TechnologyType;

import java.util.List;
import java.util.Optional;

/**
 * Service to manage technologies.
 */
public interface TechnologyService {
    /**
     * Researches the given technology on the given planet.
     *
     * @param player     Player.
     * @param planet     Planet.
     * @param technology Technology to research.
     * @return Research.
     */
    Research researchTechnology(Player player, Planet planet, TechnologyType technology) throws ResearchException;

    /**
     * Finds all running researches on the given planet.
     *
     * @param planet Planet.
     * @return Researches.
     */
    List<Research> findResearchesOnPlanet(Planet planet);

    /**
     * Calculates the research time for the given type and level of technology.
     *
     * @param technology  Type of technology.
     * @param level Level to research.
     * @param buildings Buildings on the planet where the research is done.
     * @return Research time in rounds.
     */
    long calculateResearchTime(TechnologyType technology, int level, Buildings buildings);

    /**
     * Calculates the research time for the given type and level of technology without applying bonuses.
     *
     * @param technology Type of technology.
     * @param level      Level to research.
     * @return Research time in rounds.
     */
    long calculateResearchTimeWithoutBonuses(TechnologyType technology, int level);

    /**
     * Calculates the research cost for the given type and level of technology.
     *
     * @param type  Type of technology.
     * @param level Level to research.
     * @return Research cost.
     */
    Resources calculateResearchCost(TechnologyType type, int level);

    /**
     * Finds all technologies for a given player.
     *
     * @param player Player.
     * @return All technologies for the given player.
     */
    Technologies findAllForPlayer(Player player);

    /**
     * Finds a technology with a given type for a given player.
     *
     * @param player Player.
     * @param type   Type of technology.
     * @return Technology for the given player.
     */
    Optional<Technology> findForPlayer(Player player, TechnologyType type);

    /**
     * Finishes all researches which are done in the current round.
     */
    void finishResearches();
}
