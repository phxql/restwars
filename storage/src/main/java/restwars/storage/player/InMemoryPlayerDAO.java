package restwars.storage.player;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;

import java.util.Map;
import java.util.UUID;

public class InMemoryPlayerDAO implements PlayerDAO {
    private final Map<UUID, Player> players = Maps.newHashMap();

    @Override
    public void insert(Player player) {
        Preconditions.checkNotNull(player, "player");

        players.put(player.getId(), player);
    }

    @Override
    public Optional<Player> findWithUsername(String username) {
        java.util.Optional<Player> player = players.values().stream().filter(p -> p.getUsername().equals(username)).findFirst();

        if (player.isPresent()) {
            return Optional.of(player.get());
        } else {
            return Optional.absent();
        }
    }
}
