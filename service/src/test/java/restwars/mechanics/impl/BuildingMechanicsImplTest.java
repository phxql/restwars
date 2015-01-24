package restwars.mechanics.impl;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BuildingMechanicsImplTest {

    private final BuildingMechanicsImpl sut = new BuildingMechanicsImpl();

    @Test
    public void testCalculateFleetSizeVariance() throws Exception {
        assertThat(sut.calculateFleetSizeVariance(1), is(1.0));
        assertThat(sut.calculateFleetSizeVariance(2), is(0.95));
        assertThat(sut.calculateFleetSizeVariance(3), is(0.9));
    }
}