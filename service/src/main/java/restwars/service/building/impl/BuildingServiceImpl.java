package restwars.service.building.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.*;
import restwars.service.event.Event;
import restwars.service.event.EventDAO;
import restwars.service.event.EventType;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.resource.Resources;
import restwars.service.technology.Technologies;
import restwars.service.technology.TechnologyDAO;
import restwars.service.technology.TechnologyType;
import restwars.util.MathExt;

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
    private final EventDAO eventDAO;
    private final TechnologyDAO technologyDAO;

    @Inject
    public BuildingServiceImpl(UUIDFactory uuidFactory, BuildingDAO buildingDAO, RoundService roundService, ConstructionSiteDAO constructionSiteDAO, PlanetDAO planetDAO, EventDAO eventDAO, TechnologyDAO technologyDAO) {
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.constructionSiteDAO = Preconditions.checkNotNull(constructionSiteDAO, "constructionSiteDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.eventDAO = Preconditions.checkNotNull(eventDAO, "eventDAO");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
    }

    @Override
    public Buildings findBuildingsOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return buildingDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public List<ConstructionSite> findConstructionSitesOnPlanet(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        return constructionSiteDAO.findWithPlanetId(planet.getId());
    }

    @Override
    public void manifestBuilding(Planet planet, BuildingType type, int level) {
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

        Technologies technologies = technologyDAO.findAllWithPlayerId(planet.getOwnerId());
        Buildings buildings = buildingDAO.findWithPlanetId(planet.getId());

        Resources buildCost = calculateBuildCost(type, level, technologies);
        if (!planet.getResources().isEnough(buildCost)) {
            throw new BuildingException(BuildingException.Reason.INSUFFICIENT_RESOURCES);
        }

        if (!findConstructionSitesOnPlanet(planet).isEmpty()) {
            throw new BuildingException(BuildingException.Reason.NOT_ENOUGH_BUILD_QUEUES);
        }

        Planet updatedPlanet = planet.withResources(planet.getResources().minus(buildCost));
        planetDAO.update(updatedPlanet);

        UUID id = uuidFactory.create();
        long buildTime = calculateBuildTime(type, level, technologies, buildings);
        long currentRound = roundService.getCurrentRound();
        ConstructionSite constructionSite = new ConstructionSite(id, type, level, updatedPlanet.getId(), updatedPlanet.getOwnerId(), currentRound, currentRound + buildTime);

        LOGGER.debug("Creating construction site {} on planet {}", constructionSite, updatedPlanet);

        constructionSiteDAO.insert(constructionSite);

        return constructionSite;
    }

    @Override
    public ConstructionSite upgradeBuilding(Planet planet, BuildingType type) throws BuildingException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        Buildings buildings = findBuildingsOnPlanet(planet);
        Optional<Building> existingBuilding = buildings.stream().filter(b -> b.getType().equals(type)).findFirst();

        Building building = existingBuilding.orElseThrow(() -> new BuildingException(BuildingException.Reason.EXISTING_BUILDING_NOT_FOUND));

        return createConstructionSite(planet, type, building.getLevel() + 1);
    }

    @Override
    public ConstructionSite constructOrUpgradeBuilding(Planet planet, BuildingType type) throws BuildingException {
        Preconditions.checkNotNull(planet, "planet");
        Preconditions.checkNotNull(type, "type");

        Buildings buildings = findBuildingsOnPlanet(planet);
        Optional<Building> existingBuilding = buildings.stream().filter(b -> b.getType().equals(type)).findFirst();

        if (existingBuilding.isPresent()) {
            return upgradeBuilding(planet, type);
        } else {
            return constructBuilding(planet, type);
        }
    }

    @Override
    public Resources calculateBuildCostWithoutBonuses(BuildingType type, int level) {
        return calculateBuildCost(type, level, Technologies.NONE);
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

            // Create event
            eventDAO.insert(new Event(uuidFactory.create(), constructionSite.getPlayerId(), constructionSite.getPlanetId(), EventType.BUILDING_COMPLETED, round));

            constructionSiteDAO.delete(constructionSite);
        }
    }

    @Override
    public long calculateBuildTime(BuildingType type, int level, Technologies technologies, Buildings buildings) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");
        Preconditions.checkNotNull(technologies, "technologies");
        Preconditions.checkNotNull(buildings, "buildings");

        int buildTime;
        switch (type) {
            case COMMAND_CENTER:
                buildTime = level;
                break;
            case CRYSTAL_MINE:
                buildTime = level;
                break;
            case GAS_REFINERY:
                buildTime = level;
                break;
            case RESEARCH_CENTER:
                buildTime = level;
                break;
            case SHIPYARD:
                buildTime = level;
                break;
            case SOLAR_PANELS:
                buildTime = level;
                break;
            case TELESCOPE:
                buildTime = level;
                break;
            default:
                throw new AssertionError("Unknown building type " + type);
        }

        int commandCenterLevel = buildings.getLevel(BuildingType.COMMAND_CENTER);
        int technologyLevel = technologies.getLevel(TechnologyType.BUILDING_BUILD_TIME_REDUCTION);

        int effectiveLevel = commandCenterLevel + technologyLevel;
        double timeMultiplier = Math.max(1 - effectiveLevel * 0.01, 0);

        return MathExt.floorLong(buildTime * timeMultiplier);
    }

    @Override
    public long calculateBuildTimeWithoutBonuses(BuildingType type, int level) {
        return calculateBuildTime(type, level, Technologies.NONE, Buildings.NONE);
    }

    @Override
    public Resources calculateBuildCost(BuildingType type, int level, Technologies technologies) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");
        Preconditions.checkNotNull(technologies, "technologies");

        Resources cost;
        switch (type) {
            case COMMAND_CENTER:
                cost = new Resources(level * 10L, level * 10L, level * 100L);
                break;
            case CRYSTAL_MINE:
                cost = new Resources(level, level, level);
                break;
            case GAS_REFINERY:
                cost = new Resources(level, level, level);
                break;
            case SOLAR_PANELS:
                cost = new Resources(level, level, level);
                break;
            case RESEARCH_CENTER:
                cost = new Resources(level, level, level);
                break;
            case SHIPYARD:
                cost = new Resources(level, level, level);
                break;
            case TELESCOPE:
                cost = new Resources(level, level, level);
                break;
            default:
                throw new AssertionError("Unknown building type: " + type);
        }

        int technologyLevel = technologies.getLevel(TechnologyType.BUILDING_BUILD_COST_REDUCTION);
        double costMultiplier = Math.max(1 - technologyLevel * 0.01, 0);

        return new Resources(MathExt.floorLong(cost.getCrystals() * costMultiplier), MathExt.floorLong(cost.getGas() * costMultiplier), MathExt.floorLong(cost.getEnergy() * costMultiplier));
    }
}
