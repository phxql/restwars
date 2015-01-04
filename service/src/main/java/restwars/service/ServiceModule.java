package restwars.service;

import dagger.Module;
import dagger.Provides;
import restwars.service.building.BuildingService;
import restwars.service.building.impl.BuildingServiceImpl;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.infrastructure.impl.RoundServiceImpl;
import restwars.service.infrastructure.impl.UUIDFactoryImpl;
import restwars.service.location.LocationFactory;
import restwars.service.location.impl.LocationFactoryImpl;
import restwars.service.planet.PlanetService;
import restwars.service.planet.impl.PlanetServiceImpl;
import restwars.service.player.PlayerService;
import restwars.service.player.impl.PlayerServiceImpl;
import restwars.service.resource.ResourceService;
import restwars.service.resource.impl.ResourceServiceImpl;
import restwars.service.ship.ShipService;
import restwars.service.ship.impl.ShipServiceImpl;
import restwars.service.technology.TechnologyService;
import restwars.service.technology.impl.TechnologyServiceImpl;
import restwars.service.telescope.TelescopeService;
import restwars.service.telescope.impl.TelescopeServiceImpl;

@Module(library = true, complete = false)
public class ServiceModule {
    @Provides
    UUIDFactory providesUUIDFactory() {
        return new UUIDFactoryImpl();
    }

    @Provides
    ResourceService providesResourceService(ResourceServiceImpl resourceService) {
        return resourceService;
    }

    @Provides
    PlayerService providesPlayerService(PlayerServiceImpl playerService) {
        return playerService;
    }

    @Provides
    BuildingService providesBuildingService(BuildingServiceImpl buildingService) {
        return buildingService;
    }

    @Provides
    PlanetService providesPlanetService(PlanetServiceImpl planetService) {
        return planetService;
    }

    @Provides
    LocationFactory providesLocationFactory() {
        return new LocationFactoryImpl();
    }

    @Provides
    TechnologyService providesTechnologyService(TechnologyServiceImpl technologyService) {
        return technologyService;
    }

    @Provides
    ShipService providesShipService(ShipServiceImpl shipService) {
        return shipService;
    }

    @Provides
    RoundService providesRoundService(RoundServiceImpl roundService) {
        return roundService;
    }

    @Provides
    TelescopeService telescopeService(TelescopeServiceImpl telescopeService) {
        return telescopeService;
    }
}
