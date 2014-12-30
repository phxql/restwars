package restwars.service.ship;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.UUID;

public class Hangar {
    private final UUID id;

    private final UUID planetId;

    private final UUID playerId;

    private final Ships ships;

    public Hangar(UUID id, UUID planetId, UUID playerId, Ships ships) {
        this.id = Preconditions.checkNotNull(id, "id");
        this.planetId = Preconditions.checkNotNull(planetId, "planetId");
        this.playerId = Preconditions.checkNotNull(playerId, "playerId");
        this.ships = Preconditions.checkNotNull(ships, "ships");
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlanetId() {
        return planetId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Ships getShips() {
        return ships;
    }

    public Hangar withShips(Ships ships) {
        Preconditions.checkNotNull(ships, "ships");

        return new Hangar(id, planetId, playerId, ships);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("planetId", planetId)
                .add("playerId", playerId)
                .add("ships", ships)
                .toString();
    }
}
