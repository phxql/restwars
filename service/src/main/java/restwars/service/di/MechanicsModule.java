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
    BuildingMechanics provideBuildingMechanics(UniverseConfiguration universeConfiguration, BuildingMechanicsImpl buildingMechanics) {
        if (universeConfiguration.isDebugOptionEnabled()) {
            return new DebugBuildingMechanicsImpl(universeConfiguration, buildingMechanics);
        }

        return buildingMechanics;
    }

    @Provides
    ShipMechanics provideShipMechanics(UniverseConfiguration universeConfiguration, ShipMechanicsImpl shipMechanics) {
        if (universeConfiguration.isDebugOptionEnabled()) {
            return new DebugShipMechanics(universeConfiguration, shipMechanics);
        }

        return shipMechanics;
    }

    @Provides
    TechnologyMechanics providesTechnologyMechanics(UniverseConfiguration universeConfiguration, TechnologyMechanicsImpl technologyMechanics) {
        if (universeConfiguration.isDebugOptionEnabled()) {
            return new DebugTechnologyMechanicsImpl(universeConfiguration, technologyMechanics);
        }

        return technologyMechanics;
    }

    @Provides
    ResourcesMechanics providesResourcesMechanics() {
        return new ResourcesMechanicsImpl();
    }
}
