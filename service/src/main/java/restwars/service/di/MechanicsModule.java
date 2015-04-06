package restwars.service.di;

import dagger.Module;
import dagger.Provides;
import restwars.model.UniverseConfiguration;
import restwars.service.mechanics.*;
import restwars.service.mechanics.impl.*;

/**
 * Dagger module which provides the mechanics.
 */
@Module(library = true, complete = false)
public class MechanicsModule {
    @Provides
    PlanetMechanics providePlanetMechanics() {
        return PlanetMechanicsImpl.create();
    }

    @Provides
    BuildingMechanics provideBuildingMechanics(UniverseConfiguration universeConfiguration) {
        BuildingMechanicsImpl mechanics = new BuildingMechanicsImpl();

        if (universeConfiguration.isDebugOptionEnabled()) {
            return new DebugBuildingMechanicsImpl(universeConfiguration, mechanics);
        }

        return mechanics;
    }

    @Provides
    ShipMechanics provideShipMechanics(UniverseConfiguration universeConfiguration) {
        ShipMechanicsImpl mechanics = new ShipMechanicsImpl();

        if (universeConfiguration.isDebugOptionEnabled()) {
            return new DebugShipMechanics(universeConfiguration, mechanics);
        }

        return mechanics;
    }

    @Provides
    TechnologyMechanics providesTechnologyMechanics(UniverseConfiguration universeConfiguration) {
        TechnologyMechanicsImpl mechanics = new TechnologyMechanicsImpl();

        if (universeConfiguration.isDebugOptionEnabled()) {
            return new DebugTechnologyMechanicsImpl(universeConfiguration, mechanics);
        }

        return mechanics;
    }

    @Provides
    ResourcesMechanics providesResourcesMechanics() {
        return new ResourcesMechanicsImpl();
    }
}
