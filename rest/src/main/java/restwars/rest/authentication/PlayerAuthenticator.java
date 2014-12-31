package restwars.rest.authentication;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;

import javax.inject.Inject;
import java.util.Optional;

public class PlayerAuthenticator implements Authenticator<BasicCredentials, Player> {
    private final PlayerService playerService;

    @Inject
    public PlayerAuthenticator(PlayerService playerService) {
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
    }

    @Override
    public com.google.common.base.Optional<Player> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Preconditions.checkNotNull(basicCredentials, "basicCredentials");

        Optional<Player> player = playerService.findWithUsername(basicCredentials.getUsername());
        if (player.isPresent()) {
            // TODO: Security - Fix timing attacks
            // TODO: Security - Bcrypt password
            if (player.get().getPassword().equals(basicCredentials.getPassword())) {
                return com.google.common.base.Optional.of(player.get());
            }
        }

        return com.google.common.base.Optional.absent();
    }
}
