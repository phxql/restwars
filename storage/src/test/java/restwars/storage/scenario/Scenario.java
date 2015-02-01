package restwars.storage.scenario;

import org.skife.jdbi.v2.Handle;

/**
 * A scenario.
 */
public interface Scenario {
    /**
     * Creates the scenario in the database.
     *
     * @param handle Handle to the database.
     * @throws ScenarioException If something went wrong while creating the scenario.
     */
    void create(Handle handle) throws ScenarioException;
}
