package restwars.rest.resources;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import restwars.Data;
import restwars.rest.DummyAuthenticator;
import restwars.restapi.dto.planet.PlanetResponse;
import restwars.restapi.dto.player.PlayerResponse;
import restwars.restapi.dto.player.RegisterPlayerRequest;
import restwars.service.planet.PlanetService;
import restwars.service.player.PlayerService;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PlayerResourceTest {
    public static final String URL = "/v1/player";
    private static PlayerService playerService = mock(PlayerService.class);
    private static PlanetService planetService = mock(PlanetService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addProvider(new BasicAuthProvider<>(new DummyAuthenticator("username", "password", Data.Player1.PLAYER), "test"))
            .addResource(new PlayerResource(playerService, planetService))
            .build();

    @BeforeClass
    public static void setupClass() {
        resources.client().addFilter(new HTTPBasicAuthFilter("username", "password"));
    }

    @Before
    public void setUp() throws Exception {
        reset(playerService);
    }

    @Test
    public void testRegister() throws Exception {
        request(URL).post(new RegisterPlayerRequest("username", "password"));

        verify(playerService).createPlayer("username", "password");
    }

    @Test(expected = ConstraintViolationException.class)
    public void testRegister2() throws Exception {
        request(URL).post(new RegisterPlayerRequest("", "password"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testRegister3() throws Exception {
        request(URL).post(new RegisterPlayerRequest("username", ""));
    }

    @Test
    public void testMe() throws Exception {
        when(planetService.findWithOwner(Data.Player1.PLAYER)).thenReturn(Arrays.asList(Data.Player1.Planet1.PLANET));

        PlayerResponse response = request(URL).get(PlayerResponse.class);

        assertThat(response.getUsername(), is("username"));
        List<PlanetResponse> planets = response.getPlanets();
        assertThat(planets, hasSize(1));
        assertThat(planets.get(0), is(new PlanetResponse("1.1.1", 100, 200, 300)));
    }

    private WebResource.Builder request(String url) {
        return resources.client().resource(url).type(MediaType.APPLICATION_JSON_TYPE);
    }
}