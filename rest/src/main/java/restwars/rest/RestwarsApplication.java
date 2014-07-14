package restwars.rest;

import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.rest.authentication.PlayerAuthenticator;
import restwars.rest.resources.PlayerResource;
import restwars.rest.resources.SystemResource;
import restwars.service.UniverseConfiguration;
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
import restwars.storage.planet.InMemoryPlanetDAO;
import restwars.storage.player.InMemoryPlayerDAO;

public class RestwarsApplication extends Application<RestwarsConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestwarsApplication.class);

    public static void main(String[] args) {
        try {
            new RestwarsApplication().run(args);
        } catch (Exception e) {
            LOGGER.error("Exception while starting the application", e);
        }
    }

    @Override
    public void initialize(Bootstrap<RestwarsConfiguration> restwarsConfigurationBootstrap) {

    }

    @Override
    public void run(RestwarsConfiguration restwarsConfiguration, Environment environment) throws Exception {
        UUIDFactory uuidFactory = new UUIDFactoryImpl();
        LocationFactory locationFactory = new LocationFactoryImpl();

        UniverseConfiguration universeConfiguration = new UniverseConfiguration(2, 2, 2);

        PlanetDAO planetDAO = new InMemoryPlanetDAO();
        PlanetService planetService = new PlanetServiceImpl(uuidFactory, planetDAO);
        PlayerDAO playerDAO = new InMemoryPlayerDAO();
        PlayerService playerService = new PlayerServiceImpl(uuidFactory, playerDAO, planetService, locationFactory, universeConfiguration);

        environment.jersey().register(new BasicAuthProvider<>(new PlayerAuthenticator(playerService), "RESTwars"));

        environment.jersey().register(new SystemResource());
        environment.jersey().register(new PlayerResource(playerService, planetService));
    }
}
