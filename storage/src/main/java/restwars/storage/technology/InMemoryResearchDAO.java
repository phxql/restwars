package restwars.storage.technology;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.technology.Research;
import restwars.service.technology.ResearchDAO;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryResearchDAO implements ResearchDAO {
    private final Map<UUID, Research> researches = Maps.newHashMap();

    @Override
    public void insert(Research research) {
        Preconditions.checkNotNull(research, "research");

        researches.put(research.getId(), research);
    }

    @Override
    public List<Research> findWithDone(long round) {
        return researches.values().stream().filter(r -> r.getDone() == round).collect(Collectors.toList());
    }

    @Override
    public void delete(Research research) {
        Preconditions.checkNotNull(research, "research");

        researches.remove(research.getId());
    }
}
