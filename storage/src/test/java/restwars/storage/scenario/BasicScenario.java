package restwars.storage.scenario;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.building.Building;
import restwars.model.building.BuildingType;
import restwars.model.planet.Location;
import restwars.model.planet.Planet;
import restwars.model.player.Player;
import restwars.model.resource.Resources;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BasicScenario implements Scenario {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicScenario.class);

    public static class Player1 {

        public static final Player PLAYER = new Player(UUID.fromString("60f19086-8daa-4c02-ac41-35f7e2727d99"), "player1", "player1");
        public static class Planet1 {

            public static final Planet PLANET = new Planet(UUID.fromString("ab8657a1-f418-420a-a44a-ab5adeb7f4f1"), new Location(1, 2, 3), PLAYER.getId(), new Resources(100, 200, 300));
            public static final Building COMMAND_CENTER = new Building(UUID.fromString("701a4465-eefb-4db7-8054-5a54c6ee182e"), BuildingType.COMMAND_CENTER, 1, PLANET.getId());

            public static final Building SHIPYARD = new Building(UUID.fromString("f7a42fbe-0abc-43d3-b78e-96ca52960d54"), BuildingType.SHIPYARD, 2, PLANET.getId());
        }
    }

    public static class Model {

        private final List<Planet> planets;
        private final List<Player> players;
        private final List<Building> buildings;
        public Model(List<Planet> planets, List<Player> players, List<Building> buildings) {
            this.planets = planets;
            this.players = players;
            this.buildings = buildings;
        }

        public List<Planet> getPlanets() {
            return planets;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public List<Building> getBuildings() {
            return buildings;
        }

    }

    private static final BasicScenario INSTANCE = new BasicScenario();

    private final Configuration configuration;

    private BasicScenario() {
        configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setClassForTemplateLoading(BasicScenario.class, "/");
        configuration.setDefaultEncoding("UTF-8");
    }

    public static Scenario getInstance() {
        return INSTANCE;
    }

    @Override
    public void create(Connection connection) throws ScenarioException {
        List<Player> players = Arrays.asList(Player1.PLAYER);
        List<Planet> planets = Arrays.asList(Player1.Planet1.PLANET);
        List<Building> buildings = Arrays.asList(Player1.Planet1.COMMAND_CENTER, Player1.Planet1.SHIPYARD);

        Model model = new Model(planets, players, buildings);

        Template template;
        try {
            template = configuration.getTemplate("scenario/basic.sql.ftl");
        } catch (IOException e) {
            throw new ScenarioException("Exception while loading Freemarker template", e);
        }

        StringWriter writer = new StringWriter();
        try {
            template.process(model, writer);
        } catch (TemplateException | IOException e) {
            throw new ScenarioException("Exception while rendering Freemarker template", e);
        }

        String sql = writer.toString();
        LOGGER.trace("Generated SQL: {}", sql);

        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        } catch (SQLException e) {
            throw new ScenarioException("Exception while executing SQL", e);
        }
    }
}
