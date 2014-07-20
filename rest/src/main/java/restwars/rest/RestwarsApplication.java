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
import restwars.service.building.BuildingDAO;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;
import restwars.service.building.ConstructionSiteDAO;
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
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.ResourceService;
import restwars.service.resource.impl.ResourceServiceImpl;
import restwars.service.ship.HangarDAO;
import restwars.service.ship.ShipInConstructionDAO;
import restwars.service.ship.ShipService;
import restwars.service.ship.ShipType;
import restwars.service.ship.impl.ShipServiceImpl;
import restwars.service.technology.ResearchDAO;
import restwars.service.technology.TechnologyDAO;
import restwars.service.technology.TechnologyService;
import restwars.service.technology.TechnologyType;
import restwars.service.technology.impl.TechnologyServiceImpl;
import restwars.storage.building.InMemoryBuildingDAO;
import restwars.storage.building.InMemoryConstructionSiteDAO;
import restwars.storage.planet.InMemoryPlanetDAO;
import restwars.storage.player.InMemoryPlayerDAO;
import restwars.storage.ship.InMemoryHangarDAO;
import restwars.storage.ship.InMemoryShipInConstructionDAO;
import restwars.storage.technology.InMemoryResearchDAO;
import restwars.storage.technology.InMemoryTechnologyDAO;

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
        ResearchDAO researchDAO = new InMemoryResearchDAO();
        TechnologyDAO technologyDAO = new InMemoryTechnologyDAO();
        HangarDAO hangarDAO = new InMemoryHangarDAO();
        ShipInConstructionDAO shipInConstructionDAO = new InMemoryShipInConstructionDAO();

        RoundService roundService = new RoundServiceImpl();
        BuildingService buildingService = new BuildingServiceImpl(uuidFactory, buildingDAO, roundService, constructionSiteDAO, planetDAO);
        PlanetService planetService = new PlanetServiceImpl(uuidFactory, planetDAO, locationFactory, universeConfiguration, buildingService);
        PlayerService playerService = new PlayerServiceImpl(uuidFactory, playerDAO, planetService);
        ResourceService resourceService = new ResourceServiceImpl(buildingService, planetService);
        TechnologyService technologyService = new TechnologyServiceImpl(uuidFactory, buildingService, technologyDAO, planetDAO, roundService, researchDAO);
        ShipService shipService = new ShipServiceImpl(hangarDAO, shipInConstructionDAO, planetDAO, uuidFactory, roundService);

        environment.jersey().register(new BasicAuthProvider<>(new PlayerAuthenticator(playerService), "RESTwars"));

        BuildingSubResource buildingSubResource = new BuildingSubResource(buildingService, planetService);
        ConstructionSiteSubResource constructionSiteSubResource = new ConstructionSiteSubResource(planetService, buildingService);

        environment.jersey().register(new SystemResource());
        environment.jersey().register(new PlayerResource(playerService, planetService));
        environment.jersey().register(new PlanetResource(planetService, buildingSubResource, constructionSiteSubResource));

        loadDemoData(playerService, planetService, buildingService, technologyService, shipService);

        environment.lifecycle().manage(new Clock(buildingService, roundService, universeConfiguration, resourceService, technologyService, shipService));
    }

    private void loadDemoData(PlayerService playerService, PlanetService planetService, BuildingService buildingService, TechnologyService technologyService, ShipService shipService) {
        Player moe = playerService.createPlayer("moe", "moe");
        List<Planet> planets = planetService.findWithOwner(moe);

        if (!planets.isEmpty()) {
            try {
                technologyService.researchTechnology(moe, planets.get(0), TechnologyType.CRYSTAL_MINE_EFFICIENCY);
            } catch (InsufficientResourcesException e) {
                LOGGER.error("Exception while researching crystal mine efficiency", e);
            }
        }

        for (Planet planet : planets) {
            LOGGER.info("Moe has a planet at {}", planet.getLocation());

            try {
                buildingService.constructBuilding(planet, BuildingType.CRYSTAL_MINE);
            } catch (InsufficientResourcesException e) {
                LOGGER.error("Exception while constructing a crystal mine", e);
            }

            try {
                shipService.buildShip(moe, planet, ShipType.MOSQUITO);
            } catch (InsufficientResourcesException e) {
                LOGGER.error("Exception while building a mosquito", e);
            }
        }
    }
}
