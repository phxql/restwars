package restwars.restclient;

import restwars.restapi.dto.planet.PlanetResponse;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RestWarsClient client = new RestWarsClient("http://localhost:8080");
        client.setCredentials("foo", "foo");

        List<PlanetResponse> planets = client.getPlanetResource().allPlanets();
        for (PlanetResponse planet : planets) {
            System.out.println("Planet " + planet);

            System.out.println(client.getBuildingResource().getBuildings(planet.getLocation()));
        }
    }
}