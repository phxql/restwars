package restwars.storage;

import dagger.Module;
import dagger.Provides;
import restwars.service.building.BuildingDAO;
import restwars.service.building.ConstructionSiteDAO;
import restwars.service.event.EventDAO;
import restwars.service.infrastructure.RoundDAO;
import restwars.service.planet.PlanetDAO;
import restwars.service.player.PlayerDAO;
import restwars.service.ship.FightDAO;
import restwars.service.ship.FlightDAO;
import restwars.service.ship.HangarDAO;
import restwars.service.ship.ShipInConstructionDAO;
import restwars.service.technology.ResearchDAO;
import restwars.service.technology.TechnologyDAO;
import restwars.storage.building.JooqBuildingDAO;
import restwars.storage.building.JooqConstructionSiteDAO;
import restwars.storage.event.JooqEventDAO;
import restwars.storage.fight.JooqFightDAO;
import restwars.storage.planet.JooqPlanetDAO;
import restwars.storage.player.JooqPlayerDAO;
import restwars.storage.round.JooqRoundDAO;
import restwars.storage.ship.JooqFlightDAO;
import restwars.storage.ship.JooqHangarDAO;
import restwars.storage.ship.JooqShipInConstructionDAO;
import restwars.storage.technology.JooqResearchDAO;
import restwars.storage.technology.JooqTechnologyDAO;

import javax.inject.Singleton;

@Module(library = true, complete = false)
public class JooqDAOModule {
    @Provides
    PlayerDAO providesPlayerDAO(JooqPlayerDAO jooqPlayerDAO) {
        return jooqPlayerDAO;
    }

    @Provides
    PlanetDAO providesPlanetDAO(JooqPlanetDAO jooqPlanetDAO) {
        return jooqPlanetDAO;
    }

    @Provides
    BuildingDAO provideBuildingDAO(JooqBuildingDAO jooqBuildingDAO) {
        return jooqBuildingDAO;
    }

    @Provides
    ConstructionSiteDAO providesConstructionSiteDAO(JooqConstructionSiteDAO jooqConstructionSiteDAO) {
        return jooqConstructionSiteDAO;
    }

    @Provides
    TechnologyDAO providesTechnologyDAO(JooqTechnologyDAO jooqTechnologyDAO) {
        return jooqTechnologyDAO;
    }

    @Provides
    ResearchDAO providesResearchDAO(JooqResearchDAO jooqResearchDAO) {
        return jooqResearchDAO;
    }

    @Provides
    ShipInConstructionDAO providesShipInConstructionDAO(JooqShipInConstructionDAO jooqShipInConstructionDAO) {
        return jooqShipInConstructionDAO;
    }

    @Provides
    @Singleton
    HangarDAO providesHangarDAO(JooqHangarDAO jooqHangarDAO) {
        return jooqHangarDAO;
    }

    @Provides
    @Singleton
    RoundDAO providesRoundDAO(JooqRoundDAO jooqRoundDAO) {
        return jooqRoundDAO;
    }

    @Provides
    @Singleton
    FlightDAO providesFlightDAO(JooqFlightDAO jooqFlightDAO) {
        return jooqFlightDAO;
    }

    @Provides
    @Singleton
    EventDAO providesEventDAO(JooqEventDAO jooqEventDAO) {
        return jooqEventDAO;
    }

    @Provides
    @Singleton
    FightDAO providesFightDAO(JooqFightDAO jooqFightDAO) {
        return jooqFightDAO;
    }
}
