package restwars.service;

import dagger.Module;
import dagger.Provides;
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingService;
import restwars.service.building.ConstructionSiteDAO;
import restwars.service.building.impl.BuildingServiceImpl;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.infrastructure.impl.UUIDFactoryImpl;
import restwars.service.location.LocationFactory;
import restwars.service.location.impl.LocationFactoryImpl;
import restwars.service.planet.PlanetDAO;
import restwars.service.planet.PlanetService;
import restwars.service.planet.impl.PlanetServiceImpl;
import restwars.service.player.PlayerDAO;
import restwars.service.player.PlayerService;
import restwars.service.player.impl.PlayerServiceImpl;
import restwars.service.resource.ResourceService;
import restwars.service.resource.impl.ResourceServiceImpl;
import restwars.service.ship.HangarDAO;
import restwars.service.ship.ShipInConstructionDAO;
import restwars.service.ship.ShipService;
import restwars.service.ship.impl.ShipServiceImpl;
import restwars.service.technology.ResearchDAO;
import restwars.service.technology.TechnologyDAO;
import restwars.service.technology.TechnologyService;
import restwars.service.technology.impl.TechnologyServiceImpl;

@Module(library = true, complete = false)
public class ServiceModule {
    @Provides
    ResourceService providesResourceService(BuildingService buildingService, PlanetService planetService) {
        return new ResourceServiceImpl(buildingService, planetService);
    }

    @Provides
    UUIDFactory providesUUIDFactory() {
        return new UUIDFactoryImpl();
    }

    @Provides
    PlayerService providesPlayerService(UUIDFactory uuidFactory, PlayerDAO playerDAO, PlanetService planetService) {
        return new PlayerServiceImpl(uuidFactory, playerDAO, planetService);
    }

    @Provides
    BuildingService providesBuildingService(UUIDFactory uuidFactory, BuildingDAO buildingDAO, RoundService roundService, ConstructionSiteDAO constructionSiteDAO, PlanetDAO planetDAO) {
        return new BuildingServiceImpl(uuidFactory, buildingDAO, roundService, constructionSiteDAO, planetDAO);
    }

    @Provides
    PlanetService providesPlanetService(UUIDFactory uuidFactory, PlanetDAO planetDAO, LocationFactory locationFactory, UniverseConfiguration universeConfiguration, BuildingService buildingService) {
        return new PlanetServiceImpl(uuidFactory, planetDAO, locationFactory, universeConfiguration, buildingService);
    }

    @Provides
    LocationFactory providesLocationFactory() {
        return new LocationFactoryImpl();
    }

    @Provides
    TechnologyService providesTechnologyService(UUIDFactory uuidFactory, BuildingService buildingService, TechnologyDAO technologyDAO, PlanetDAO planetDAO, RoundService roundService, ResearchDAO researchDAO) {
        return new TechnologyServiceImpl(uuidFactory, buildingService, technologyDAO, planetDAO, roundService, researchDAO);
    }

    @Provides
    ShipService providesShipService(HangarDAO hangarDAO, ShipInConstructionDAO shipInConstructionDAO, PlanetDAO planetDAO, UUIDFactory uuidFactory, RoundService roundService) {
        return new ShipServiceImpl(hangarDAO, shipInConstructionDAO, planetDAO, uuidFactory, roundService);
    }
}
