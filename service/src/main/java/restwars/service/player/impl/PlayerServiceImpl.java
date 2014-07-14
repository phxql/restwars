package restwars.service.player.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.location.LocationFactory;
import restwars.service.planet.Location;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.player.PlayerService;

import java.util.Optional;
import java.util.UUID;

public class PlayerServiceImpl implements PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final UniverseConfiguration universeConfiguration;
    private final UUIDFactory uuidFactory;
    private final PlayerDAO playerDAO;
    private final PlanetService planetService;
    private final LocationFactory locationFactory;

    public PlayerServiceImpl(UUIDFactory uuidFactory, PlayerDAO playerDAO, PlanetService planetService, LocationFactory locationFactory, UniverseConfiguration universeConfiguration) {
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.locationFactory = Preconditions.checkNotNull(locationFactory, "locationFactory");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.playerDAO = Preconditions.checkNotNull(playerDAO, "playerDAO");
    }

    @Override
    public Player createPlayer(String username, String password) {
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(password, "password");

        UUID id = uuidFactory.create();

        // TODO: Validate username and password

        Player player = new Player(id, username, password);
        playerDAO.insert(player);

        LOGGER.debug("Created player {}", player);

        Location location = locationFactory.random(universeConfiguration.getGalaxyCount(), universeConfiguration.getSolarSystemsPerGalaxy(), universeConfiguration.getPlanetsPerSolarSystem());
        planetService.createPlanet(location, player);

        LOGGER.debug("Created player starting planet at {}", location);

        return player;
    }

    @Override
    public Optional<Player> findWithUsername(String username) {
        Preconditions.checkNotNull(username, "username");

        return playerDAO.findWithUsername(username);
    }
}
