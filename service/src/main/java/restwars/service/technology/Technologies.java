package restwars.service.technology;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

public class Technologies extends ForwardingList<Technology> {
    public static final Technologies NONE = new Technologies(Collections.emptyList());

    private final ImmutableList<Technology> technologies;

    public Technologies(Iterable<Technology> technologies) {
        Preconditions.checkNotNull(technologies);
        this.technologies = ImmutableList.copyOf(technologies);
    }

    public int getLevel(TechnologyType type) {
        return technologies.stream().filter(t -> t.getType().equals(type)).findAny().map(Technology::getLevel).orElse(0);
    }

    @Override
    protected List<Technology> delegate() {
        return technologies;
    }
}
