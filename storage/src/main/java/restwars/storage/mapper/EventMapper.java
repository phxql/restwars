package restwars.storage.mapper;

import org.jooq.Record;
import restwars.model.event.Event;
import restwars.model.event.EventType;

import java.util.Optional;

import static restwars.storage.jooq.Tables.EVENT;

public final class EventMapper {
    private EventMapper() {
    }

    public static Event fromRecord(Record record) {
        return new Event(
                record.getValue(EVENT.ID), record.getValue(EVENT.PLAYER_ID), record.getValue(EVENT.PLANET_ID),
                EventType.fromId(record.getValue(EVENT.TYPE)), record.getValue(EVENT.ROUND),
                Optional.ofNullable(record.getValue(EVENT.FIGHT_ID))
        );
    }
}
