package restwars.rest.di;

import restwars.rest.Clock;
import restwars.rest.authentication.PlayerAuthenticator;
import restwars.rest.resources.*;
import restwars.service.building.BuildingService;
import restwars.service.planet.PlanetService;
import restwars.service.player.PlayerService;
import restwars.service.ship.ShipService;
import restwars.service.technology.TechnologyService;

import javax.inject.Inject;

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
    private final PlayerAuthenticator playerAuthenticator;
    private final Clock clock;

    @Inject
    public CompositionRoot(PlayerService playerService, PlanetService planetService, BuildingService buildingService, TechnologyService technologyService, ShipService shipService, BuildingSubResource buildingSubResource, SystemResource systemResource, PlayerResource playerResource, TechnologyResource technologyResource, PlanetResource planetResource, PlayerAuthenticator playerAuthenticator, Clock clock) {
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
        this.playerAuthenticator = playerAuthenticator;
        this.clock = clock;
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
}
