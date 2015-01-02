package restwars.restclient;

import restwars.restapi.dto.player.PlayerResponse;

public class Main {
    public static void main(String[] args) {
        RestWarsClient client = new RestWarsClient("http://localhost:8080");
        client.setCredentials("foo", "foo");
        PlayerResponse response = client.getPlayerResource().me();

        System.out.println(response);
    }
}