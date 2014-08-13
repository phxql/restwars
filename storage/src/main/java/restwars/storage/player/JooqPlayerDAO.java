package restwars.storage.player;

import com.google.common.base.Preconditions;
import org.jooq.Record;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.service.unitofwork.UnitOfWorkService;
import restwars.storage.jooq.AbstractJooqDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

import static restwars.storage.jooq.Tables.PLAYER;

@Singleton
public class JooqPlayerDAO extends AbstractJooqDAO implements PlayerDAO {
    private final UnitOfWorkService unitOfWorkService;

    @Inject
    public JooqPlayerDAO(UnitOfWorkService unitOfWorkService) {
        this.unitOfWorkService = Preconditions.checkNotNull(unitOfWorkService, "unitOfWorkService");
    }

    @Override
    public void insert(Player player) {
        Preconditions.checkNotNull(player, "player");

        context(unitOfWorkService.getCurrent()).insertInto(PLAYER, PLAYER.ID, PLAYER.USERNAME, PLAYER.PASSWORD)
                .values(player.getId(), player.getUsername(), player.getPassword())
                .execute();

    }

    @Override
    public Optional<Player> findWithUsername(String username) {
        Preconditions.checkNotNull(username, "username");

        Record record = context(unitOfWorkService.getCurrent()).select().from(PLAYER).where(PLAYER.USERNAME.eq(username)).fetchAny();
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
