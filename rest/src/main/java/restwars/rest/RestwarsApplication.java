package restwars.rest;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.FilterFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.converter.OverrideConverter;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import dagger.ObjectGraph;
import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.rest.configuration.RestwarsConfiguration;
import restwars.rest.di.CompositionRoot;
import restwars.rest.di.RestWarsModule;
import restwars.rest.doc.SwaggerFilter;
import restwars.rest.integration.database.UnitOfWorkResourceMethodDispatchAdapter;
import restwars.rest.resources.param.LocationParam;
import restwars.service.UniverseConfiguration;
import restwars.service.building.BuildingService;
import restwars.service.planet.Location;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetService;
import restwars.service.player.Player;
import restwars.service.player.PlayerService;
import restwars.service.resource.InsufficientResourcesException;
import restwars.service.resource.Resources;
import restwars.service.ship.*;
import restwars.service.technology.TechnologyService;
import restwars.service.unitofwork.UnitOfWorkService;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
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
    public void initialize(Bootstrap<RestwarsConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<RestwarsConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(RestwarsConfiguration configuration) {
                return configuration.getDatabase();
            }
        });
    }

    @Override
    public void run(RestwarsConfiguration restwarsConfiguration, Environment environment) throws Exception {
        // Start connection pool
        ManagedDataSource dataSource = restwarsConfiguration.getDatabase().build(environment.metrics(), "datasource");
        environment.lifecycle().manage(dataSource);

        UniverseConfiguration universeConfiguration = new UniverseConfiguration(2, 2, 2, new Resources(1000L, 200L, 200L), 5);

        ObjectGraph objectGraph = ObjectGraph.create(new RestWarsModule(universeConfiguration, dataSource));
        CompositionRoot compositionRoot = objectGraph.get(CompositionRoot.class);

        environment.jersey().register(new UnitOfWorkResourceMethodDispatchAdapter(compositionRoot.getUnitOfWorkService()));

        environment.jersey().register(new BasicAuthProvider<>(compositionRoot.getPlayerAuthenticator(), "RESTwars"));
        environment.jersey().register(compositionRoot.getSystemResource());
        environment.jersey().register(compositionRoot.getPlayerResource());
        environment.jersey().register(compositionRoot.getPlanetResource());
        environment.jersey().register(compositionRoot.getTechnologyResource());

        environment.lifecycle().manage(compositionRoot.getClock());

        // loadDemoData(compositionRoot.getUnitOfWorkService(), compositionRoot.getPlayerService(), compositionRoot.getPlanetService(), compositionRoot.getBuildingService(), compositionRoot.getTechnologyService(), compositionRoot.getShipService());

        // Initialize swagger documentation
        registerSwagger(environment);

        registerCorsFilter(environment);
    }

    private void registerSwagger(Environment environment) {
        environment.jersey().register(new ApiListingResourceJSON());
        environment.jersey().register(new ResourceListingProvider());
        environment.jersey().register(new ApiDeclarationProvider());
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("1.0.0");
        config.setBasePath("http://localhost:8080");

        FilterFactory.setFilter(new SwaggerFilter());
        OverrideConverter overrideConverter = new OverrideConverter();

        // TODO: Code smell - This is really ugly
        String jsonString = "{" +
                "  \"id\": \"Date\"" +
                "}";
        overrideConverter.add(LocationParam.class.getName(), jsonString);
        ModelConverters.addConverter(overrideConverter, true);
    }

    private void registerCorsFilter(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

    private void loadDemoData(UnitOfWorkService unitOfWorkService, PlayerService playerService, PlanetService planetService, BuildingService buildingService, TechnologyService technologyService, ShipService shipService) {
        unitOfWorkService.start();

        Player player1 = playerService.createPlayer("player1", "player1");
        List<Planet> player1planets = planetService.findWithOwner(player1);

        Player player2 = playerService.createPlayer("player2", "player2");
        List<Planet> player2planets = planetService.findWithOwner(player2);

        try {
            shipService.manifestShips(player1, player1planets.get(0), new Ships(new Ship(ShipType.MOSQUITO, 2), new Ship(ShipType.COLONY, 1)));
            shipService.manifestShips(player2, player2planets.get(0), new Ships(new Ship(ShipType.MOSQUITO, 1)));

            shipService.sendShipsToPlanet(player1, player1planets.get(0), player2planets.get(0).getLocation(), new Ships(new Ship(ShipType.MOSQUITO, 2)), FlightType.ATTACK);
            shipService.sendShipsToPlanet(player1, player1planets.get(0), new Location(3, 3, 3), new Ships(new Ship(ShipType.COLONY, 1)), FlightType.COLONIZE);
        } catch (NotEnoughShipsException | InvalidFlightException | InsufficientResourcesException e) {
            LOGGER.error("Exception while sending ships to planet", e);
        }

        unitOfWorkService.commit();
    }
}
