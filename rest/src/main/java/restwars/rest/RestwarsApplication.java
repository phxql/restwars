package restwars.rest;

import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.rest.authentication.PlayerAuthenticator;
import restwars.rest.resources.*;
import restwars.service.UniverseConfiguration;
import restwars.service.building.*;
import restwars.service.building.impl.BuildingServiceImpl;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.infrastructure.impl.RoundServiceImpl;
import restwars.service.infrastructure.impl.UUIDFactoryImpl;
import restwars.service.location.LocationFactory;
import restwars.service.location.impl.LocationFactoryImpl;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.planet.PlanetService;
import restwars.service.planet.impl.PlanetServiceImpl;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.player.PlayerService;
import restwars.service.player.impl.PlayerServiceImpl;
import restwars.service.resource.ResourceService;
import restwars.service.resource.impl.ResourceServiceImpl;
import restwars.storage.building.InMemoryBuildingDAO;
import restwars.storage.building.InMemoryConstructionSiteDAO;
import restwars.storage.planet.InMemoryPlanetDAO;
import restwars.storage.player.InMemoryPlayerDAO;

import java.util.List;

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

        UniverseConfiguration universeConfiguration = new UniverseConfiguration(2, 2, 2, 1000L, 200L, 200L, 1);

        BuildingDAO buildingDAO = new InMemoryBuildingDAO();
        PlanetDAO planetDAO = new InMemoryPlanetDAO();
        PlayerDAO playerDAO = new InMemoryPlayerDAO();
        ConstructionSiteDAO constructionSiteDAO = new InMemoryConstructionSiteDAO();

        RoundService roundService = new RoundServiceImpl();
        BuildingService buildingService = new BuildingServiceImpl(uuidFactory, buildingDAO, roundService, constructionSiteDAO, planetDAO);
        PlanetService planetService = new PlanetServiceImpl(uuidFactory, planetDAO, locationFactory, universeConfiguration, buildingService);
        PlayerService playerService = new PlayerServiceImpl(uuidFactory, playerDAO, planetService);
        ResourceService resourceService = new ResourceServiceImpl(buildingService, planetService);

        environment.jersey().register(new BasicAuthProvider<>(new PlayerAuthenticator(playerService), "RESTwars"));

        BuildingSubResource buildingSubResource = new BuildingSubResource(buildingService, planetService);
        ConstructionSiteSubResource constructionSiteSubResource = new ConstructionSiteSubResource(planetService, buildingService);

        environment.jersey().register(new SystemResource());
        environment.jersey().register(new PlayerResource(playerService, planetService));
        environment.jersey().register(new PlanetResource(planetService, buildingSubResource, constructionSiteSubResource));

        loadDemoData(playerService, planetService, buildingService);

        environment.lifecycle().manage(new Clock(buildingService, roundService, universeConfiguration, resourceService));
    }

    private void loadDemoData(PlayerService playerService, PlanetService planetService, BuildingService buildingService) {
        Player moe = playerService.createPlayer("moe", "moe");
        List<Planet> planets = planetService.findWithOwner(moe);

        for (Planet planet : planets) {
            LOGGER.info("Moe has a planet at {}", planet.getLocation());

            try {
                buildingService.constructBuilding(planet, BuildingType.CRYSTAL_MINE);
            } catch (InsufficientResourcesException e) {
                LOGGER.error("Exception while constructing a crystal mine", e);
            }
        }
    }
}
