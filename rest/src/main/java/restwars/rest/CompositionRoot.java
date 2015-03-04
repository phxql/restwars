package restwars.rest;

import restwars.rest.authentication.PlayerAuthenticator;
import restwars.rest.integration.database.UnitOfWorkListener;
import restwars.rest.integration.locking.LockingListener;
import restwars.rest.resources.*;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.inject.Inject;

/**
 * Composition root of the dependency injection.
 */
public class CompositionRoot {
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
    private final LockingListener lockingListener;
    private final UnitOfWorkListener unitOfWorkListener;
    private final UnitOfWorkService unitOfWorkService;

    @Inject
    public CompositionRoot(SystemResource systemResource, PlayerResource playerResource, TechnologyResource technologyResource, PlanetResource planetResource, MetadataResource metadataResource, EventResource eventResource, FightResource fightResource, RootResource rootResource, FlightResource flightResource, PlayerAuthenticator playerAuthenticator, Clock clock, LockingListener lockingListener, UnitOfWorkListener unitOfWorkListener, UnitOfWorkService unitOfWorkService) {
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
        this.lockingListener = lockingListener;
        this.unitOfWorkListener = unitOfWorkListener;
        this.unitOfWorkService = unitOfWorkService;
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

    public LockingListener getLockingListener() {
        return lockingListener;
    }

    public UnitOfWorkService getUnitOfWorkService() {
        return unitOfWorkService;
    }

    public UnitOfWorkListener getUnitOfWorkListener() {
        return unitOfWorkListener;
    }
}
