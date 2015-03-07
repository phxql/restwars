package restwars.rest;

import io.dropwizard.auth.basic.BasicAuthProvider;
import restwars.model.player.Player;
import restwars.rest.integration.locking.LockingFilter;
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

    private final Clock clock;
    private final LockingFilter lockingFilter;
    private final UnitOfWorkService unitOfWorkService;
    private final BasicAuthProvider<Player> basicAuthProvider;

    @Inject
    public CompositionRoot(SystemResource systemResource, PlayerResource playerResource, TechnologyResource technologyResource, PlanetResource planetResource, MetadataResource metadataResource, EventResource eventResource, FightResource fightResource, RootResource rootResource, FlightResource flightResource, Clock clock, LockingFilter lockingFilter, UnitOfWorkService unitOfWorkService, BasicAuthProvider<Player> basicAuthProvider) {
        this.systemResource = systemResource;
        this.playerResource = playerResource;
        this.technologyResource = technologyResource;
        this.planetResource = planetResource;
        this.metadataResource = metadataResource;
        this.eventResource = eventResource;
        this.fightResource = fightResource;
        this.rootResource = rootResource;
        this.flightResource = flightResource;
        this.clock = clock;
        this.lockingFilter = lockingFilter;
        this.unitOfWorkService = unitOfWorkService;
        this.basicAuthProvider = basicAuthProvider;
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

    public LockingFilter getLockingFilter() {
        return lockingFilter;
    }

    public UnitOfWorkService getUnitOfWorkService() {
        return unitOfWorkService;
    }

    public BasicAuthProvider<Player> getBasicAuthProvider() {
        return basicAuthProvider;
    }
}
