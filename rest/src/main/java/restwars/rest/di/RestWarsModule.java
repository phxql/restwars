package restwars.rest.di;

import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilderSpec;
import dagger.Module;
import dagger.Provides;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.ManagedDataSource;
import restwars.model.UniverseConfiguration;
import restwars.model.player.Player;
import restwars.rest.CompositionRoot;
import restwars.rest.authentication.PlayerAuthenticator;
import restwars.rest.integration.authentication.PlayerAuthenticationCache;
import restwars.rest.integration.authentication.impl.PlayerAuthenticationCacheImpl;
import restwars.service.di.MechanicsModule;
import restwars.service.di.ServiceModule;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.di.JooqDAOModule;
import restwars.storage.jooq.JooqUnitOfWorkFactory;
import restwars.storage.jooq.JooqUnitOfWorkService;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Dagger module for RESTwars.
 */
@Module(injects = CompositionRoot.class, includes = {
        JooqDAOModule.class, ServiceModule.class, MechanicsModule.class
})
public class RestWarsModule {
    private final UniverseConfiguration universeConfiguration;
    private final ManagedDataSource managedDataSource;
    private final int passwordIterations;
    private final CacheBuilderSpec credentialsCache;
    private final MetricRegistry metricRegistry;
    private final String securityRealm;

    public RestWarsModule(UniverseConfiguration universeConfiguration, ManagedDataSource managedDataSource, int passwordIterations, CacheBuilderSpec credentialsCache, MetricRegistry metricRegistry, String securityRealm) {
        this.securityRealm = Preconditions.checkNotNull(securityRealm, "securityRealm");
        this.metricRegistry = Preconditions.checkNotNull(metricRegistry, "metricRegistry");
        this.credentialsCache = Preconditions.checkNotNull(credentialsCache, "credentialsCache");
        this.managedDataSource = Preconditions.checkNotNull(managedDataSource, "managedDataSource");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.passwordIterations = passwordIterations;
    }

    @Provides
    UnitOfWorkService providesUnitOfWorkService(JooqUnitOfWorkFactory jooqUnitOfWorkFactory) {
        return new JooqUnitOfWorkService(managedDataSource, jooqUnitOfWorkFactory);
    }

    @Provides
    UniverseConfiguration providesUniverseConfiguration() {
        return universeConfiguration;
    }

    @Provides
    @Named("passwordIterations")
    int providesPasswordIterations() {
        return passwordIterations;
    }

    @Provides
    @Singleton
    PlayerAuthenticationCache providesPlayerAuthenticationCache(PlayerAuthenticator playerAuthenticator) {
        return new PlayerAuthenticationCacheImpl(metricRegistry, playerAuthenticator, credentialsCache);
    }

    @Provides
    BasicAuthProvider<Player> providesAuthProvider(PlayerAuthenticationCache playerAuthenticationCache) {
        return new BasicAuthProvider<>(playerAuthenticationCache.getAuthenticator(), securityRealm);
    }
}
