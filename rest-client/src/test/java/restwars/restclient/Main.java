package restwars.restclient;

public class Main {
    public static void main(String[] args) {
        RestWarsClient client = new RestWarsClient("http://localhost:8080");
        client.setCredentials("foo", "foo");
    }
}