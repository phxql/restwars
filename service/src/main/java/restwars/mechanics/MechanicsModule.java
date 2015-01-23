package restwars.mechanics;

import dagger.Module;
import dagger.Provides;
import restwars.mechanics.impl.PlanetMechanicsImpl;

/**
 * Dagger module which provides the mechanics.
 */
@Module(library = true, complete = false)
public class MechanicsModule {
    @Provides
    PlanetMechanics providePlanetMechanics() {
        return PlanetMechanicsImpl.create();
    }
}
