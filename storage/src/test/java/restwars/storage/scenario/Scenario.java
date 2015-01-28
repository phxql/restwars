package restwars.storage.scenario;

import java.sql.Connection;

public interface Scenario {
    void create(Connection connection) throws ScenarioException;
}
