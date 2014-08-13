package restwars.rest.authentication;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.inject.Inject;
import java.util.Optional;

public class PlayerAuthenticator implements Authenticator<BasicCredentials, Player> {
    private final PlayerService playerService;
    // TODO: Maybe we can use JAXRS features to get the current unit of work?
    private final UnitOfWorkService unitOfWorkService;

    @Inject
    public PlayerAuthenticator(PlayerService playerService, UnitOfWorkService unitOfWorkService) {
        this.unitOfWorkService = Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");
        this.playerService = Preconditions.checkNotNull(playerService, "playerService");
    }

    @Override
    public com.google.common.base.Optional<Player> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Preconditions.checkNotNull(basicCredentials, "basicCredentials");

        unitOfWorkService.start();
        Optional<Player> player = playerService.findWithUsername(basicCredentials.getUsername());
        if (player.isPresent()) {
            // TODO: Fix timing attacks
            // TODO: Bcrypt password
            if (player.get().getPassword().equals(basicCredentials.getPassword())) {
                unitOfWorkService.commit();
                return com.google.common.base.Optional.of(player.get());
            }
        }

        unitOfWorkService.commit();
        return com.google.common.base.Optional.absent();
    }
}
