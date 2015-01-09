package restwars.service.technology.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.building.Buildings;
import restwars.service.event.Event;
import restwars.service.event.EventDAO;
import restwars.service.event.EventType;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.Resources;
import restwars.service.technology.*;
import restwars.util.MathExt;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TechnologyServiceImpl implements TechnologyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnologyServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final TechnologyDAO technologyDAO;
    private final PlanetDAO planetDAO;
    private final RoundService roundService;
    private final ResearchDAO researchDAO;
    private final BuildingDAO buildingDAO;
    private final EventDAO eventDAO;

    @Inject
    public TechnologyServiceImpl(UUIDFactory uuidFactory, TechnologyDAO technologyDAO, PlanetDAO planetDAO, RoundService roundService, ResearchDAO researchDAO, BuildingDAO buildingDAO, EventDAO eventDAO) {
        this.researchDAO = Preconditions.checkNotNull(researchDAO, "researchDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.eventDAO = Preconditions.checkNotNull(eventDAO, "eventDAO");
    }

    @Override
    public Technologies findAllForPlayer(Player player) {
        Preconditions.checkNotNull(player, "player");

        return technologyDAO.findAllWithPlayerId(player.getId());
    }

    @Override
    public Optional<Technology> findForPlayer(Player player, TechnologyType type) {
        Preconditions.checkNotNull(player, "player");
        Preconditions.checkNotNull(type, "type");

        return technologyDAO.findWithPlayerId(player.getId(), type);
    }

    @Override
    public void finishResearches() {
        long round = roundService.getCurrentRound();

        List<Research> finishedResearches = researchDAO.findWithDone(round);

        for (Research research : finishedResearches) {
            if (research.getLevel() == 1) {
                // Create new technology
                Technology technology = new Technology(uuidFactory.create(), research.getType(), research.getLevel(), research.getPlayerId());
                technologyDAO.insert(technology);
            } else {
                // Update existing technology
                Optional<Technology> existingTechnology = technologyDAO.findWithPlayerId(research.getPlayerId(), research.getType());
                Technology updatedTechnology = existingTechnology.get().withLevel(research.getLevel());
                technologyDAO.update(updatedTechnology);
            }

            // Create event
            eventDAO.insert(new Event(uuidFactory.create(), research.getPlayerId(), research.getPlanetId(), EventType.RESEARCH_COMPLETED, round));

            researchDAO.delete(research);
        }
    }

    @Override
    public List<Research> findResearchesOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return researchDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public Research researchTechnology(Player player, Planet planet, TechnologyType technology) throws ResearchException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(technology, "technology");
        Preconditions.checkNotNull(player, "player");

        Buildings buildings = buildingDAO.findWithPlanetId(planet.getId());

        // Ensure that the planet has a research center
        if (!buildings.has(BuildingType.RESEARCH_CENTER)) {
            throw new ResearchException(ResearchException.Reason.NO_RESEARCH_CENTER);
        }

        // Ensure that no other research is running on that planet
        if (!researchDAO.findWithPlanetId(planet.getId()).isEmpty()) {
            throw new ResearchException(ResearchException.Reason.NOT_ENOUGH_RESEARCH_QUEUES);
        }

        // Ensure that the research is not running on other planets
        if (!researchDAO.findWithPlayerAndType(player.getId(), technology).isEmpty()) {
            throw new ResearchException(ResearchException.Reason.ALREADY_RUNNING);
        }

        Optional<Technology> existingTechnology = technologyDAO.findWithPlayerId(player.getId(), technology);
        int level = existingTechnology.map(Technology::getLevel).orElse(1);

        Resources researchCost = calculateResearchCost(technology, level);
        if (!planet.getResources().isEnough(researchCost)) {
            throw new ResearchException(ResearchException.Reason.INSUFFICIENT_RESOURCES);
        }

        Planet updatedPlanet = planet.withResources(planet.getResources().minus(researchCost));
        planetDAO.update(updatedPlanet);

        long researchTime = calculateResearchTime(technology, level, buildings);
        long currentRound = roundService.getCurrentRound();
        Research research = new Research(uuidFactory.create(), technology, level, currentRound, currentRound + researchTime, updatedPlanet.getId(), player.getId());

        LOGGER.debug("Creating research {} on planet {}", research, updatedPlanet);
        researchDAO.insert(research);

        return research;
    }

    @Override
    public long calculateResearchTime(TechnologyType technology, int level, Buildings buildings) {
        Preconditions.checkNotNull(technology, "technology");
        Preconditions.checkArgument(level > 0, "level must be > 0");
        Preconditions.checkNotNull(buildings, "buildings");

        int researchTime;
        switch (technology) {
            case BUILDING_BUILD_COST_REDUCTION:
                researchTime = level;
                break;
            case BUILDING_BUILD_TIME_REDUCTION:
                researchTime = level;
                break;
            case CRYSTAL_MINE_EFFICIENCY:
                researchTime = level;
                break;
            case GAS_REFINERY_EFFICIENCY:
                researchTime = level;
                break;
            case SOLAR_PANELS_EFFICIENCY:
                researchTime = level;
                break;
            default:
                throw new AssertionError("Unknown technology: " + technology);
        }

        int researchCenterLevel = buildings.getLevel(BuildingType.RESEARCH_CENTER);
        double timeMultiplier = Math.max(1 - researchCenterLevel * 0.01, 0);

        return MathExt.floorLong(researchTime * timeMultiplier);
    }

    @Override
    public long calculateResearchTimeWithoutBonuses(TechnologyType technology, int level) {
        return calculateResearchTime(technology, level, Buildings.NONE);
    }

    @Override
    public Resources calculateResearchCost(TechnologyType technology, int level) {
        Preconditions.checkNotNull(technology, "technology");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (technology) {
            case BUILDING_BUILD_COST_REDUCTION:
                return new Resources(level, level, level);
            case BUILDING_BUILD_TIME_REDUCTION:
                return new Resources(level, level, level);
            case CRYSTAL_MINE_EFFICIENCY:
                return new Resources(level, level, level);
            case GAS_REFINERY_EFFICIENCY:
                return new Resources(level, level, level);
            case SOLAR_PANELS_EFFICIENCY:
                return new Resources(level, level, level);
            default:
                throw new AssertionError("Unknown technology: " + technology);
        }
    }
}
