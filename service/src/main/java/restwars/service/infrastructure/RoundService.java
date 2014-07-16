package restwars.service.infrastructure;

/**
 * Service to manage rounds.
 */
public interface RoundService {
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
}
