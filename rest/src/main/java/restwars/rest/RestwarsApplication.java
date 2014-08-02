package restwars.rest;

import dagger.ObjectGraph;
import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.rest.di.CompositionRoot;
import restwars.rest.di.RestWarsModule;
import restwars.service.UniverseConfiguration;
import restwars.service.building.BuildingService;
import restwars.service.building.BuildingType;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.ship.ShipService;
import restwars.service.ship.ShipType;
import restwars.service.technology.TechnologyService;
import restwars.service.technology.TechnologyType;

import java.util.List;

public class RestwarsApplication extends Application<RestwarsConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestwarsApplication.class);

    public static void main(String[] args) throws Exception {
        try {
            new RestwarsApplication().run(args);
        } catch (Exception e) {
            LOGGER.error("Exception while starting the application", e);
            throw e;
        }
    }

    @Override
    public void initialize(Bootstrap<RestwarsConfiguration> restwarsConfigurationBootstrap) {
    }

    @Override
    public void run(RestwarsConfiguration restwarsConfiguration, Environment environment) throws Exception {
        UniverseConfiguration universeConfiguration = new UniverseConfiguration(2, 2, 2, 1000L, 200L, 200L, 5);
        ObjectGraph objectGraph = ObjectGraph.create(new RestWarsModule(universeConfiguration));
        CompositionRoot compositionRoot = objectGraph.get(CompositionRoot.class);

        environment.jersey().register(new BasicAuthProvider<>(compositionRoot.getPlayerAuthenticator(), "RESTwars"));
        environment.jersey().register(compositionRoot.getSystemResource());
        environment.jersey().register(compositionRoot.getPlayerResource());
        environment.jersey().register(compositionRoot.getPlanetResource());
        environment.jersey().register(compositionRoot.getTechnologyResource());

        environment.lifecycle().manage(compositionRoot.getClock());

        loadDemoData(compositionRoot.getPlayerService(), compositionRoot.getPlanetService(), compositionRoot.getBuildingService(), compositionRoot.getTechnologyService(), compositionRoot.getShipService());
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
