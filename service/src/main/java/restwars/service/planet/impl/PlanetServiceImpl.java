package restwars.service.planet.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.mechanics.PlanetMechanics;
import restwars.service.UniverseConfiguration;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.location.LocationFactory;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlanetServiceImpl implements PlanetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final PlanetDAO planetDAO;
    private final LocationFactory locationFactory;
    private final UniverseConfiguration universeConfiguration;
    private final BuildingService buildingService;
    private final PlanetMechanics planetMechanics;

    @Inject
    public PlanetServiceImpl(UUIDFactory uuidFactory, PlanetDAO planetDAO, LocationFactory locationFactory, UniverseConfiguration universeConfiguration, BuildingService buildingService, PlanetMechanics planetMechanics) {
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
        this.locationFactory = Preconditions.checkNotNull(locationFactory, "locationFactory");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.planetMechanics = Preconditions.checkNotNull(planetMechanics, "planetMechanics");
    }

    @Override
    public Planet createStartPlanet(Player owner) {
        Preconditions.checkNotNull(owner, "owner");

        UUID id = uuidFactory.create();

        // TODO: Check if the planet is already colonized and raise exception when universe is full.
        Location location = locationFactory.random(universeConfiguration.getGalaxyCount(), universeConfiguration.getSolarSystemsPerGalaxy(), universeConfiguration.getPlanetsPerSolarSystem());
        Planet planet = new Planet(id, location, owner.getId(), planetMechanics.getStarterPlanetResources());
        planetDAO.insert(planet);

        for (Map.Entry<BuildingType, Integer> building : planetMechanics.getStarterPlanetBuildings().entrySet()) {
            buildingService.manifestBuilding(planet, building.getKey(), building.getValue());
        }

        LOGGER.debug("Created starter planet {}", planet);

        return planet;
    }

    @Override
    public List<Planet> findWithOwner(Player owner) {
        Preconditions.checkNotNull(owner, "owner");

        return planetDAO.findWithOwnerId(owner.getId());
    }

    @Override
    public Optional<Planet> findWithLocation(Location location) {
        Preconditions.checkNotNull(location, "location");

        return planetDAO.findWithLocation(location);
    }

    @Override
    public List<Planet> findAll() {
        return planetDAO.findAll();
    }

    @Override
    public void update(Planet planet) {
        Preconditions.checkNotNull(planet, "planet");

        planetDAO.update(planet);
    }
}
