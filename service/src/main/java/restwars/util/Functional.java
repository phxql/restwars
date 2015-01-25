package restwars.util;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Helper class for functional programming.
 */
public final class Functional {
    private Functional() {
    }

    /**
     * Maps the given collection to a list by applying a mapper function.
     *
     * @param input  Input collection.
     * @param mapper Mapper function.
     * @param <F>    Input type.
     * @param <T>    Output type.
     * @return A list of output types.
     */
    public static <F, T> List<T> mapToList(Collection<F> input, Function<F, T> mapper) {
        Preconditions.checkNotNull(input, "input");
        Preconditions.checkNotNull(mapper, "mapper");

        return input.stream().map(mapper).collect(Collectors.toList());
    }
}
