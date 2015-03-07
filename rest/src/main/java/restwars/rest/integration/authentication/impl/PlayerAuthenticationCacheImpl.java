package restwars.rest.integration.authentication.impl;

import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.CacheBuilderSpec;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import restwars.model.player.Player;
import restwars.rest.integration.authentication.PlayerAuthenticationCache;

import javax.inject.Singleton;

@Singleton
public class PlayerAuthenticationCacheImpl extends CachingAuthenticator<BasicCredentials, Player> implements PlayerAuthenticationCache {
    public PlayerAuthenticationCacheImpl(MetricRegistry metricRegistry, Authenticator<BasicCredentials, Player> authenticator, CacheBuilderSpec cacheSpec) {
        super(metricRegistry, authenticator, cacheSpec);
    }

    @Override
    public void invalidate(String username, String password) {
        invalidate(new BasicCredentials(username, password));
    }

    @Override
    public Authenticator<BasicCredentials, Player> getAuthenticator() {
        return this;
    }
}
