package restwars.storage.scenario;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Executes multiple scenarios.
 */
public class MultipleScenarios implements Scenario {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleScenarios.class);
    /**
     * Scenarios.
     */
    private final List<Scenario> scenarios;

    /**
     * Constructor.
     *
     * @param scenarios Scenarios.
     */
    public MultipleScenarios(Scenario... scenarios) {
        Preconditions.checkNotNull(scenarios, "scenarios");
        this.scenarios = ImmutableList.copyOf(scenarios);
    }

    @Override
    public void create(Handle handle) throws ScenarioException {
        for (Scenario scenario : scenarios) {
            LOGGER.debug("Executing scenario {}", scenario);

            scenario.create(handle);
        }
    }
}
