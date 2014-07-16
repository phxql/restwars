package restwars.rest;

import com.google.common.base.Preconditions;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.UniverseConfiguration;
import restwars.service.building.BuildingService;
import restwars.service.infrastructure.RoundService;

import javax.annotation.Nullable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock implements Managed, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Clock.class);

    private final BuildingService buildingService;
    private final RoundService roundService;
    private final UniverseConfiguration universeConfiguration;

    @Nullable
    private ScheduledExecutorService scheduledExecutorService;

    public Clock(BuildingService buildingService, RoundService roundService, UniverseConfiguration universeConfiguration) {
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

        LOGGER.info("Starting round {}", round);
    }
}
