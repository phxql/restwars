package restwars.rest.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import restwars.restapi.dto.player.RegisterPlayerRequest;
import restwars.service.planet.PlanetService;
import restwars.service.player.PlayerService;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;

import static org.mockito.Mockito.*;

public class PlayerResourceTest {
    private static PlayerService playerService = mock(PlayerService.class);
    private static PlanetService planetService = mock(PlanetService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PlayerResource(playerService, planetService))
            .build();

    @Before
    public void setUp() throws Exception {
        reset(playerService);
    }

    @Test
    public void testRegister() throws Exception {
        resources.client().resource("/v1/player").type(MediaType.APPLICATION_JSON_TYPE).post(new RegisterPlayerRequest("username", "password"));

        verify(playerService).createPlayer("username", "password");
    }

    @Test(expected = ConstraintViolationException.class)
    public void testRegister2() throws Exception {
        resources.client().resource("/v1/player").type(MediaType.APPLICATION_JSON_TYPE).post(new RegisterPlayerRequest("", "password"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testRegister3() throws Exception {
        resources.client().resource("/v1/player").type(MediaType.APPLICATION_JSON_TYPE).post(new RegisterPlayerRequest("username", ""));
    }
}