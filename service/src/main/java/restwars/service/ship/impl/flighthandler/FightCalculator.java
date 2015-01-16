package restwars.service.ship.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.resource.Resources;
import restwars.service.ship.Fight;
import restwars.service.ship.Ship;
import restwars.service.ship.ShipType;
import restwars.service.ship.Ships;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class FightCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FightCalculator.class);

    /**
     * Random number generator.
     */
    private final Random random = new Random();

    private final UUIDFactory uuidFactory;

    public FightCalculator(UUIDFactory uuidFactory) {
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
    }

    public Fight attack(UUID attackerId, UUID defenderId, UUID planetId, Ships attackingShips, Ships defendingShips, long round) {
        LOGGER.debug("Fight between {} and {}", attackingShips, defendingShips);

        Ships remainingDefendingShips = fight(attackingShips, defendingShips);
        Ships remainingAttackingShips = fight(defendingShips, attackingShips);

        LOGGER.debug("Remaining ships from attacker: {}", remainingAttackingShips);
        LOGGER.debug("Remaining ships from defender: {}", remainingDefendingShips);

        return new Fight(uuidFactory.create(), attackerId, defenderId, planetId, attackingShips, defendingShips, remainingAttackingShips, remainingDefendingShips, round, Resources.NONE);
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
