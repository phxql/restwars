package restwars.rest.di;

import com.google.common.base.Preconditions;
import dagger.Module;
import dagger.Provides;
import restwars.rest.configuration.DatabaseConfiguration;
import restwars.service.ServiceModule;
import restwars.service.UniverseConfiguration;
import restwars.storage.JdbcConnection;
import restwars.storage.JooqDAOModule;

import javax.inject.Singleton;

@Module(injects = CompositionRoot.class, includes = {
        JooqDAOModule.class, ServiceModule.class
})
public class RestWarsModule {
    private final UniverseConfiguration universeConfiguration;
    private final DatabaseConfiguration databaseConfiguration;

    public RestWarsModule(UniverseConfiguration universeConfiguration, DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
    }

    @Provides
    @Singleton
    JdbcConnection provideJdbcConnection() {
        // TODO: Move this into the JooqDAOModule if possible!

        return new JdbcConnection(
                databaseConfiguration.getDriverClass(), databaseConfiguration.getUrl(),
                databaseConfiguration.getUsername(), databaseConfiguration.getPassword(),
                databaseConfiguration.getSqlDialect()
        );
    }

    @Provides
    UniverseConfiguration providesUniverseConfiguration() {
        return universeConfiguration;
    }
}
