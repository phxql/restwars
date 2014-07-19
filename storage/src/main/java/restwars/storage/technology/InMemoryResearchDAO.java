package restwars.storage.technology;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.technology.Research;
import restwars.service.technology.ResearchDAO;

import java.util.Map;
import java.util.UUID;

public class InMemoryResearchDAO implements ResearchDAO {
    private final Map<UUID, Research> researches = Maps.newHashMap();

    @Override
    public void insert(Research research) {
        Preconditions.checkNotNull(research, "research");

        researches.put(research.getId(), research);
    }
}
