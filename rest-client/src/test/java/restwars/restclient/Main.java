package restwars.restclient;

import restwars.restapi.dto.planet.PlanetResponse;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        RestWarsClient client = new RestWarsClient("http://localhost:8080");
        System.out.println("Ping: " + client.getSystemResource().ping());
        System.out.println("Configuration: " + client.getMetadataResource().getConfiguration());

        client.setCredentials("foo", "foo");

        while (true) {
            List<PlanetResponse> planets = client.getPlanetResource().allPlanets();
            for (PlanetResponse planet : planets) {
                System.out.println("Planet " + planet);

                // client.getConstructionSiteResource().build(planet.getLocation(), new CreateBuildingRequest("TELESCOPE"));

                System.out.println();
                System.out.println("Buildings");
                System.out.println(client.getBuildingResource().getBuildings(planet.getLocation()));

                System.out.println();
                System.out.println("Construction sites");
                System.out.println(client.getConstructionSiteResource().getConstructionSites(planet.getLocation()));

                System.out.println();
                System.out.println("Flights");
                System.out.println(client.getFlightResource().getOwnFlights(planet.getLocation()));

                System.out.println();
                System.out.println("Research");
                System.out.println(client.getResearchResource().getResearch(planet.getLocation()));

                System.out.println();
                System.out.println("Ships");
                System.out.println(client.getShipResource().getShips(planet.getLocation()));

                System.out.println();
                System.out.println("Planets in vicinity");
                System.out.println(client.getTelescopeResource().scan(planet.getLocation()));
            }

            System.in.read();
        }
    }
}