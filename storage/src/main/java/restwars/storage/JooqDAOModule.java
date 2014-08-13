package restwars.storage;

import dagger.Module;
import dagger.Provides;
import restwars.service.building.BuildingDAO;
import restwars.service.building.ConstructionSiteDAO;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.impl.RoundServiceImpl;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.PlayerDAO;
import restwars.service.ship.FlightDAO;
import restwars.service.ship.HangarDAO;
import restwars.service.ship.ShipInConstructionDAO;
import restwars.service.technology.ResearchDAO;
import restwars.service.technology.TechnologyDAO;
import restwars.storage.building.InMemoryBuildingDAO;
import restwars.storage.building.InMemoryConstructionSiteDAO;
import restwars.storage.planet.InMemoryPlanetDAO;
import restwars.storage.player.JooqPlayerDAO;
import restwars.storage.ship.InMemoryFlightDAO;
import restwars.storage.ship.InMemoryHangarDAO;
import restwars.storage.ship.InMemoryShipInConstructionDAO;
import restwars.storage.technology.InMemoryResearchDAO;
import restwars.storage.technology.InMemoryTechnologyDAO;

import javax.inject.Singleton;

@Module(library = true, complete = false)
public class JooqDAOModule {
    @Provides
    @Singleton
    PlayerDAO providesPlayerDAO(JooqPlayerDAO jooqPlayerDAO) {
        return jooqPlayerDAO;
    }

    @Provides
    @Singleton
    HangarDAO providesHangarDAO() {
        return new InMemoryHangarDAO();
    }

    @Provides
    @Singleton
    ShipInConstructionDAO providesShipInConstructionDAO() {
        return new InMemoryShipInConstructionDAO();
    }

    @Provides
    @Singleton
    BuildingDAO provideBuildingDAO() {
        return new InMemoryBuildingDAO();
    }

    @Provides
    @Singleton
    ConstructionSiteDAO providesConstructionSiteDAO() {
        return new InMemoryConstructionSiteDAO();
    }

    @Provides
    @Singleton
    PlanetDAO providesPlanetDAO() {
        return new InMemoryPlanetDAO();
    }

    @Provides
    @Singleton
    ResearchDAO providesResearchDAO() {
        return new InMemoryResearchDAO();
    }

    @Provides
    @Singleton
    TechnologyDAO providesTechnologyDAO() {
        return new InMemoryTechnologyDAO();
    }

    @Provides
    @Singleton
    RoundService providesRoundService() {
        return new RoundServiceImpl();
    }

    @Provides
    @Singleton
    FlightDAO providesFlightDAO() {
        return new InMemoryFlightDAO();
    }
}
