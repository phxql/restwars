package restwars.service.player;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.UUID;

public class Player {
    private final UUID id;

    private final String username;

    private final String password;

    public Player(UUID id, String username, String password) {
        this.id = Preconditions.checkNotNull(id, "id");
        this.username = Preconditions.checkNotNull(username, "username");
        this.password = Preconditions.checkNotNull(password, "password");
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player that = (Player) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.username, that.username) &&
                Objects.equal(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, password);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .toString();
    }

    /**
     * Determines if this player is the other player.
     *
     * @param other Other player.
     * @return True if this player is the other player.
     */
    public boolean is(Player other) {
        return id.equals(other.getId());
    }
}
