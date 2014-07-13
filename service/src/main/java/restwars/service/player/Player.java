package restwars.service.player;

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
}
