package restwars.service.player;

import java.util.UUID;

public class Player {
    private final UUID id;

    private final String username;

    private final String password;

    public Player(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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
