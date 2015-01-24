package restwars.mechanics.impl;

import org.testng.annotations.Test;
import restwars.service.resource.Resources;
import restwars.service.technology.TechnologyType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TechnologyMechanicsImplTest {
    private final TechnologyMechanicsImpl sut = new TechnologyMechanicsImpl();

    @Test
    public void testAllTechnologiesAreSupported() throws Exception {
        for (TechnologyType technologyType : TechnologyType.values()) {
            Resources cost = sut.calculateResearchCost(technologyType, 1);
            int time = sut.calculateResearchTime(technologyType, 1);

            assertThat(cost, is(notNullValue()));
            assertThat(time, is(greaterThan(0)));
        }
    }
}