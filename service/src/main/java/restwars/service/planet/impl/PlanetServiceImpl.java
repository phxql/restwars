package restwars.service.planet.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.UniverseConfiguration;
import restwars.model.building.BuildingType;
import restwars.model.planet.Location;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.service.building.BuildingService;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.location.LocationFactory;
import restwars.service.mechanics.PlanetMechanics;
import restwars.service.planet.CreateStartPlanetException;
import restwars.service.planet.PlanetDAO;
import restwars.service.planet.PlanetService;

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
    private final RoundService roundService;

    private static final int MAX_STARTER_TRIES = 1000;

    @Inject
    public PlanetServiceImpl(UUIDFactory uuidFactory, PlanetDAO planetDAO, LocationFactory locationFactory, UniverseConfiguration universeConfiguration, BuildingService buildingService, PlanetMechanics planetMechanics, RoundService roundService) {
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
        this.locationFactory = Preconditions.checkNotNull(locationFactory, "locationFactory");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.planetDAO = Preconditions.checkNotNull(planetDAO, "planetDAO");
        this.planetMechanics = Preconditions.checkNotNull(planetMechanics, "planetMechanics");
    }

    @Override
    public Planet createStartPlanet(Player owner) throws CreateStartPlanetException {
        Preconditions.checkNotNull(owner, "owner");

        Location location = null;
        for (int i = 0; i < MAX_STARTER_TRIES; i++) {
            Location randomLocation = locationFactory.random(universeConfiguration.getGalaxyCount(), universeConfiguration.getSolarSystemsPerGalaxy(), universeConfiguration.getPlanetsPerSolarSystem());
            if (!planetDAO.findWithLocation(randomLocation).isPresent()) {
                location = randomLocation;
                break;
            }
        }
        if (location == null) {
            throw new CreateStartPlanetException(CreateStartPlanetException.Reason.UNIVERSE_FULL);
        }

        long currentRound = roundService.getCurrentRound();

        UUID id = uuidFactory.create();
        Planet planet = new Planet(id, location, owner.getId(), planetMechanics.getStarterPlanetResources(), currentRound);
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
