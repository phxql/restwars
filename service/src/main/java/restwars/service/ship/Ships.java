package restwars.service.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ships implements Iterable<Ship> {
    public static final Ships EMPTY = new Ships();

    private final Map<ShipType, Long> ships = Maps.newHashMap();

    public Ships() {
    }

    public Ships(List<Ship> ships) {
        Preconditions.checkNotNull(ships, "ships");

        for (Ship ship : ships) {
            this.ships.put(ship.getType(), this.ships.getOrDefault(ship.getType(), 0L) + ship.getCount());
        }
    }

    public Ships(Map<ShipType, Long> ships) {
        Preconditions.checkNotNull(ships, "ships");

        this.ships.putAll(ships);
    }

    public Ships plus(List<Ship> ships) {
        Preconditions.checkNotNull(ships, "ships");
        Map<ShipType, Long> newShips = Maps.newHashMap(this.ships);

        for (Ship ship : ships) {
            newShips.put(ship.getType(), newShips.getOrDefault(ship.getType(), 0L) + ship.getCount());
        }

        return new Ships(newShips);
    }

    public Ships plus(Ships ships) {
        Preconditions.checkNotNull(ships, "ships");

        return plus(ships.asList());
    }

    public Ships plus(ShipType type, long count) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(count >= 0, "count must be >= 0");

        return plus(Lists.newArrayList(new Ship(type, count)));
    }

    public Ships minus(List<Ship> ships) {
        Preconditions.checkNotNull(ships, "ships");
        Map<ShipType, Long> newShips = Maps.newHashMap(this.ships);

        for (Ship ship : ships) {
            newShips.put(ship.getType(), newShips.getOrDefault(ship.getType(), 0L) - ship.getCount());
        }

        return new Ships(newShips);
    }

    public Ships minus(Ships ships) {
        Preconditions.checkNotNull(ships, "ships");

        return minus(ships.asList());
    }

    public Ships minus(ShipType type, long count) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(count >= 0, "count must be >= 0");

        return minus(Lists.newArrayList(new Ship(type, count)));
    }

    public Map<ShipType, Long> asMap() {
        return Collections.unmodifiableMap(ships);
    }

    public List<Ship> asList() {
        return ships.entrySet().stream().map(e -> new Ship(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    public long countByType(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        return ships.getOrDefault(type, 0L);
    }

    @Override
    public Iterator<Ship> iterator() {
        return asList().iterator();
    }
}
