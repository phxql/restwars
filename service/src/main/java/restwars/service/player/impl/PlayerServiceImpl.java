package restwars.service.player.impl;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.player.PlayerService;
import restwars.service.unitofwork.UnitOfWork;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

public class PlayerServiceImpl implements PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final UUIDFactory uuidFactory;
    private final PlayerDAO playerDAO;
    private final PlanetService planetService;

    @Inject
    public PlayerServiceImpl(UUIDFactory uuidFactory, PlayerDAO playerDAO, PlanetService planetService) {
        this.planetService = Preconditions.checkNotNull(planetService, "planetService");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.playerDAO = Preconditions.checkNotNull(playerDAO, "playerDAO");
    }

    @Override
    public Player createPlayer(UnitOfWork uow, String username, String password) {
        Preconditions.checkNotNull(uow, "uow");
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(password, "password");

        UUID id = uuidFactory.create();

        // TODO: Validate username and password

        Player player = new Player(id, username, password);
        playerDAO.insert(uow, player);
        LOGGER.debug("Created player {}", player);

        Planet planet = planetService.createStartPlanet(player);
        LOGGER.debug("Created player starting planet at {}", planet);

        return player;
    }

    @Override
    public Optional<Player> findWithUsername(UnitOfWork uow, String username) {
        Preconditions.checkNotNull(uow, "uow");
        Preconditions.checkNotNull(username, "username");

        return playerDAO.findWithUsername(uow, username);
    }
}
