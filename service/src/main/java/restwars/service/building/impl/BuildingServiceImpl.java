package restwars.service.building.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.*;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;

import java.util.List;
import java.util.UUID;

public class BuildingServiceImpl implements BuildingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final BuildingDAO buildingDAO;
    private final RoundService roundService;
    private final ConstructionSiteDAO constructionSiteDAO;

    public BuildingServiceImpl(UUIDFactory uuidFactory, BuildingDAO buildingDAO, RoundService roundService, ConstructionSiteDAO constructionSiteDAO) {
        this.constructionSiteDAO = Preconditions.checkNotNull(constructionSiteDAO, "constructionSiteDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
    }

    @Override
    public List<Building> findBuildingsOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return buildingDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public List<ConstructionSite> findConstructionSitesOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return constructionSiteDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public void addBuilding(Planet planet, BuildingType type, int level) {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0);

        Building building = new Building(uuidFactory.create(), type, level, planet.getId());

        LOGGER.debug("Adding building {} to planet {}", building, planet);
        buildingDAO.insert(building);
    }

    @Override
    public ConstructionSite constructBuilding(Planet planet, BuildingType type) {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        // TODO: Check and decrease resources

        UUID id = uuidFactory.create();
        long buildTime = calculateBuildTime(type, 1);
        long currentRound = roundService.getCurrentRound();
        ConstructionSite constructionSite = new ConstructionSite(id, type, 1, planet.getId(), currentRound, currentRound + buildTime);

        LOGGER.debug("Creating construction site {} on planet {}", constructionSite, planet);

        constructionSiteDAO.insert(constructionSite);

        return constructionSite;
    }

    @Override
    public long calculateBuildTime(BuildingType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case COMMAND_CENTER:
                return level;
            case CRYSTAL_MINE:
                return level;
            case GAS_REFINERY:
                return level;
            case RESEARCH_CENTER:
                return level;
            case SHIPYARD:
                return level;
            case SOLAR_PANELS:
                return level;
            default:
                throw new AssertionError("Unknown building type " + type);
        }
    }
}
