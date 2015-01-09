package restwars.util;

public final class MathExt {
    private MathExt() {
    }

    public static long ceilLong(double value) {
        return (long) Math.ceil(value);
    }

    public static long floorLong(double value) {
        return (long) Math.floor(value);
    }
}
