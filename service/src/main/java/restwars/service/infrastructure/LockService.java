package restwars.service.infrastructure;

/**
 * Manages the global lock.
 */
public interface LockService {
    /**
     * Is called before a request from a client is handled.
     */
    void beforeRequest();

    /**
     * Is called after a request from a client is handled.
     */
    void afterRequest();

    /**
     * Is called before the clock advances the round.
     */
    void beforeClock();

    /**
     * Is called after the clock advanced the round.
     */
    void afterClock();
}
