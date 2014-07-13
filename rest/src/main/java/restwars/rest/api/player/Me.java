package restwars.rest.api.player;

public class Me {
    private final String username;

    public Me(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
