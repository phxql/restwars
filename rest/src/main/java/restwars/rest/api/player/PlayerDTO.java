package restwars.rest.api.player;

public class PlayerDTO {
    private final String username;

    public PlayerDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
