package restwars.storage.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.unitofwork.UnitOfWork;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class InMemoryPlayerDAO implements PlayerDAO {
    private final Map<UUID, Player> players = Maps.newHashMap();

    @Override
    public void insert(UnitOfWork uow, Player player) {
        Preconditions.checkNotNull(uow, "uow");
        Preconditions.checkNotNull(player, "player");

        players.put(player.getId(), player);
    }

    @Override
    public Optional<Player> findWithUsername(UnitOfWork uow, String username) {
        Preconditions.checkNotNull(uow, "uow");
        Preconditions.checkNotNull(username, "username");

        return players.values().stream().filter(p -> p.getUsername().equals(username)).findFirst();
    }
}
