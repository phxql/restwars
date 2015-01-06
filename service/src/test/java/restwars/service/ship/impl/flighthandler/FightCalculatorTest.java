package restwars.service.ship.impl.flighthandler;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.ship.Fight;
import restwars.service.ship.Ship;
import restwars.service.ship.ShipType;
import restwars.service.ship.Ships;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FightCalculatorTest {
    private FightCalculator sut;

    private static final UUID ATTACKER_ID = UUID.randomUUID();
    private static final UUID DEFENDER_ID = UUID.randomUUID();
    private static final UUID PLANET_ID = UUID.randomUUID();

    @BeforeMethod
    public void setUp() throws Exception {
        UUIDFactory uuidFactory = mock(UUIDFactory.class);
        when(uuidFactory.create()).thenReturn(UUID.randomUUID());

        sut = new FightCalculator(uuidFactory);
    }

    @Test
    public void testAttack() throws Exception {
        Fight fight = sut.attack(ATTACKER_ID, DEFENDER_ID, PLANET_ID, new Ships(new Ship(ShipType.MOSQUITO, 5)), new Ships(new Ship(ShipType.MOSQUITO, 3)));

        assertThat(fight.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(2L));
        assertThat(fight.getRemainingDefenderShips().countByType(ShipType.MOSQUITO), is(0L));
    }

    @Test
    public void testAttack2() throws Exception {
        Fight fight = sut.attack(ATTACKER_ID, DEFENDER_ID, PLANET_ID, new Ships(new Ship(ShipType.MOSQUITO, 5)), new Ships(new Ship(ShipType.MOSQUITO, 5)));

        assertThat(fight.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(0L));
        assertThat(fight.getRemainingDefenderShips().countByType(ShipType.MOSQUITO), is(0L));
    }

    @Test
    public void testAttack3() throws Exception {
        Fight fight = sut.attack(ATTACKER_ID, DEFENDER_ID, PLANET_ID, new Ships(new Ship(ShipType.MOSQUITO, 3)), new Ships(new Ship(ShipType.MOSQUITO, 5)));

        assertThat(fight.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(0L));
        assertThat(fight.getRemainingDefenderShips().countByType(ShipType.MOSQUITO), is(2L));
    }

    @Test
    public void testAttack4() throws Exception {
        Fight fight = sut.attack(ATTACKER_ID, DEFENDER_ID, PLANET_ID, new Ships(new Ship(ShipType.MOSQUITO, 5)), new Ships(new Ship(ShipType.COLONY, 1)));

        assertThat(fight.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(5L));
        assertThat(fight.getRemainingDefenderShips().countByType(ShipType.COLONY), is(0L));
    }

    @Test
    public void testAttack5() throws Exception {
        Fight fight = sut.attack(ATTACKER_ID, DEFENDER_ID, PLANET_ID, new Ships(new Ship(ShipType.MOSQUITO, 4)), new Ships(new Ship(ShipType.COLONY, 1)));

        assertThat(fight.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(4L));
        assertThat(fight.getRemainingDefenderShips().countByType(ShipType.COLONY), is(1L));
    }
}