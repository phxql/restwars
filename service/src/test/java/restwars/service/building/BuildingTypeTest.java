package restwars.service.building;

import org.testng.annotations.Test;

import java.util.stream.Stream;

import static org.testng.Assert.fail;

public class BuildingTypeTest {
    @Test
    public void testUniqueIds() throws Exception {
        for (BuildingType buildingType : BuildingType.values()) {
            if (Stream.of(BuildingType.values()).filter(t -> t.getId() == buildingType.getId()).count() > 1) {
                fail("Id " + buildingType.getId() + " isn't unique");
            }
        }
    }
}