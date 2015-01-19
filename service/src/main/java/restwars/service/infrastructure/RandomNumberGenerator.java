package restwars.service.infrastructure;

/**
 * Generates random numbers.
 */
public interface RandomNumberGenerator {
    /**
     * Generates a random int number which is between min and max.
     *
     * @param min Minimum number (inclusive).
     * @param max Maximum number (inclusive).
     * @return A random int number which is between min and max.
     */
    int nextInt(int min, int max);

    /**
     * Generates a random int number which is smaller than max.
     *
     * @param max Maximum number (exclusive).
     * @return A random int number which is smaller than max.
     */
    int nextInt(int max);

    /**
     * Generates a random long number which is smaller than max.
     *
     * @param max Maximum number (exclusive).
     * @return A random long number which is smaller than max.
     */
    long nextLong(long max);

    /**
     * Generates a random long number which is between min and max.
     *
     * @param min Minimum number (inclusive).
     * @param max Maximum number (inclusive).
     * @return A random long number which is between min and max.
     */
    long nextLong(long min, long max);
}
