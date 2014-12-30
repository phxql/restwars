package restwars.service.ship.impl;

import org.testng.annotations.Test;
import restwars.service.ship.Attack;
import restwars.service.ship.Ship;
import restwars.service.ship.ShipType;
import restwars.service.ship.Ships;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AttackCalculatorTest {
    private final AttackCalculator sut = new AttackCalculator();

    @Test
    public void testAttack() throws Exception {
        Attack attack = sut.attack(new Ships(new Ship(ShipType.MOSQUITO, 5)), new Ships(new Ship(ShipType.MOSQUITO, 3)));

        assertThat(attack.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(2L));
        assertThat(attack.getRemainingDefenderShips().countByType(ShipType.MOSQUITO), is(0L));
    }

    @Test
    public void testAttack2() throws Exception {
        Attack attack = sut.attack(new Ships(new Ship(ShipType.MOSQUITO, 5)), new Ships(new Ship(ShipType.MOSQUITO, 5)));

        assertThat(attack.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(0L));
        assertThat(attack.getRemainingDefenderShips().countByType(ShipType.MOSQUITO), is(0L));
    }

    @Test
    public void testAttack3() throws Exception {
        Attack attack = sut.attack(new Ships(new Ship(ShipType.MOSQUITO, 3)), new Ships(new Ship(ShipType.MOSQUITO, 5)));

        assertThat(attack.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(0L));
        assertThat(attack.getRemainingDefenderShips().countByType(ShipType.MOSQUITO), is(2L));
    }

    @Test
    public void testAttack4() throws Exception {
        Attack attack = sut.attack(new Ships(new Ship(ShipType.MOSQUITO, 5)), new Ships(new Ship(ShipType.COLONY, 1)));

        assertThat(attack.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(5L));
        assertThat(attack.getRemainingDefenderShips().countByType(ShipType.COLONY), is(0L));
    }

    @Test
    public void testAttack5() throws Exception {
        Attack attack = sut.attack(new Ships(new Ship(ShipType.MOSQUITO, 4)), new Ships(new Ship(ShipType.COLONY, 1)));

        assertThat(attack.getRemainingAttackerShips().countByType(ShipType.MOSQUITO), is(4L));
        assertThat(attack.getRemainingDefenderShips().countByType(ShipType.COLONY), is(1L));
    }
}