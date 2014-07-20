package restwars.rest;

import com.google.common.base.Preconditions;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.building.BuildingService;
import restwars.service.infrastructure.RoundService;
import restwars.service.resource.ResourceService;
import restwars.service.ship.ShipService;
import restwars.service.technology.TechnologyService;

import javax.annotation.Nullable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock implements Managed, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Clock.class);

    private final BuildingService buildingService;
    private final RoundService roundService;
    private final ResourceService resourceService;
    private final TechnologyService technologyService;
    private final ShipService shipService;
    private final UniverseConfiguration universeConfiguration;

    @Nullable
    private ScheduledExecutorService scheduledExecutorService;

    public Clock(BuildingService buildingService, RoundService roundService, UniverseConfiguration universeConfiguration, ResourceService resourceService, TechnologyService technologyService, ShipService shipService) {
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
        this.technologyService = Preconditions.checkNotNull(technologyService, "technologyService");
        this.resourceService = Preconditions.checkNotNull(resourceService, "resourceService");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
    }

    @Override
    public void start() throws Exception {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(); // TODO: IoC
        scheduledExecutorService.scheduleAtFixedRate(this, universeConfiguration.getRoundTimeInSeconds(), universeConfiguration.getRoundTimeInSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
            scheduledExecutorService.shutdownNow();
        }
    }

    @Override
    public void run() {
        long round = roundService.nextRound();

        buildingService.finishConstructionSites();
        technologyService.finishResearches();
        shipService.finishShipsInConstruction();
        resourceService.gatherResourcesOnAllPlanets();

        LOGGER.info("Starting round {}", round);
    }
}
