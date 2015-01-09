package restwars.service.ship.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.event.Event;
import restwars.service.event.EventDAO;
import restwars.service.event.EventType;
import restwars.service.infrastructure.RoundService;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.planet.Planet;
import restwars.service.planet.PlanetDAO;
import restwars.service.resource.Resources;
import restwars.service.ship.*;

import java.util.Optional;
import java.util.UUID;

public class AttackFlightHandler extends AbstractFlightHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttackFlightHandler.class);

    private final FightCalculator fightCalculator;
    private final FightDAO fightDAO;

    public AttackFlightHandler(RoundService roundService, FlightDAO flightDAO, PlanetDAO planetDAO, HangarDAO hangarDAO, UUIDFactory uuidFactory, FightDAO fightDAO, EventDAO eventDAO) {
        super(roundService, flightDAO, planetDAO, hangarDAO, uuidFactory, eventDAO);

        this.fightDAO = Preconditions.checkNotNull(fightDAO, "fightDAO");
        this.fightCalculator = new FightCalculator(uuidFactory);
    }

    @Override
    public void handle(Flight flight, long round) {
        assert flight != null;
        LOGGER.debug("Handling attack of flight {}", flight);

        Optional<Planet> planet = getPlanetDAO().findWithLocation(flight.getDestination());
        if (planet.isPresent()) {
            Planet defenderPlanet = planet.get();

            if (defenderPlanet.getOwnerId().equals(flight.getPlayerId())) {
                // Planet is friendly, create return flight
                createReturnFlight(flight, flight.getShips(), flight.getCargo());
            } else {
                Hangar hangar = getOrCreateHangar(defenderPlanet.getId(), defenderPlanet.getOwnerId());
                UUID attackerId = flight.getPlayerId();
                Fight fight = fightCalculator.attack(attackerId, defenderPlanet.getOwnerId(), defenderPlanet.getId(), flight.getShips(), hangar.getShips(), round);

                // Update defenders hangar
                getHangarDAO().update(hangar.withShips(fight.getRemainingDefenderShips()));

                if (fight.getRemainingAttackerShips().isEmpty()) {
                    LOGGER.debug("Attacker lost all ships");
                    getFlightDAO().delete(flight);
                } else {
                    Resources cargo = Resources.NONE;
                    if (fight.getRemainingDefenderShips().isEmpty()) {
                        cargo = lootPlanet(defenderPlanet, fight.getRemainingAttackerShips());
                    }

                    createReturnFlight(flight, fight.getRemainingAttackerShips(), cargo);
                }

                // Store fight
                fightDAO.insert(fight);

                // Create event
                getEventDAO().insert(new Event(getUuidFactory().create(), attackerId, planet.get().getId(), EventType.FIGHT_HAPPENED, round));
            }
        } else {
            // Planet is not colonized, create return flight
            createReturnFlight(flight, flight.getShips(), flight.getCargo());
        }
    }

    private Resources lootPlanet(Planet planet, Ships ships) {
        long storageCapacity = getShipUtils().calculateStorageCapacity(ships);

        long lootCrystals = storageCapacity / 3;
        long lootGas = storageCapacity / 3;
        long lootEnergy = storageCapacity - lootCrystals - lootGas;

        // TODO - Gameplay: Energy can't be stolen
        // TODO - Gameplay: Implement a more greedy looting strategy
        lootCrystals = Math.min(planet.getResources().getCrystals(), lootCrystals);
        lootGas = Math.min(planet.getResources().getGas(), lootGas);
        lootEnergy = Math.min(planet.getResources().getEnergy(), lootEnergy);

        // Decrease resources on planet
        planet = planet.withResources(planet.getResources().minus(new Resources(lootCrystals, lootGas, lootEnergy)));
        getPlanetDAO().update(planet);

        Resources resources = new Resources(lootCrystals, lootGas, lootEnergy);
        LOGGER.debug("Looted {} from planet {}", resources, planet.getLocation());
        return resources;
    }
}
