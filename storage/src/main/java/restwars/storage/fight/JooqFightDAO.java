package restwars.storage.fight;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectOnConditionStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.model.fight.Fight;
import restwars.model.fight.FightWithPlanetAndPlayer;
import restwars.model.planet.Planet;
import restwars.model.resource.Resources;
import restwars.model.ship.Ship;
import restwars.model.ship.ShipType;
import restwars.model.ship.Ships;
import restwars.service.fight.FightDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;
import restwars.storage.jooq.tables.Player;
import restwars.storage.jooq.tables.records.FightRecord;
import restwars.storage.mapper.PlanetMapper;
import restwars.storage.mapper.PlayerMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static restwars.storage.jooq.Tables.*;

public class JooqFightDAO extends AbstractJooqDAO implements FightDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JooqFightDAO.class);
    public static final Player ATTACKER_ALIAS = PLAYER.as("a");
    public static final Player DEFENDER_ALIAS = PLAYER.as("d");

    private static enum Category {
        ATTACKER_SHIP(0),
        DEFENDER_SHIP(1),
        REMAINING_ATTACKER_SHIP(2),
        REMAINING_DEFENDER_SHIP(3);

        private final int id;

        Category(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Category fromId(int id) {
            for (Category type : Category.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown id: " + id);
        }
    }

    @Inject
    public JooqFightDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public Optional<FightWithPlanetAndPlayer> findWithId(UUID id) {
        Preconditions.checkNotNull(id, "id");
        LOGGER.debug("Finding fight with id {}", id);

        FightRecord record = context().selectFrom(FIGHT).where(FIGHT.ID.eq(id)).fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        Map<UUID, Result<Record>> result = selectFromFights()
                .where(FIGHT.ID.eq(id))
                .fetchGroups(FIGHT.ID);

        List<FightWithPlanetAndPlayer> fight = readFights(result);
        assert !fight.isEmpty();

        return Optional.of(fight.get(0));
    }

    @Override
    public void insert(Fight fight) {
        Preconditions.checkNotNull(fight, "fight");
        LOGGER.debug("Inserting fight {}", fight);

        context()
                .insertInto(FIGHT, FIGHT.ID, FIGHT.ATTACKER_ID, FIGHT.DEFENDER_ID, FIGHT.PLANET_ID, FIGHT.ROUND, FIGHT.CRYSTALS_LOOTED, FIGHT.GAS_LOOTED)
                .values(
                        fight.getId(), fight.getAttackerId(), fight.getDefenderId(), fight.getPlanetId(),
                        fight.getRound(), fight.getLoot().getCrystals(), fight.getLoot().getGas()
                )
                .execute();

        insertShips(fight, fight.getAttackingShips(), Category.ATTACKER_SHIP);
        insertShips(fight, fight.getDefendingShips(), Category.DEFENDER_SHIP);
        insertShips(fight, fight.getRemainingAttackerShips(), Category.REMAINING_ATTACKER_SHIP);
        insertShips(fight, fight.getRemainingDefenderShips(), Category.REMAINING_DEFENDER_SHIP);
    }

    @Override
    public List<FightWithPlanetAndPlayer> findFightsWithPlayerSinceRound(UUID playerId, long round) {
        Preconditions.checkNotNull(playerId, "playerId");
        LOGGER.debug("Finding all fights with player {} since round {}", playerId, round);

        Map<UUID, Result<Record>> result = selectFromFights()
                .where(FIGHT.ATTACKER_ID.eq(playerId).or(FIGHT.DEFENDER_ID.eq(playerId)))
                .and(FIGHT.ROUND.greaterOrEqual(round))
                .fetchGroups(FIGHT.ID);

        return readFights(result);
    }

    private List<FightWithPlanetAndPlayer> readFights(Map<UUID, Result<Record>> queryResult) {
        List<FightWithPlanetAndPlayer> result = Lists.newArrayList();

        for (Map.Entry<UUID, Result<Record>> entry : queryResult.entrySet()) {
            Ships attackerShips = new Ships();
            Ships defenderShips = new Ships();
            Ships remainingAttackerShips = new Ships();
            Ships remainingDefenderShips = new Ships();

            Record main = entry.getValue().get(0);

            for (Record child : entry.getValue()) {
                ShipType shipType = ShipType.fromId(child.getValue(FIGHT_SHIPS.TYPE));
                int amount = child.getValue(FIGHT_SHIPS.AMOUNT);
                Category category = Category.fromId(child.getValue(FIGHT_SHIPS.CATEGORY));

                switch (category) {
                    case ATTACKER_SHIP:
                        attackerShips = attackerShips.plus(shipType, amount);
                        break;
                    case DEFENDER_SHIP:
                        defenderShips = defenderShips.plus(shipType, amount);
                        break;
                    case REMAINING_ATTACKER_SHIP:
                        remainingAttackerShips = remainingAttackerShips.plus(shipType, amount);
                        break;
                    case REMAINING_DEFENDER_SHIP:
                        remainingDefenderShips = remainingDefenderShips.plus(shipType, amount);
                        break;
                    default:
                        throw new AssertionError("Cannot happen");
                }
            }

            Fight fight = new Fight(
                    main.getValue(FIGHT.ID), main.getValue(FIGHT.ATTACKER_ID), main.getValue(FIGHT.DEFENDER_ID),
                    main.getValue(FIGHT.PLANET_ID), attackerShips, defenderShips, remainingAttackerShips,
                    remainingDefenderShips, main.getValue(FIGHT.ROUND),
                    new Resources(main.getValue(FIGHT.CRYSTALS_LOOTED), main.getValue(FIGHT.GAS_LOOTED), 0)
            );
            Planet planet = PlanetMapper.fromRecord(main);
            restwars.model.player.Player attacker = PlayerMapper.fromRecord(main, ATTACKER_ALIAS);
            restwars.model.player.Player defender = PlayerMapper.fromRecord(main, DEFENDER_ALIAS);

            result.add(new FightWithPlanetAndPlayer(fight, planet, attacker, defender));
        }

        return result;
    }

    private SelectOnConditionStep<Record> selectFromFights() {
        return context()
                .select()
                .from(FIGHT)
                .join(FIGHT_SHIPS).on(FIGHT_SHIPS.FIGHT_ID.eq(FIGHT.ID))
                .join(PLANET).on(PLANET.ID.eq(FIGHT.PLANET_ID))
                .join(ATTACKER_ALIAS).on(ATTACKER_ALIAS.ID.eq(FIGHT.ATTACKER_ID))
                .join(DEFENDER_ALIAS).on(DEFENDER_ALIAS.ID.eq(FIGHT.DEFENDER_ID));
    }

    private void insertShips(Fight fight, Ships ships, Category category) {
        for (Ship ship : ships) {
            context()
                    .insertInto(FIGHT_SHIPS, FIGHT_SHIPS.FIGHT_ID, FIGHT_SHIPS.TYPE, FIGHT_SHIPS.AMOUNT, FIGHT_SHIPS.CATEGORY)
                    .values(fight.getId(), ship.getType().getId(), ship.getAmount(), category.getId())
                    .execute();
        }
    }
}
