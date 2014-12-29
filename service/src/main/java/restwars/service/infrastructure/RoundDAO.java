package restwars.service.infrastructure;

/**
 * DAO for rounds.
 */
public interface RoundDAO {
    /**
     * Initial round.
     */
    public static final int INITIAL_ROUND = 1;

    /**
     * Returns the current round.
     *
     * @return Round.
     */
    long getRound();

    /**
     * Updates the round to the given value.
     *
     * @param round New value.
     */
    void updateRound(long round);
}
