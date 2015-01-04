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

            System.out.println();
            System.out.println("Buildings");
            System.out.println(client.getBuildingResource().getBuildings(planet.getLocation()));

            System.out.println();
            System.out.println("Construction sites");
            System.out.println(client.getConstructionSiteResource().getConstructionSites(planet.getLocation()));

            System.out.println();
            System.out.println("Flights");
            System.out.println(client.getFlightResource().getOwnFlights(planet.getLocation()));
        }
    }
}