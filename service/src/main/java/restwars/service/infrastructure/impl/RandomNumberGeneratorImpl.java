package restwars.service.infrastructure.impl;

import restwars.service.infrastructure.RandomNumberGenerator;

import javax.inject.Inject;
import java.util.Random;

public class RandomNumberGeneratorImpl implements RandomNumberGenerator {
    private final Random random = new Random();

    @Inject
    public RandomNumberGeneratorImpl() {
    }

    @Override
    public int nextInt(int max) {
        return random.nextInt(max);
    }

    @Override
    public int nextInt(int min, int max) {
        return nextInt((max - min) + 1) + min;
    }

    @Override
    public long nextLong(long max) {
        long bits, val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % max;
        } while (bits - val + (max - 1) < 0L);
        return val;
    }

    @Override
    public long nextLong(long min, long max) {
        return nextLong((max - min) + 1) + min;
    }
}
