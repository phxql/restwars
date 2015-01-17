package restwars.rest.di;

import restwars.rest.Clock;
import restwars.rest.authentication.PlayerAuthenticator;
import restwars.rest.resources.*;
import restwars.service.building.BuildingService;
import restwars.service.planet.PlanetService;
import restwars.service.player.PlayerService;
import restwars.service.ship.ShipService;
import restwars.service.technology.TechnologyService;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.inject.Inject;

/**
 * Composition root of the dependency injection.
 */
public class CompositionRoot {
    private final PlayerService playerService;
    private final PlanetService planetService;
    private final BuildingService buildingService;
    private final TechnologyService technologyService;
    private final ShipService shipService;

    private final BuildingSubResource buildingSubResource;
    private final SystemResource systemResource;
    private final PlayerResource playerResource;
    private final TechnologyResource technologyResource;
    private final PlanetResource planetResource;
    private final MetadataResource metadataResource;
    private final EventResource eventResource;
    private final FightResource fightResource;
    private final RootResource rootResource;
    private final FlightResource flightResource;

    private final PlayerAuthenticator playerAuthenticator;
    private final Clock clock;

    private final UnitOfWorkService unitOfWorkService;

    @Inject
    public CompositionRoot(PlayerService playerService, PlanetService planetService, BuildingService buildingService, TechnologyService technologyService, ShipService shipService, BuildingSubResource buildingSubResource, SystemResource systemResource, PlayerResource playerResource, TechnologyResource technologyResource, PlanetResource planetResource, MetadataResource metadataResource, EventResource eventResource, FightResource fightResource, RootResource rootResource, FlightResource flightResource, PlayerAuthenticator playerAuthenticator, Clock clock, UnitOfWorkService unitOfWorkService) {
        this.playerService = playerService;
        this.planetService = planetService;
        this.buildingService = buildingService;
        this.technologyService = technologyService;
        this.shipService = shipService;
        this.buildingSubResource = buildingSubResource;
        this.systemResource = systemResource;
        this.playerResource = playerResource;
        this.technologyResource = technologyResource;
        this.planetResource = planetResource;
        this.metadataResource = metadataResource;
        this.eventResource = eventResource;
        this.fightResource = fightResource;
        this.rootResource = rootResource;
        this.flightResource = flightResource;
        this.playerAuthenticator = playerAuthenticator;
        this.clock = clock;
        this.unitOfWorkService = unitOfWorkService;
    }

    public BuildingService getBuildingService() {
        return buildingService;
    }

    public BuildingSubResource getBuildingSubResource() {
        return buildingSubResource;
    }

    public SystemResource getSystemResource() {
        return systemResource;
    }

    public PlayerResource getPlayerResource() {
        return playerResource;
    }

    public TechnologyResource getTechnologyResource() {
        return technologyResource;
    }

    public PlanetResource getPlanetResource() {
        return planetResource;
    }

    public PlayerAuthenticator getPlayerAuthenticator() {
        return playerAuthenticator;
    }

    public Clock getClock() {
        return clock;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public PlanetService getPlanetService() {
        return planetService;
    }

    public TechnologyService getTechnologyService() {
        return technologyService;
    }

    public ShipService getShipService() {
        return shipService;
    }

    public UnitOfWorkService getUnitOfWorkService() {
        return unitOfWorkService;
    }

    public MetadataResource getMetadataResource() {
        return metadataResource;
    }

    public EventResource getEventResource() {
        return eventResource;
    }

    public FightResource getFightResource() {
        return fightResource;
    }

    public RootResource getRootResource() {
        return rootResource;
    }

    public FlightResource getFlightResource() {
        return flightResource;
    }
}
