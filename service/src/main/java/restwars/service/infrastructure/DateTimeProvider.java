package restwars.service.infrastructure;

import org.joda.time.DateTime;

/**
 * Provides the current datetime.
 */
public interface DateTimeProvider {
    /**
     * Returns the current datetime.
     *
     * @return Current datetime.
     */
    DateTime now();
}
