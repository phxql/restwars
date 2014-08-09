package restwars.storage.player;

import com.google.common.base.Preconditions;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import restwars.service.player.Player;
import restwars.service.player.PlayerDAO;
import restwars.storage.JdbcConnection;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

import static restwars.storage.jooq.Tables.PLAYER;

@Singleton
public class JooqPlayerDAO implements PlayerDAO {
    private final DSLContext create;

    @Inject
    public JooqPlayerDAO(JdbcConnection jdbcConnection) {
        Preconditions.checkNotNull(jdbcConnection, "jdbcConnection");

        create = DSL.using(jdbcConnection.getConnection(), jdbcConnection.getSqlDialect());
    }

    @Override
    public void insert(Player player) {
        Preconditions.checkNotNull(player, "player");

        // TODO: Use Unit of Work pattern instead of manually creating a transaction.

        create.transaction(configuration -> {
            create.insertInto(PLAYER, PLAYER.ID, PLAYER.USERNAME, PLAYER.PASSWORD)
                    .values(player.getId(), player.getUsername(), player.getPassword())
                    .execute();
        });
    }

    @Override
    public Optional<Player> findWithUsername(String username) {
        Preconditions.checkNotNull(username, "username");

        Record record = create.select().from(PLAYER).where(PLAYER.USERNAME.eq(username)).fetchAny();
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
