package restwars.service.resource.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.Building;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.resource.ResourceService;
import restwars.service.resource.Resources;

import java.util.List;

public class ResourceServiceImpl implements ResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private final BuildingService buildingService;
    private final PlanetService planetService;

    public ResourceServiceImpl(BuildingService buildingService, PlanetService planetService) {
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
    }

    @Override
    public Resources calculateGatheredResources(BuildingType type, int level) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(level > 0, "level must be > 0");

        switch (type) {
            case CRYSTAL_MINE:
                return new Resources(level, 0, 0);
            case GAS_REFINERY:
                return new Resources(0, level, 0);
            case SOLAR_PANELS:
                return new Resources(0, 0, level * 10);
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

        LOGGER.debug("Planet has {} crystals, {} gas, {} energy", planet.getCrystals(), planet.getGas(), planet.getEnergy());

        long crystals = planet.getCrystals();
        long gas = planet.getGas();
        long energy = planet.getEnergy();

        List<Building> buildings = buildingService.findBuildingsOnPlanet(planet);
        for (Building building : buildings) {
            Resources gatheredResources = calculateGatheredResources(building.getType(), building.getLevel());

            crystals += gatheredResources.getCrystals();
            gas += gatheredResources.getGas();
            energy += gatheredResources.getEnergy();
        }

        Planet updatedPlanet = planet.withResources(crystals, gas, energy);
        planetService.update(updatedPlanet);

        LOGGER.debug("Planet has now {} crystals, {} gas, {} energy", updatedPlanet.getCrystals(), updatedPlanet.getGas(), updatedPlanet.getEnergy());
    }
}
