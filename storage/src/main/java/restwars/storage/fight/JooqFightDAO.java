package restwars.storage.fight;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restwars.service.ship.Fight;
import restwars.service.ship.FightDAO;
import restwars.service.ship.Ship;
import restwars.service.ship.Ships;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;

import javax.inject.Inject;

import static restwars.storage.jooq.Tables.FIGHT;
import static restwars.storage.jooq.Tables.FIGHT_SHIPS;

public class JooqFightDAO extends AbstractJooqDAO implements FightDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JooqFightDAO.class);

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
    }


    @Inject
    public JooqFightDAO(UnitOfWorkService unitOfWorkService) {
        super(unitOfWorkService);
    }

    @Override
    public void insert(Fight fight) {
        Preconditions.checkNotNull(fight, "fight");
        LOGGER.debug("Inserting fight {}", fight);

        context()
                .insertInto(FIGHT, FIGHT.ID, FIGHT.ATTACKER_ID, FIGHT.DEFENDER_ID, FIGHT.PLANET_ID)
                .values(fight.getId(), fight.getAttackerId(), fight.getDefenderId(), fight.getPlanetId())
                .execute();

        insertShips(fight, fight.getAttackingShips(), Category.ATTACKER_SHIP);
        insertShips(fight, fight.getDefendingShips(), Category.DEFENDER_SHIP);
        insertShips(fight, fight.getRemainingAttackerShips(), Category.REMAINING_ATTACKER_SHIP);
        insertShips(fight, fight.getRemainingDefenderShips(), Category.REMAINING_DEFENDER_SHIP);
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
