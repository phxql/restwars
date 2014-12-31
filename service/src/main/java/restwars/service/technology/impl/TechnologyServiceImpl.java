package restwars.service.technology.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.BuildingService;
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
    private final BuildingService buildingService;
    private final TechnologyDAO technologyDAO;
    private final PlanetDAO planetDAO;
    private final RoundService roundService;
    private final ResearchDAO researchDAO;

    @Inject
    public TechnologyServiceImpl(UUIDFactory uuidFactory, BuildingService buildingService, TechnologyDAO technologyDAO, PlanetDAO planetDAO, RoundService roundService, ResearchDAO researchDAO) {
        this.researchDAO = Preconditions.checkNotNull(researchDAO, "researchDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
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
    public Research researchTechnology(Player player, Planet planet, TechnologyType technology) throws InsufficientResourcesException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(technology, "technology");
        Preconditions.checkNotNull(player, "player");

        // TODO: Gameplay - Check if a research is already running on the planet
        // TODO: Gameplay - Check if the research is already running for the player
        // TODO: Gameplay - Check if the planet has a research center
        Optional<Technology> existingTechnology = technologyDAO.findWithPlayerId(player.getId(), technology);
        int level = existingTechnology.map(Technology::getLevel).orElse(1);

        Resources researchCost = calculateResearchCost(technology, level);
        if (!planet.hasResources(researchCost)) {
            throw new InsufficientResourcesException(
                    researchCost.getCrystals(), researchCost.getGas(), researchCost.getEnergy(),
                    planet.getCrystals(), planet.getGas(), planet.getEnergy()
            );
        }

        Planet updatedPlanet = planet.withResources(planet.getCrystals() - researchCost.getCrystals(), planet.getGas() - researchCost.getGas(), planet.getEnergy() - researchCost.getEnergy());
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
