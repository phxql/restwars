package restwars.restclient;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import restwars.restapi.MetadataResource;
import restwars.restapi.PlayerResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class RestWarsClient {
    private final Client client;
    private final String url;
    private PlayerResource playerResource;
    private MetadataResource metadataResource;

    public RestWarsClient(String url) {
        client = ClientBuilder.newClient();
        this.url = url;

        createProxies(url);
    }

    public void setCredentials(String username, String password) {
        HttpAuthenticationFeature authentication = HttpAuthenticationFeature.basicBuilder()
                .nonPreemptive().credentials(username, password).build();
        client.register(authentication);

        // We need to recreate the proxies to apply the changes
        createProxies(url);
    }

    private void createProxies(String url) {
        WebTarget target = client.target(url);
        playerResource = WebResourceFactory.newResource(PlayerResource.class, target);
        metadataResource = WebResourceFactory.newResource(MetadataResource.class, target);
    }

    public PlayerResource getPlayerResource() {
        return playerResource;
    }

    public MetadataResource getMetadataResource() {
        return metadataResource;
    }
}
