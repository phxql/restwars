package restwars.service.ship;

import org.testng.annotations.Test;

import java.util.stream.Stream;

import static org.testng.Assert.fail;

public class ShipTypeTest {
    @Test
    public void testUniqueIds() throws Exception {
        for (ShipType shipType : ShipType.values()) {
            if (Stream.of(ShipType.values()).filter(t -> t.getId() == shipType.getId()).count() > 1) {
                fail("ShipType id " + shipType.getId() + " isn't unique");
            }
        }
    }
}