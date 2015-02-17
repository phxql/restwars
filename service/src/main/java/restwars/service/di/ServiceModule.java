package restwars.service.di;

import dagger.Module;
import dagger.Provides;
import restwars.service.building.BuildingService;
import restwars.service.building.impl.BuildingServiceImpl;
import restwars.service.event.EventService;
import restwars.service.event.impl.EventServiceImpl;
import restwars.service.fight.FightService;
import restwars.service.fight.impl.FightServiceImpl;
import restwars.service.flight.FlightService;
import restwars.service.flight.impl.FlightServiceImpl;
import restwars.service.infrastructure.*;
import restwars.service.infrastructure.impl.*;
import restwars.service.location.LocationFactory;
import restwars.service.location.impl.LocationFactoryImpl;
import restwars.service.planet.PlanetService;
import restwars.service.planet.impl.PlanetServiceImpl;
import restwars.service.player.PlayerService;
import restwars.service.player.impl.PlayerServiceImpl;
import restwars.service.resource.ResourceService;
import restwars.service.resource.impl.ResourceServiceImpl;
import restwars.service.security.PasswordService;
import restwars.service.security.impl.Pbkdf2PasswordService;
import restwars.service.ship.ShipService;
import restwars.service.ship.impl.ShipServiceImpl;
import restwars.service.technology.TechnologyService;
import restwars.service.technology.impl.TechnologyServiceImpl;
import restwars.service.telescope.TelescopeService;
import restwars.service.telescope.impl.TelescopeServiceImpl;

import javax.inject.Singleton;

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
    @Singleton
    RoundService providesRoundService(RoundServiceImpl roundService) {
        return roundService;
    }

    @Provides
    TelescopeService providesTelescopeService(TelescopeServiceImpl telescopeService) {
        return telescopeService;
    }

    @Provides
    EventService providesEventService(EventServiceImpl eventService) {
        return eventService;
    }

    @Provides
    FlightService providesFlightService(FlightServiceImpl flightService) {
        return flightService;
    }

    @Provides
    FightService providesFightService(FightServiceImpl fightService) {
        return fightService;
    }

    @Provides
    @Singleton
    PasswordService providesPasswordService(Pbkdf2PasswordService passwordService) {
        return passwordService;
    }

    @Provides
    @Singleton
    RandomNumberGenerator providesRandomNumberGenerator(RandomNumberGeneratorImpl randomNumberGenerator) {
        return randomNumberGenerator;
    }

    @Provides
    @Singleton
    LockService providesLockService() {
        return new LockServiceImpl();
    }

    @Provides
    DateTimeProvider providesDateTimeProvider() {
        return new CurrentDateTimeProvider();
    }
}
