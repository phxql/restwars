package restwars.restclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import restwars.restapi.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class RestWarsClient {
    private final Client client;
    private final String url;
    private PlayerResource playerResource;
    private MetadataResource metadataResource;
    private TechnologyResource technologyResource;
    private BuildingResource buildingResource;
    private PlanetResource planetResource;
    private ConstructionSiteResource constructionSiteResource;
    private FlightResource flightResource;
    private ResearchResource researchResource;
    private ShipResource shipResource;
    private ShipInConstructionResource shipInConstructionResource;
    private SystemResource systemResource;
    private TelescopeResource telescopeResource;
    private EventResource eventResource;
    private FightResource fightResource;
    private RootResource rootResource;
    private WebsocketResource websocketResource;

    public RestWarsClient(String url) {
        client = ClientBuilder.newClient();
        enableJodaTime();

        this.url = url;

        createProxies(url);
    }

    private void enableJodaTime() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JodaModule());

        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(objectMapper);

        client.register(jacksonProvider);
    }

    public void setCredentials(String username, String password) {
        HttpAuthenticationFeature authentication = HttpAuthenticationFeature.basic(username, password);
        client.register(authentication);

        // We need to recreate the proxies to apply the changes
        createProxies(url);
    }

    private void createProxies(String url) {
        WebTarget target = client.target(url);
        playerResource = WebResourceFactory.newResource(PlayerResource.class, target);
        metadataResource = WebResourceFactory.newResource(MetadataResource.class, target);
        technologyResource = WebResourceFactory.newResource(TechnologyResource.class, target);
        buildingResource = WebResourceFactory.newResource(BuildingResource.class, target);
        planetResource = WebResourceFactory.newResource(PlanetResource.class, target);
        constructionSiteResource = WebResourceFactory.newResource(ConstructionSiteResource.class, target);
        flightResource = WebResourceFactory.newResource(FlightResource.class, target);
        researchResource = WebResourceFactory.newResource(ResearchResource.class, target);
        shipResource = WebResourceFactory.newResource(ShipResource.class, target);
        shipInConstructionResource = WebResourceFactory.newResource(ShipInConstructionResource.class, target);
        systemResource = WebResourceFactory.newResource(SystemResource.class, target);
        telescopeResource = WebResourceFactory.newResource(TelescopeResource.class, target);
        eventResource = WebResourceFactory.newResource(EventResource.class, target);
        fightResource = WebResourceFactory.newResource(FightResource.class, target);
        rootResource = WebResourceFactory.newResource(RootResource.class, target);
        websocketResource = WebResourceFactory.newResource(WebsocketResource.class, target);
    }

    public PlayerResource getPlayerResource() {
        return playerResource;
    }

    public MetadataResource getMetadataResource() {
        return metadataResource;
    }

    public TechnologyResource getTechnologyResource() {
        return technologyResource;
    }

    public BuildingResource getBuildingResource() {
        return buildingResource;
    }

    public PlanetResource getPlanetResource() {
        return planetResource;
    }

    public ConstructionSiteResource getConstructionSiteResource() {
        return constructionSiteResource;
    }

    public FlightResource getFlightResource() {
        return flightResource;
    }

    public ResearchResource getResearchResource() {
        return researchResource;
    }

    public ShipResource getShipResource() {
        return shipResource;
    }

    public ShipInConstructionResource getShipInConstructionResource() {
        return shipInConstructionResource;
    }

    public SystemResource getSystemResource() {
        return systemResource;
    }

    public TelescopeResource getTelescopeResource() {
        return telescopeResource;
    }

    public EventResource getEventResource() {
        return eventResource;
    }

    public FightResource getFightResource() {
        return fightResource;
    }

    public RootResource getRootResource() {
        return rootResource;
    }

    public WebsocketResource getWebsocketResource() {
        return websocketResource;
    }
}
