package restwars.service.infrastructure.impl;

import org.joda.time.DateTime;
import restwars.service.infrastructure.DateTimeProvider;

public class CurrentDateTimeProvider implements DateTimeProvider {
    @Override
    public DateTime now() {
        return DateTime.now();
    }
}
