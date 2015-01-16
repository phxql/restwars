package restwars.service.planet;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LocationTest {

    @Test
    public void testIsValid() throws Exception {
        assertThat(new Location(2, 2, 2).isValid(2, 2, 2), is(true));
        assertThat(new Location(1, 1, 1).isValid(2, 2, 2), is(true));
        assertThat(new Location(3, 3, 3).isValid(2, 2, 2), is(false));
        assertThat(new Location(3, 2, 2).isValid(2, 2, 2), is(false));
        assertThat(new Location(2, 3, 2).isValid(2, 2, 2), is(false));
        assertThat(new Location(2, 2, 3).isValid(2, 2, 2), is(false));
    }
}