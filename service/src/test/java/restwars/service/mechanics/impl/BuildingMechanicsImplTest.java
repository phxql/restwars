package restwars.service.mechanics.impl;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BuildingMechanicsImplTest {

    private final BuildingMechanicsImpl sut = new BuildingMechanicsImpl(new ResourcesMechanicsImpl());

    @Test
    public void testCalculateFleetSizeVariance() throws Exception {
        assertThat(sut.calculateFleetSizeVariance(1), is(1.0));
        assertThat(sut.calculateFleetSizeVariance(2), is(0.95));
        assertThat(sut.calculateFleetSizeVariance(3), is(0.9));
    }

    @Test
    public void testCalculateBuildingBuildTimeSpeedup() throws Exception {
        assertThat(sut.calculateBuildingBuildTimeSpeedup(1), is(0.0));
        assertThat(sut.calculateBuildingBuildTimeSpeedup(2), is(0.1));
        assertThat(sut.calculateBuildingBuildTimeSpeedup(3), is(0.2));
    }

    @Test
    public void testCalculateShipBuildTimeSpeedup() throws Exception {
        assertThat(sut.calculateShipBuildTimeSpeedup(1), is(0.0));
        assertThat(sut.calculateShipBuildTimeSpeedup(2), is(0.1));
        assertThat(sut.calculateShipBuildTimeSpeedup(3), is(0.2));
    }
}