package restwars.rest.di;

import com.google.common.base.Preconditions;
import dagger.Module;
import dagger.Provides;
import restwars.service.ServiceModule;
import restwars.service.UniverseConfiguration;
import restwars.storage.InMemoryDAOModule;

@Module(injects = CompositionRoot.class, includes = {
        InMemoryDAOModule.class, ServiceModule.class
})
public class RestWarsModule {
    private final UniverseConfiguration universeConfiguration;

    public RestWarsModule(UniverseConfiguration universeConfiguration) {
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
    }

    @Provides
    UniverseConfiguration providesUniverseConfiguration() {
        return universeConfiguration;
    }
}
