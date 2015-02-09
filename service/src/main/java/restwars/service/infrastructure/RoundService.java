package restwars.service.infrastructure;

import org.joda.time.DateTime;

/**
 * Service to manage rounds.
 */
public interface RoundService {
    /**
     * Initializes the round service.
     */
    void initialize();

    /**
     * Returns the current round.
     *
     * @return Current round.
     */
    long getCurrentRound();

    /**
     * Starts the next round.
     *
     * @return Next round.
     */
    long nextRound();

    /**
     * Returns the datetime when the current round has been started.
     *
     * @return Datetime when the current round has been started.
     */
    DateTime getCurrentRoundStarted();
}
