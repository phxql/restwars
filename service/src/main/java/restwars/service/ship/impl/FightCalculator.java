package restwars.service.ship.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.ship.Fight;
import restwars.service.ship.Ship;
import restwars.service.ship.ShipType;
import restwars.service.ship.Ships;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FightCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FightCalculator.class);

    /**
     * Random number generator.
     */
    private final Random random = new Random();

    public Fight attack(Ships attackingShips, Ships defendingShips) {
        LOGGER.debug("Fight between {} and {}", attackingShips, defendingShips);

        Ships remainingDefendingShips = fight(attackingShips, defendingShips);
        Ships remainingAttackingShips = fight(defendingShips, attackingShips);

        return new Fight(attackingShips, defendingShips, remainingAttackingShips, remainingDefendingShips);
    }

    /**
     * Starts a fight between the attacking ships and the defending ships.
     *
     * @param attackingShips Attacking ships.
     * @param defendingShips Defending ships.
     * @return Remaining defender ships.
     */
    private Ships fight(Ships attackingShips, Ships defendingShips) {
        long attackerAttackPoints = attackingShips.stream().mapToLong(s -> s.getType().getAttackPoints() * s.getAmount()).sum();
        Ships remainingDefendingShips = defendingShips;

        while (attackerAttackPoints > 0) {
            List<ShipType> destroyableShips = findDestroyableShips(remainingDefendingShips, attackerAttackPoints);
            if (destroyableShips.isEmpty()) {
                // No more ships can be destroyed
                break;
            }

            // Randomly target a ship and destroy it
            ShipType shipToDestroy = destroyableShips.get(random.nextInt(destroyableShips.size()));
            remainingDefendingShips = remainingDefendingShips.minus(shipToDestroy, 1);

            attackerAttackPoints = attackerAttackPoints - shipToDestroy.getDefensePoints();
        }

        return remainingDefendingShips;
    }

    private List<ShipType> findDestroyableShips(Ships ships, long attackPoints) {
        return ships.stream().map(Ship::getType).filter(s -> s.getDefensePoints() <= attackPoints).collect(Collectors.toList());
    }
}
