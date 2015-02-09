package restwars.rest;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import restwars.model.player.Player;

public class DummyAuthenticator implements Authenticator<BasicCredentials, Player> {

    private final String username;
    private final String password;
    private final Player player;

    public DummyAuthenticator(String username, String password, Player player) {
        this.username = username;
        this.password = password;
        this.player = player;
    }

    @Override
    public Optional<Player> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if (basicCredentials.getUsername().equals(username) && basicCredentials.getPassword().equals(password)) {
            return Optional.of(player);
        } else {
            return Optional.absent();
        }
    }
}
