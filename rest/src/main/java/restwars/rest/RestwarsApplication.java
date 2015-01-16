package restwars.rest;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.FilterFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
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
import restwars.rest.doc.ModelConverter;
import restwars.rest.doc.SwaggerFilter;
import restwars.rest.integration.database.UnitOfWorkResourceMethodDispatchAdapter;
import restwars.service.UniverseConfiguration;
import restwars.service.resource.Resources;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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
    public void run(RestwarsConfiguration configuration, Environment environment) throws Exception {
        // Start connection pool
        ManagedDataSource dataSource = configuration.getDatabase().build(environment.metrics(), "datasource");
        environment.lifecycle().manage(dataSource);

        UniverseConfiguration universeConfiguration = new UniverseConfiguration(
                configuration.getGalaxies(), configuration.getSolarSystems(), configuration.getPlanets(),
                new Resources(1000L, 200L, 200L), configuration.getRoundTime()
        );

        ObjectGraph objectGraph = ObjectGraph.create(new RestWarsModule(universeConfiguration, dataSource));
        CompositionRoot compositionRoot = objectGraph.get(CompositionRoot.class);

        environment.jersey().register(new UnitOfWorkResourceMethodDispatchAdapter(compositionRoot.getUnitOfWorkService()));

        environment.jersey().register(new BasicAuthProvider<>(compositionRoot.getPlayerAuthenticator(), "RESTwars"));
        environment.jersey().register(compositionRoot.getSystemResource());
        environment.jersey().register(compositionRoot.getPlayerResource());
        environment.jersey().register(compositionRoot.getPlanetResource());
        environment.jersey().register(compositionRoot.getTechnologyResource());
        environment.jersey().register(compositionRoot.getEventResource());
        environment.jersey().register(compositionRoot.getFightResource());
        environment.jersey().register(compositionRoot.getMetadataResource());

        environment.lifecycle().manage(compositionRoot.getClock());

        // loadDemoData(compositionRoot.getUnitOfWorkService(), compositionRoot.getPlayerService(), compositionRoot.getPlanetService(), compositionRoot.getBuildingService(), compositionRoot.getTechnologyService(), compositionRoot.getShipService());

        // Initialize swagger documentation
        registerSwagger(environment, configuration);

        registerCorsFilter(environment);
    }

    private void registerSwagger(Environment environment, RestwarsConfiguration configuration) {
        environment.jersey().register(new ApiListingResourceJSON());
        environment.jersey().register(new ResourceListingProvider());
        environment.jersey().register(new ApiDeclarationProvider());
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("1.0.0");
        config.setBasePath(configuration.getPublicUrl());

        FilterFactory.setFilter(new SwaggerFilter());
        ModelConverter.register();
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
}
