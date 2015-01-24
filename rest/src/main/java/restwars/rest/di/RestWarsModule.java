package restwars.rest.di;

import com.google.common.base.Preconditions;
import dagger.Module;
import dagger.Provides;
import io.dropwizard.db.ManagedDataSource;
import restwars.mechanics.MechanicsModule;
import restwars.service.ServiceModule;
import restwars.service.UniverseConfiguration;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.JooqDAOModule;
import restwars.storage.jooq.JooqUnitOfWorkService;

import javax.inject.Named;

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

    public RestWarsModule(UniverseConfiguration universeConfiguration, ManagedDataSource managedDataSource, int passwordIterations) {
        this.managedDataSource = Preconditions.checkNotNull(managedDataSource, "managedDataSource");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.passwordIterations = passwordIterations;
    }

    @Provides
    UnitOfWorkService providesUnitOfWorkService() {
        return new JooqUnitOfWorkService(managedDataSource);
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
}
