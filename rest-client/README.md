# RESTwars Client

This is a RESTwars client written in Java. It can be used to implement a RESTwars bot which runs on the JVM.

## Usage

    RestWarsClient client = new RestWarsClient("http://localhost:8080");
    client.setCredentials("foo", "foo");

    // Get all planets
    List<PlanetResponse> planets = client.getPlanetResource().allPlanets();
    for (PlanetResponse planet : planets) {
        // Get buildings on that planet
        List<BuildingResponse> buildings = client.getBuildingResource().getBuildings(planet.getLocation());

        // Upgrade the command center
        client.getConstructionSiteResource().build(planet.getLocation(), new CreateBuildingRequest("COMMAND_CENTER"));
    }