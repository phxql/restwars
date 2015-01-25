package restwars.service.building.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.building.Buildings;
import restwars.model.planet.Planet;
import restwars.model.resource.Resources;
import restwars.model.technology.Technologies;
import restwars.model.technology.TechnologyType;
import restwars.model.techtree.Prerequisites;
import restwars.service.building.*;
import restwars.service.event.EventService;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.mechanics.TechnologyMechanics;
import restwars.service.planet.PlanetDAO;
import restwars.service.technology.TechnologyDAO;
import restwars.util.MathExt;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BuildingServiceImpl implements BuildingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final BuildingDAO buildingDAO;
    private final RoundService roundService;
    private final PlanetDAO planetDAO;
    private final ConstructionSiteDAO constructionSiteDAO;
    private final EventService eventService;
    private final TechnologyDAO technologyDAO;
    private final BuildingMechanics buildingMechanics;
    private final TechnologyMechanics technologyMechanics;

    @Inject
    public BuildingServiceImpl(UUIDFactory uuidFactory, BuildingDAO buildingDAO, RoundService roundService, ConstructionSiteDAO constructionSiteDAO, PlanetDAO planetDAO, EventService eventService, TechnologyDAO technologyDAO, BuildingMechanics buildingMechanics, TechnologyMechanics technologyMechanics) {
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.constructionSiteDAO = Preconditions.checkNotNull(constructionSiteDAO, "constructionSiteDAO");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.buildingDAO = Preconditions.checkNotNull(buildingDAO, "buildingDAO");
        this.eventService = Preconditions.checkNotNull(eventService, "eventService");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
        this.buildingMechanics = Preconditions.checkNotNull(buildingMechanics, "buildingMechanics");
        this.technologyMechanics = Preconditions.checkNotNull(technologyMechanics, "technologyMechanics");
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

        boolean prerequisitesFulfilled = buildingMechanics.getPrerequisites(type).fulfilled(
                buildings.stream().map(b -> new Prerequisites.Building(b.getType(), b.getLevel())).collect(Collectors.toList()),
                technologies.stream().map(t -> new Prerequisites.Technology(t.getType(), t.getLevel())).collect(Collectors.toList())
        );
        if (!prerequisitesFulfilled) {
            throw new BuildingException(BuildingException.Reason.PREREQUISITES_NOT_FULFILLED);
        }

        if (!findConstructionSitesOnPlanet(planet).isEmpty()) {
            throw new BuildingException(BuildingException.Reason.NOT_ENOUGH_BUILD_QUEUES);
        }

        Resources buildCost = calculateBuildCost(type, level, technologies);
        if (!planet.getResources().isEnough(buildCost)) {
            throw new BuildingException(BuildingException.Reason.INSUFFICIENT_RESOURCES);
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
            eventService.createBuildingCompletedEvent(constructionSite.getPlayerId(), constructionSite.getPlanetId());

            constructionSiteDAO.delete(constructionSite);
        }
    }

    @Override
    public long calculateBuildTime(BuildingType type, int level, Technologies technologies, Buildings buildings) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");
        Preconditions.checkNotNull(technologies, "technologies");
        Preconditions.checkNotNull(buildings, "buildings");

        int buildTime = buildingMechanics.calculateBuildTime(type, level);

        int commandCenterLevel = buildings.getLevel(BuildingType.COMMAND_CENTER);
        double speedUp = buildingMechanics.calculateBuildingBuildTimeSpeedup(commandCenterLevel);

        return Math.max(MathExt.floorLong(buildTime * (1 - speedUp)), 1);
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

        Resources cost = buildingMechanics.calculateBuildCost(type, level);

        int technologyLevel = technologies.getLevel(TechnologyType.BUILDING_BUILD_COST_REDUCTION);
        double buildCostReduction = technologyMechanics.calculateBuildCostReduction(technologyLevel);
        // TODO: Gameplay - This reaches eventually 0, further updates to the technology are worthless and the buildings are for free, fix this!
        double costMultiplier = Math.max(1 - buildCostReduction, 0);

        return new Resources(MathExt.floorLong(cost.getCrystals() * costMultiplier), MathExt.floorLong(cost.getGas() * costMultiplier), MathExt.floorLong(cost.getEnergy() * costMultiplier));
    }
}
