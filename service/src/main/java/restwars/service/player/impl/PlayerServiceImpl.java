package restwars.service.player.impl;

import com.google.common.base.Preconditions;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.player.PlayerService;

import java.util.UUID;

public class PlayerServiceImpl implements PlayerService {
    private final UUIDFactory uuidFactory;
    private final PlayerDAO playerDAO;

    public PlayerServiceImpl(UUIDFactory uuidFactory, PlayerDAO playerDAO) {
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

        return player;
    }
}
