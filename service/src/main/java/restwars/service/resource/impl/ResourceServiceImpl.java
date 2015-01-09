package restwars.service.resource.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.Building;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;
import restwars.service.building.Buildings;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.resource.ResourceService;
import restwars.service.resource.Resources;
import restwars.service.technology.Technologies;
import restwars.service.technology.TechnologyDAO;
import restwars.service.technology.TechnologyType;

import javax.inject.Inject;
import java.util.List;

public class ResourceServiceImpl implements ResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private final BuildingService buildingService;
    private final PlanetService planetService;
    private final TechnologyDAO technologyDAO;

    @Inject
    public ResourceServiceImpl(BuildingService buildingService, PlanetService planetService, TechnologyDAO technologyDAO) {
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.technologyDAO = Preconditions.checkNotNull(technologyDAO, "technologyDAO");
    }

    @Override
    public Resources calculateGatheredResources(BuildingType type, int level, Technologies technologies) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case CRYSTAL_MINE: {
                double technologyBonus = technologies.getLevel(TechnologyType.CRYSTAL_MINE_EFFICIENCY) * 0.10;
                return new Resources((long) Math.ceil(level * (1 + technologyBonus)), 0, 0);
            }
            case GAS_REFINERY: {
                double technologyBonus = technologies.getLevel(TechnologyType.GAS_REFINERY_EFFICIENCY) * 0.10;
                return new Resources(0, (long) Math.ceil(level * (1 + technologyBonus)), 0);
            }
            case SOLAR_PANELS: {
                double technologyBonus = technologies.getLevel(TechnologyType.SOLAR_PANELS_EFFICIENCY) * 0.10;
                return new Resources(0, 0, (long) Math.ceil(level * 10L * (1 + technologyBonus)));
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
