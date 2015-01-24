package restwars.mechanics;

import dagger.Module;
import dagger.Provides;
import restwars.mechanics.impl.BuildingMechanicsImpl;
import restwars.mechanics.impl.PlanetMechanicsImpl;
import restwars.mechanics.impl.ShipMechanicsImpl;

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
}
