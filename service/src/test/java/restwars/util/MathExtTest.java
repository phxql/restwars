package restwars.util;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MathExtTest {

    @Test
    public void testCeilLong() throws Exception {
        assertThat(MathExt.ceilLong(0.0), is(0L));
        assertThat(MathExt.ceilLong(1.0), is(1L));
        assertThat(MathExt.ceilLong(1.5), is(2L));
        assertThat(MathExt.ceilLong(1.9), is(2L));
        assertThat(MathExt.ceilLong(2.0), is(2L));
        assertThat(MathExt.ceilLong(2.5), is(3L));
        assertThat(MathExt.ceilLong(2.9), is(3L));
    }

    @Test
    public void testFloorLong() throws Exception {
        assertThat(MathExt.floorLong(0.0), is(0L));
        assertThat(MathExt.floorLong(1.0), is(1L));
        assertThat(MathExt.floorLong(1.5), is(1L));
        assertThat(MathExt.floorLong(1.9), is(1L));
        assertThat(MathExt.floorLong(2.0), is(2L));
        assertThat(MathExt.floorLong(2.5), is(2L));
        assertThat(MathExt.floorLong(2.9), is(2L));
    }
}