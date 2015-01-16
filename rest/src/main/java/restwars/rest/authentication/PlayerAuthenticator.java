package restwars.rest.authentication;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;
import restwars.service.security.PasswordException;
import restwars.service.security.PasswordService;

import javax.inject.Inject;
import java.util.Optional;

public class PlayerAuthenticator implements Authenticator<BasicCredentials, Player> {
    private final PlayerService playerService;
    private final PasswordService passwordService;

    @Inject
    public PlayerAuthenticator(PlayerService playerService, PasswordService passwordService) {
        this.passwordService = Preconditions.checkNotNull(passwordService, "passwordService");
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
    }

    @Override
    public com.google.common.base.Optional<Player> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Preconditions.checkNotNull(basicCredentials, "basicCredentials");

        Optional<Player> player = playerService.findWithUsername(basicCredentials.getUsername());
        if (player.isPresent()) {
            try {
                if (passwordService.verify(basicCredentials.getPassword(), player.get().getPassword())) {
                    return com.google.common.base.Optional.of(player.get());
                }
            } catch (PasswordException e) {
                throw new AuthenticationException("Exception while authenticating", e);
            }
        }

        return com.google.common.base.Optional.absent();
    }
}
