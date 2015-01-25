package restwars.service.technology;

import org.testng.annotations.Test;
import restwars.model.technology.TechnologyType;

import java.util.stream.Stream;

import static org.testng.Assert.fail;

public class TechnologyTypeTest {
    @Test
    public void testUniqueIds() throws Exception {
        for (TechnologyType technologyType : TechnologyType.values()) {
            if (Stream.of(TechnologyType.values()).filter(t -> t.getId() == technologyType.getId()).count() > 1) {
                fail("TechnologyType id " + technologyType.getId() + " isn't unique");
            }
        }
    }
}