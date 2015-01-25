package restwars.service.di;

import dagger.Module;
import dagger.Provides;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.mechanics.PlanetMechanics;
import restwars.service.mechanics.ShipMechanics;
import restwars.service.mechanics.TechnologyMechanics;
import restwars.service.mechanics.impl.BuildingMechanicsImpl;
import restwars.service.mechanics.impl.PlanetMechanicsImpl;
import restwars.service.mechanics.impl.ShipMechanicsImpl;
import restwars.service.mechanics.impl.TechnologyMechanicsImpl;

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
    BuildingMechanics provideBuildingMechanics() {
        return new BuildingMechanicsImpl();
    }

    @Provides
    ShipMechanics provideShipMechanics() {
        return new ShipMechanicsImpl();
    }

    @Provides
    TechnologyMechanics providesTechnologyMechanics() {
        return new TechnologyMechanicsImpl();
    }
}
