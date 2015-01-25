package restwars.service.flight.impl.flighthandler;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.mechanics.ShipMechanics;
import restwars.service.fight.Fight;
import restwars.service.infrastructure.RandomNumberGenerator;
import restwars.service.infrastructure.UUIDFactory;
import restwars.service.resource.Resources;
import restwars.service.ship.Ship;
import restwars.service.ship.ShipType;
import restwars.service.ship.Ships;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FightCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FightCalculator.class);

    private final UUIDFactory uuidFactory;
    private final RandomNumberGenerator randomNumberGenerator;
    private final ShipMechanics shipMechanics;

    public FightCalculator(UUIDFactory uuidFactory, RandomNumberGenerator randomNumberGenerator, ShipMechanics shipMechanics) {
        this.shipMechanics = Preconditions.checkNotNull(shipMechanics, "shipMechanics");
        this.uuidFactory = Preconditions.checkNotNull(uuidFactory, "uuidFactory");
        this.randomNumberGenerator = Preconditions.checkNotNull(randomNumberGenerator, "randomNumberGenerator");
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
        long attackerAttackPoints = attackingShips.stream().mapToLong(s -> shipMechanics.getAttackPoints(s.getType()) * s.getAmount()).sum();
        Ships remainingDefendingShips = defendingShips;

        while (attackerAttackPoints > 0) {
            List<ShipType> destroyableShips = findDestroyableShips(remainingDefendingShips, attackerAttackPoints);
            if (destroyableShips.isEmpty()) {
                // No more ships can be destroyed
                break;
            }

            // Randomly target a ship and destroy it
            ShipType shipToDestroy = destroyableShips.get(randomNumberGenerator.nextInt(destroyableShips.size()));
            remainingDefendingShips = remainingDefendingShips.minus(shipToDestroy, 1);

            attackerAttackPoints = attackerAttackPoints - shipMechanics.getDefensePoints(shipToDestroy);
        }

        return remainingDefendingShips;
    }

    private List<ShipType> findDestroyableShips(Ships ships, long attackPoints) {
        return ships.stream().map(Ship::getType).filter(s -> shipMechanics.getDefensePoints(s) <= attackPoints).collect(Collectors.toList());
    }
}
