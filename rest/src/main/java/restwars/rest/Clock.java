package restwars.rest;

import com.google.common.base.Preconditions;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.UnrecoverableException;
import restwars.model.UniverseConfiguration;
import restwars.service.building.BuildingService;
import restwars.service.flight.FlightService;
import restwars.service.infrastructure.LockService;
import restwars.service.infrastructure.RoundService;
import restwars.service.points.PointsService;
import restwars.service.resource.ResourceService;
import restwars.service.ship.ShipService;
import restwars.service.technology.TechnologyService;
import restwars.service.telescope.TelescopeService;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock implements Managed, Runnable {
    /**
     * Callback for the next round handler.
     */
    public interface NextRoundCallback {
        /**
         * Is called when the next round has been started.
         *
         * @param round Round, which has been started.
         */
        void onNextRound(long round);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Clock.class);

    private final BuildingService buildingService;
    private final RoundService roundService;
    private final ResourceService resourceService;
    private final TechnologyService technologyService;
    private final ShipService shipService;
    private final FlightService flightService;
    private final TelescopeService telescopeService;
    private final UniverseConfiguration universeConfiguration;

    private final LockService lockService;
    private final UnitOfWorkService unitOfWorkService;
    private final PointsService pointsService;
    private Optional<NextRoundCallback> nextRoundCallback = Optional.empty();

    @Nullable
    private ScheduledExecutorService scheduledExecutorService;

    @Inject
    public Clock(BuildingService buildingService, RoundService roundService, UniverseConfiguration universeConfiguration, ResourceService resourceService, TechnologyService technologyService, ShipService shipService, UnitOfWorkService unitOfWorkService, TelescopeService telescopeService, FlightService flightService, LockService lockService, PointsService pointsService) {
        this.pointsService = Preconditions.checkNotNull(pointsService, "pointsService");
        this.lockService = Preconditions.checkNotNull(lockService, "lockService");
        this.flightService = Preconditions.checkNotNull(flightService, "flightService");
        this.unitOfWorkService = Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");
        this.shipService = Preconditions.checkNotNull(shipService, "shipService");
        this.technologyService = Preconditions.checkNotNull(technologyService, "technologyService");
        this.resourceService = Preconditions.checkNotNull(resourceService, "resourceService");
        this.universeConfiguration = Preconditions.checkNotNull(universeConfiguration, "universeConfiguration");
        this.roundService = Preconditions.checkNotNull(roundService, "roundService");
        this.buildingService = Preconditions.checkNotNull(buildingService, "buildingService");
        this.telescopeService = Preconditions.checkNotNull(telescopeService, "telescopeService");
    }

    @Override
    public void start() throws Exception {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(); // TODO: Code smell - IoC
        scheduledExecutorService.scheduleAtFixedRate(this, universeConfiguration.getRoundTimeInSeconds(), universeConfiguration.getRoundTimeInSeconds(), TimeUnit.SECONDS);

        unitOfWorkService.start();
        roundService.initialize();
        unitOfWorkService.commit();
    }

    @Override
    public void stop() throws Exception {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
            scheduledExecutorService.shutdownNow();
        }
    }

    public void setNextRoundCallback(NextRoundCallback nextRoundCallback) {
        this.nextRoundCallback = Optional.of(nextRoundCallback);
    }

    @Override
    public void run() {
        long round = -1;
        lockService.beforeClock();
        try {
            unitOfWorkService.start();
            try {
                round = roundService.nextRound();
                LOGGER.info("Starting round {}", round);

                buildingService.finishConstructionSites();
                technologyService.finishResearches();
                shipService.finishShipsInConstruction();
                resourceService.gatherResourcesOnAllPlanets();
                flightService.finishFlights();
                telescopeService.detectFlights();

                if (round % universeConfiguration.getCalculatePointsEvery() == 0) {
                    pointsService.calculatePointsForAllPlayers();
                }

                unitOfWorkService.commit();
            } catch (Exception e) {
                unitOfWorkService.abort();
                LOGGER.error("Clock thread crashed with exception", e);
                throw new UnrecoverableException("Exception", e);
            }
        } finally {
            lockService.afterClock();
        }

        if (nextRoundCallback.isPresent()) {
            nextRoundCallback.get().onNextRound(round);
        }
    }
}
