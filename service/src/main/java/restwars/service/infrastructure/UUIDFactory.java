package restwars.service.infrastructure;

import java.util.UUID;

/**
 * Creates UUIDs.
 */
public interface UUIDFactory {
    /**
     * Creates a new UUID.
     *
     * @return Created UUID.
     */
    UUID create();
}
