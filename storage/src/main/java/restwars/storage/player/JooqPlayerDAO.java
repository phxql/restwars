package restwars.storage.player;

import com.google.common.base.Preconditions;
import org.jooq.Record;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.unitofwork.UnitOfWork;
import restwars.storage.jooq.AbstractJooqDAO;

import javax.inject.Singleton;
import java.util.Optional;

import static restwars.storage.jooq.Tables.PLAYER;

@Singleton
public class JooqPlayerDAO extends AbstractJooqDAO implements PlayerDAO {
    @Override
    public void insert(UnitOfWork uow, Player player) {
        Preconditions.checkNotNull(uow, "uow");
        Preconditions.checkNotNull(player, "player");

        context(uow).insertInto(PLAYER, PLAYER.ID, PLAYER.USERNAME, PLAYER.PASSWORD)
                .values(player.getId(), player.getUsername(), player.getPassword())
                .execute();

    }

    @Override
    public Optional<Player> findWithUsername(UnitOfWork uow, String username) {
        Preconditions.checkNotNull(uow, "uow");
        Preconditions.checkNotNull(username, "username");

        Record record = context(uow).select().from(PLAYER).where(PLAYER.USERNAME.eq(username)).fetchAny();
        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(fromRecord(record));
    }

    private Player fromRecord(Record record) {
        assert record != null;

        return new Player(record.getValue(PLAYER.ID), record.getValue(PLAYER.USERNAME), record.getValue(PLAYER.PASSWORD));
    }
}
