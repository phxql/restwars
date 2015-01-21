package restwars.util;

/**
 * Extended Math functions.
 */
public final class MathExt {
    /**
     * No instances allowed.
     */
    private MathExt() {
    }

    /**
     * A {@link Math#ceil(double)} with a long result.
     *
     * @param value Value to ceil.
     * @return Result.
     */
    public static long ceilLong(double value) {
        return (long) Math.ceil(value);
    }

    /**
     * A {@link Math#floor(double)} with a long result.
     *
     * @param value Value to floor.
     * @return Result.
     */
    public static long floorLong(double value) {
        return (long) Math.floor(value);
    }

    /**
     * A {@link Math#ceil(double)} with a int result.
     *
     * @param value Value to ceil.
     * @return Result.
     */
    public static int ceilInt(double value) {
        return (int) Math.ceil(value);
    }

    /**
     * A {@link Math#floor(double)} with a int result.
     *
     * @param value Value to floor.
     * @return Result.
     */
    public static int floorInt(double value) {
        return (int) Math.floor(value);
    }
}
