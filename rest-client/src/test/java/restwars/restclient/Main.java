package restwars.restclient;

import restwars.restapi.dto.planet.PlanetResponse;
import restwars.restapi.dto.planet.PlanetsResponse;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RestWarsClient client = new RestWarsClient("http://localhost:8080");
        System.out.println("Ping: " + client.getSystemResource().ping());
        System.out.println("Configuration: " + client.getMetadataResource().getConfiguration());

        client.setCredentials("foo", "foo");

        while (true) {
            System.out.println();
            System.out.print("Current round: ");
            System.out.println(client.getRootResource().generalInformation().getRound());
            System.out.print("Current round started: ");
            System.out.println(client.getRootResource().generalInformation().getRoundStarted());

            System.out.println();
            System.out.println("Events");
            System.out.println(client.getEventResource().getEvents(1));

            System.out.println();
            System.out.println("Fights");
            System.out.println(client.getFightResource().ownFights(1));

            PlanetsResponse planets = client.getPlanetResource().allPlanets();
            for (PlanetResponse planet : planets.getData()) {
                System.out.println();
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
                System.out.println(client.getFlightResource().getOwnFlightsForPlanet(planet.getLocation()));

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