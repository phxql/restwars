package restwars.rest.authentication;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;

public class PlayerAuthenticator implements Authenticator<BasicCredentials, Player> {
    private final PlayerService playerService;

    public PlayerAuthenticator(PlayerService playerService) {
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
    }

    @Override
    public Optional<Player> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Preconditions.checkNotNull(basicCredentials, "basicCredentials");

        Optional<Player> player = playerService.findWithUsername(basicCredentials.getUsername());
        if (player.isPresent()) {
            // TODO: Fix timing attacks
            // TODO: Bcrypt password
            if (player.get().getPassword().equals(basicCredentials.getPassword())) {
                return player;
            }
        }

        return Optional.absent();
    }
}
