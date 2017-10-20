package restwars.service.ship.impl;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import restwars.model.ship.Hangar;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.mechanics.ShipMechanics;
import restwars.service.mechanics.impl.ResourcesMechanicsImpl;
import restwars.service.mechanics.impl.ShipMechanicsImpl;
import restwars.service.ship.HangarDAO;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class ShipUtilsTest {
    private ShipMechanics shipMechanics = new ShipMechanicsImpl(new ResourcesMechanicsImpl());
    private ShipUtils utils = new ShipUtils();

    @Test
    public void testCalculateStorageCapacity() throws Exception {
        Ship daggers = new Ship(ShipType.DAGGER, 5);
        Ship mules = new Ship(ShipType.MULE, 2);
        Ships ships = new Ships(Arrays.asList(daggers, mules));

        long actualCapacity = utils.calculateStorageCapacity(ships, shipMechanics);

        assertEquals(actualCapacity, 5*shipMechanics.getCargoSpace(ShipType.DAGGER) + 2*shipMechanics.getCargoSpace(ShipType.MULE));
    }

    @Test
    public void testFindSpeedOfSlowestShip() throws Exception {
        Ship daggers = new Ship(ShipType.DAGGER, 5);
        Ship mules = new Ship(ShipType.MULE, 2);
        Ships ships = new Ships(Arrays.asList(daggers, mules));

        double actualSpeed = utils.findSpeedOfSlowestShip(ships, shipMechanics);

        //Make "future proof"
        double expectedSpeed = shipMechanics.getFlightSpeed(ShipType.DAGGER);
        if(shipMechanics.getFlightSpeed(ShipType.MULE) < expectedSpeed) {
            expectedSpeed = shipMechanics.getFlightSpeed(ShipType.MULE);
        }

        assertEquals(actualSpeed, expectedSpeed);
    }

    @Test
    public void testGetOrCreateHangar() throws Exception {
        HangarDAO hangarDAO = mock(HangarDAO.class);
        UUIDFactory uuidFactory = mock(UUIDFactory.class);
        UUID planetId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        UUID hangarId = UUID.randomUUID();

        when(uuidFactory.create()).thenReturn(hangarId);
        when(hangarDAO.findWithPlanetId(planetId)).thenReturn(Optional.empty());

        Hangar actualHangar = utils.getOrCreateHangar(hangarDAO, uuidFactory, planetId, playerId);

        assertEquals(actualHangar.getId(), hangarId);
        assertEquals(actualHangar.getPlanetId(), planetId);
        assertEquals(actualHangar.getPlayerId(), playerId);
        assertTrue(actualHangar.getShips().isEmpty());
    }

}