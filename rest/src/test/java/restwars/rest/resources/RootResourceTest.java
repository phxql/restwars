package restwars.rest.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import restwars.restapi.dto.GeneralInformationResponse;
import restwars.service.infrastructure.RoundService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class RootResourceTest extends AbstractResourceTest {
    public static final String URL = "/v1";
    private static RoundService roundService = mock(RoundService.class);

    @ClassRule
    public static final ResourceTestRule resources = createRule()
            .addResource(new RootResource(roundService))
            .build();

    @Before
    public void setUp() throws Exception {
        reset(roundService);
    }

    @Test
    public void testName() throws Exception {
        DateTime now = DateTime.now();
        when(roundService.getCurrentRound()).thenReturn(1L);
        when(roundService.getCurrentRoundStarted()).thenReturn(now);

        GeneralInformationResponse response = request(resources, URL).get(GeneralInformationResponse.class);

        assertThat(response.getRound(), is(1L));
        assertThat(response.getRoundStarted(), is(now.withZone(DateTimeZone.UTC)));
    }
}