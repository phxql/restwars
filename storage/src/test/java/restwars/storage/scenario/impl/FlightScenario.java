package restwars.storage.scenario.impl;

import restwars.model.flight.Flight;
import restwars.model.flight.FlightDirection;
import restwars.model.flight.FlightType;
import restwars.model.resource.Resources;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.storage.scenario.AbstractFreemarkerScenario;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FlightScenario extends AbstractFreemarkerScenario<FlightScenario.Model> {
    public static class Player1 {
        public static final Flight FLIGHT = new Flight(
                UUID.fromString("63728ee0-a999-11e4-bcd8-0800200c9a66"), BasicScenario.Player1.Planet1.PLANET.getLocation(), BasicScenario.Player2.Planet1.PLANET.getLocation(),
                1, 5, new Ships(new Ship(ShipType.MOSQUITO, 5), new Ship(ShipType.MULE, 1)), 100, FlightType.ATTACK, BasicScenario.Player1.PLAYER.getId(),
                FlightDirection.OUTWARD, Resources.NONE, 1.5, false
        );
    }

    public static class Model {
        private final List<Flight> flights;

        public Model(List<Flight> flights) {
            this.flights = flights;
        }

        public List<Flight> getFlights() {
            return flights;
        }
    }

    private static final FlightScenario INSTANCE = new FlightScenario();

    public static FlightScenario create() {
        return INSTANCE;
    }

    @Override
    protected Model getModel() {
        List<Flight> flights = Arrays.asList(
                Player1.FLIGHT
        );

        return new Model(flights);
    }

    @Override
    protected String getTemplateName() {
        return "scenario/flight.sql.ftl";
    }
}
