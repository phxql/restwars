package restwars.service.di;

import dagger.Module;
import dagger.Provides;
import restwars.model.UniverseConfiguration;
import restwars.service.mechanics.BuildingMechanics;
import restwars.service.mechanics.PlanetMechanics;
import restwars.service.mechanics.ShipMechanics;
import restwars.service.mechanics.TechnologyMechanics;
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

        if (universeConfiguration.isSpeedUpEverything()) {
            return new SpeedUpBuildingMechanicsImpl(mechanics);
        }

        return mechanics;
    }

    @Provides
    ShipMechanics provideShipMechanics(UniverseConfiguration universeConfiguration) {
        ShipMechanicsImpl mechanics = new ShipMechanicsImpl();

        if (universeConfiguration.isSpeedUpEverything()) {
            return new SpeedUpShipMechanics(mechanics);
        }

        return mechanics;
    }

    @Provides
    TechnologyMechanics providesTechnologyMechanics(UniverseConfiguration universeConfiguration) {
        TechnologyMechanicsImpl mechanics = new TechnologyMechanicsImpl();

        if (universeConfiguration.isSpeedUpEverything()) {
            return new SpeedUpTechnologyMechanicsImpl(mechanics);
        }

        return mechanics;
    }
}
