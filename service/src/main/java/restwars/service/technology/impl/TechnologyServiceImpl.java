package restwars.service.technology.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.InsufficientBuildQueuesException;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingType;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.Player;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;
import restwars.service.technology.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TechnologyServiceImpl implements TechnologyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnologyServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final TechnologyDAO technologyDAO;
    private final PlanetDAO planetDAO;
    private final RoundService roundService;
    private final ResearchDAO researchDAO;
    private final BuildingDAO buildingDAO;

    @Inject
    public TechnologyServiceImpl(UUIDFactory uuidFactory, TechnologyDAO technologyDAO, PlanetDAO planetDAO, RoundService roundService, ResearchDAO researchDAO, BuildingDAO buildingDAO) {
        this.researchDAO = Preconditions.checkNotNull(researchDAO, "researchDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
    }

    @Override
    public List<Technology> findAllForPlayer(Player player) {
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

            researchDAO.delete(research);
        }
    }

    @Override
    public List<Research> findResearchesOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return researchDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public Research researchTechnology(Player player, Planet planet, TechnologyType technology) throws InsufficientResourcesException, InsufficientResearchCenterException, InsufficientBuildQueuesException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(technology, "technology");
        Preconditions.checkNotNull(player, "player");

        // Ensure that the planet has a research center
        boolean hasResearchCenter = buildingDAO.findWithPlanetId(planet.getId()).stream().anyMatch(b -> b.getType().equals(BuildingType.RESEARCH_CENTER));
        if (!hasResearchCenter) {
            throw new InsufficientResearchCenterException(1);
        }

        // Ensure that no other research is running on that planet
        if (!researchDAO.findWithPlanetId(planet.getId()).isEmpty()) {
            throw new InsufficientBuildQueuesException();
        }

        // TODO: Gameplay - Check if the research is already running for the player
        Optional<Technology> existingTechnology = technologyDAO.findWithPlayerId(player.getId(), technology);
        int level = existingTechnology.map(Technology::getLevel).orElse(1);

        Resources researchCost = calculateResearchCost(technology, level);
        if (!planet.getResources().isEnough(researchCost)) {
            throw new InsufficientResourcesException(researchCost, planet.getResources());
        }

        Planet updatedPlanet = planet.withResources(planet.getResources().minus(researchCost));
        planetDAO.update(updatedPlanet);

        UUID id = uuidFactory.create();
        long researchTime = calculateResearchTime(technology, level);
        long currentRound = roundService.getCurrentRound();
        Research research = new Research(id, technology, level, currentRound, currentRound + researchTime, updatedPlanet.getId(), player.getId());

        LOGGER.debug("Creating research {} on planet {}", research, updatedPlanet);
        researchDAO.insert(research);

        return research;
    }

    @Override
    public long calculateResearchTime(TechnologyType technology, int level) {
        Preconditions.checkNotNull(technology, "technology");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (technology) {
            case BUILD_COST_REDUCTION:
                return level;
            case BUILD_TIME_REDUCTION:
                return level;
            case CRYSTAL_MINE_EFFICIENCY:
                return level;
            case GAS_REFINERY_EFFICIENCY:
                return level;
            case SOLAR_PANELS_EFFICIENCY:
                return level;
            default:
                throw new AssertionError("Unknown technology: " + technology);
        }
    }

    @Override
    public Resources calculateResearchCost(TechnologyType technology, int level) {
        Preconditions.checkNotNull(technology, "technology");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (technology) {
            case BUILD_COST_REDUCTION:
                return new Resources(level, level, level);
            case BUILD_TIME_REDUCTION:
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
