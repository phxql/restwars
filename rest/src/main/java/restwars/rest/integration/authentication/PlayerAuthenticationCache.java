package restwars.rest.integration.authentication;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import restwars.model.player.Player;

/**
 * Player authentication cache.
 */
public interface PlayerAuthenticationCache {
    /**
     * Invalidates the cache for the given username and password.
     *
     * @param username Username.
     * @param password Password.
     */
    void invalidate(String username, String password);

    /**
     * Returns the authenticator
     *
     * @return Authenticator.
     */
    Authenticator<BasicCredentials, Player> getAuthenticator();
}
