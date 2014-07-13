package restwars.storage.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;

import java.util.Map;
import java.util.UUID;

public class MemoryPlayerDAO implements PlayerDAO {
    private final Map<UUID, Player> players = Maps.newHashMap();

    @Override
    public void insert(Player player) {
        Preconditions.checkNotNull(player, "player");

        players.put(player.getId(), player);
    }
}
