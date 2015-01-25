package restwars.service.event;

import org.testng.annotations.Test;
import restwars.model.event.EventType;

import java.util.stream.Stream;

import static org.testng.Assert.fail;

public class EventTypeTest {
    @Test
    public void testUniqueIds() throws Exception {
        for (EventType eventType : EventType.values()) {
            if (Stream.of(EventType.values()).filter(t -> t.getId() == eventType.getId()).count() > 1) {
                fail("EventType id " + eventType.getId() + " isn't unique");
            }
        }
    }
}