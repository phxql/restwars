package restwars.storage.technology;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.technology.Research;
import restwars.service.technology.ResearchDAO;
import restwars.service.technology.TechnologyType;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
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
    public List<Research> findWithPlanetId(UUID planetId) {
        Preconditions.checkNotNull(planetId, "planetId");

        return researches.values().stream().filter(r -> r.getPlanetId().equals(planetId)).collect(Collectors.toList());
    }

    @Override
    public List<Research> findWithPlayerAndType(UUID playerId, TechnologyType type) {
        return researches.values().stream().filter(r -> r.getPlayerId().equals(playerId) && r.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public void delete(Research research) {
        Preconditions.checkNotNull(research, "research");

        researches.remove(research.getId());
    }
}
