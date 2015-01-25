package restwars.service.player.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.CreateStartPlanetException;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.CreatePlayerException;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.player.PlayerService;
import restwars.service.security.PasswordService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class PlayerServiceImpl implements PlayerService {
    private static final Pattern ALLOWED_USERNAMES = Pattern.compile("[a-zA-Z0-9_]+");

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final PlayerDAO playerDAO;
    private final PlanetService planetService;
    private final PasswordService passwordService;

    @Inject
    public PlayerServiceImpl(UUIDFactory uuidFactory, PlayerDAO playerDAO, PlanetService planetService, PasswordService passwordService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.playerDAO = Preconditions.checkNotNull(playerDAO, "playerDAO");
        this.passwordService = Preconditions.checkNotNull(passwordService, "passwordService");
    }

    @Override
    public Player createPlayer(String username, String password) throws CreatePlayerException {
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(password, "password");

        // Check if username contains invalid chars
        if (!ALLOWED_USERNAMES.matcher(username).matches()) {
            throw new CreatePlayerException(CreatePlayerException.Reason.INVALID_USERNAME);
        }

        // Check if player with this username already exists
        if (playerDAO.findWithUsername(username).isPresent()) {
            throw new CreatePlayerException(CreatePlayerException.Reason.DUPLICATE_USERNAME);
        }

        UUID id = uuidFactory.create();
        String hash = passwordService.hash(password);

        Player player = new Player(id, username, hash);
        playerDAO.insert(player);
        LOGGER.debug("Created player {}", player);

        Planet planet;
        try {
            planet = planetService.createStartPlanet(player);
        } catch (CreateStartPlanetException e) {
            switch (e.getReason()) {
                case UNIVERSE_FULL:
                    throw new CreatePlayerException(CreatePlayerException.Reason.UNIVERSE_FULL, e);
                default:
                    throw new AssertionError("Unknown reason: " + e.getReason());
            }
        }
        LOGGER.debug("Created player starting planet at {}", planet);

        return player;
    }

    @Override
    public Optional<Player> findWithUsername(String username) {
        Preconditions.checkNotNull(username, "username");

        return playerDAO.findWithUsername(username);
    }
}
