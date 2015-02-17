package restwars.service.resource.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.building.Buildings;
import restwars.model.planet.Planet;
import restwars.model.resource.Resources;
import restwars.model.technology.Technologies;
import restwars.service.building.BuildingService;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.planet.PlanetService;
import restwars.service.resource.ResourceService;
import restwars.service.technology.TechnologyDAO;

import javax.inject.Inject;
import java.util.List;

public class ResourceServiceImpl implements ResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private final BuildingService buildingService;
    private final PlanetService planetService;
    private final TechnologyDAO technologyDAO;
    private final BuildingMechanics buildingMechanics;

    @Inject
    public ResourceServiceImpl(BuildingService buildingService, PlanetService planetService, TechnologyDAO technologyDAO, BuildingMechanics buildingMechanics) {
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
        this.buildingMechanics = Preconditions.checkNotNull(buildingMechanics, "buildingMechanics");
    }

    @Override
    public Resources calculateGatheredResourcesWithoutBonus(BuildingType type, int level) {
        return calculateGatheredResources(type, level, Technologies.NONE);
    }

    @Override
    public Resources calculateGatheredResources(BuildingType type, int level, Technologies technologies) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case CRYSTAL_MINE: {
                return new Resources(
                        buildingMechanics.calculateCrystalsGathered(level),
                        0, 0
                );
            }
            case GAS_REFINERY: {
                return new Resources(
                        0,
                        buildingMechanics.calculateGasGathered(level),
                        0
                );
            }
            case SOLAR_PANELS: {
                return new Resources(
                        0, 0,
                        buildingMechanics.calculateEnergyGathered(level)
                );
            }
            default:
                return Resources.NONE;
        }
    }

    @Override
    public void gatherResourcesOnAllPlanets() {
        List<Planet> planets = planetService.findAll();

        LOGGER.debug("Gathering resources on {} planets", planets.size());
        planets.forEach(this::gatherResources);
    }

    @Override
    public void gatherResources(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        LOGGER.debug("Planet has {}", planet.getResources());

        Resources totalGathered = Resources.NONE;

        Buildings buildings = buildingService.findBuildingsOnPlanet(planet);
        Technologies technologies = technologyDAO.findAllWithPlayerId(planet.getOwnerId());
        for (Building building : buildings) {
            Resources gatheredResources = calculateGatheredResources(building.getType(), building.getLevel(), technologies);
            totalGathered = totalGathered.plus(gatheredResources);
        }

        Planet updatedPlanet = planet.withResources(planet.getResources().plus(totalGathered));
        planetService.update(updatedPlanet);

        LOGGER.debug("Planet has now {}", updatedPlanet.getResources());
    }
}
