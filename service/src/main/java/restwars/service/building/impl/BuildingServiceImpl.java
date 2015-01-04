package restwars.service.building.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.*;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.resource.Resources;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BuildingServiceImpl implements BuildingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final BuildingDAO buildingDAO;
    private final RoundService roundService;
    private final PlanetDAO planetDAO;
    private final ConstructionSiteDAO constructionSiteDAO;

    @Inject
    public BuildingServiceImpl(UUIDFactory uuidFactory, BuildingDAO buildingDAO, RoundService roundService, ConstructionSiteDAO constructionSiteDAO, PlanetDAO planetDAO) {
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
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
    public ConstructionSite constructBuilding(Planet planet, BuildingType type) throws BuildingException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        return createConstructionSite(planet, type, 1);
    }

    private ConstructionSite createConstructionSite(Planet planet, BuildingType type, int level) throws BuildingException {
        assert planet != null;
        assert type != null;
        assert level > 0;

        Resources buildCost = calculateBuildCost(type, level);
        if (!planet.getResources().isEnough(buildCost)) {
            throw new BuildingException(BuildingException.Reason.INSUFFICIENT_RESOURCES);
        }

        if (!findConstructionSitesOnPlanet(planet).isEmpty()) {
            throw new BuildingException(BuildingException.Reason.NOT_ENOUGH_BUILD_QUEUES);
        }

        Planet updatedPlanet = planet.withResources(planet.getResources().minus(buildCost));
        planetDAO.update(updatedPlanet);

        UUID id = uuidFactory.create();
        long buildTime = calculateBuildTime(type, level);
        long currentRound = roundService.getCurrentRound();
        ConstructionSite constructionSite = new ConstructionSite(id, type, level, updatedPlanet.getId(), currentRound, currentRound + buildTime);

        LOGGER.debug("Creating construction site {} on planet {}", constructionSite, updatedPlanet);

        constructionSiteDAO.insert(constructionSite);

        return constructionSite;
    }

    @Override
    public ConstructionSite upgradeBuilding(Planet planet, BuildingType type) throws BuildingException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        List<Building> buildings = findBuildingsOnPlanet(planet);
        Optional<Building> existingBuilding = buildings.stream().filter(b -> b.getType().equals(type)).findFirst();

        Building building = existingBuilding.orElseThrow(() -> new BuildingException(BuildingException.Reason.EXISTING_BUILDING_NOT_FOUND));

        return createConstructionSite(planet, type, building.getLevel() + 1);
    }

    @Override
    public ConstructionSite constructOrUpgradeBuilding(Planet planet, BuildingType type) throws BuildingException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        List<Building> buildings = findBuildingsOnPlanet(planet);
        Optional<Building> existingBuilding = buildings.stream().filter(b -> b.getType().equals(type)).findFirst();

        if (existingBuilding.isPresent()) {
            return upgradeBuilding(planet, type);
        } else {
            return constructBuilding(planet, type);
        }
    }

    @Override
    public void finishConstructionSites() {
        long round = roundService.getCurrentRound();

        List<ConstructionSite> finishedConstructionSites = constructionSiteDAO.findWithDone(round);
        for (ConstructionSite constructionSite : finishedConstructionSites) {
            LOGGER.debug("Found finished construction site {}", constructionSite);

            if (constructionSite.getLevel() == 1) {
                UUID id = uuidFactory.create();
                Building building = new Building(id, constructionSite.getType(), constructionSite.getLevel(), constructionSite.getPlanetId());

                LOGGER.debug("Construction new building {}", building);
                buildingDAO.insert(building);
            } else {
                Building existingBuilding = buildingDAO.findWithPlanetIdAndType(constructionSite.getPlanetId(), constructionSite.getType()).orElseThrow(AssertionError::new);
                Building updatedBuilding = existingBuilding.withLevel(constructionSite.getLevel());

                LOGGER.debug("Updating building {}", updatedBuilding);
                buildingDAO.update(updatedBuilding);
            }

            constructionSiteDAO.delete(constructionSite);
        }
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
            case TELESCOPE:
                return level;
            default:
                throw new AssertionError("Unknown building type " + type);
        }
    }

    @Override
    public Resources calculateBuildCost(BuildingType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case COMMAND_CENTER:
                return new Resources(level * 10, level * 10, level * 100);
            case CRYSTAL_MINE:
                return new Resources(level, level, level);
            case GAS_REFINERY:
                return new Resources(level, level, level);
            case SOLAR_PANELS:
                return new Resources(level, level, level);
            case RESEARCH_CENTER:
                return new Resources(level, level, level);
            case SHIPYARD:
                return new Resources(level, level, level);
            case TELESCOPE:
                return new Resources(level, level, level);
            default:
                throw new AssertionError("Unknown building type: " + type);
        }
    }
}
