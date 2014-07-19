package restwars.service.resource.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.building.Building;
import restwars.service.building.BuildingService;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.resource.ResourceService;

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
    public void gatherResources(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        LOGGER.debug("Planet has {} crystals, {} gas, {} energy", planet.getCrystals(), planet.getGas(), planet.getEnergy());

        long crystals = planet.getCrystals();
        long gas = planet.getGas();
        long energy = planet.getEnergy();

        List<Building> buildings = buildingService.findBuildingsOnPlanet(planet);
        for (Building building : buildings) {
            crystals += calculateGatheredCrystals(building);
            gas += calculateGatheredGas(building);
            energy += calculateGatheredEnergy(building);
        }

        Planet updatedPlanet = planet.withResources(crystals, gas, energy);
        planetService.update(updatedPlanet);

        LOGGER.debug("Planet has now {} crystals, {} gas, {} energy", updatedPlanet.getCrystals(), updatedPlanet.getGas(), updatedPlanet.getEnergy());
    }

    private long calculateGatheredCrystals(Building building) {
        assert building != null;

        switch (building.getType()) {
            case CRYSTAL_MINE:
                return building.getLevel();
            default:
                return 0;
        }
    }

    private long calculateGatheredGas(Building building) {
        assert building != null;

        switch (building.getType()) {
            case GAS_REFINERY:
                return building.getLevel();
            default:
                return 0;
        }
    }

    private long calculateGatheredEnergy(Building building) {
        assert building != null;

        switch (building.getType()) {
            case SOLAR_PANELS:
                return building.getLevel() * 10;
            default:
                return 0;
        }
    }
}
