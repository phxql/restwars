package restwars.service.ship;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ships implements Iterable<Ship> {
    public static final Ships EMPTY = new Ships();

    private final Map<ShipType, Integer> ships = Maps.newHashMap();

    public Ships() {
    }

    public Ships(Ship... ships) {
        this(Arrays.asList(ships));
    }

    public Ships(List<Ship> ships) {
        Preconditions.checkNotNull(ships, "ships");

        for (Ship ship : ships) {
            this.ships.put(ship.getType(), this.ships.getOrDefault(ship.getType(), 0) + ship.getAmount());
        }

        reduce();
    }

    public Ships(Map<ShipType, Integer> ships) {
        Preconditions.checkNotNull(ships, "ships");

        this.ships.putAll(ships);

        reduce();
    }

    /**
     * Removes all ships with an amount of zero.
     */
    private void reduce() {
        Iterator<Map.Entry<ShipType, Integer>> iterator = ships.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ShipType, Integer> entry = iterator.next();
            if (entry.getValue() == 0) {
                iterator.remove();
            }
        }
    }

    public Ships plus(List<Ship> ships) {
        Preconditions.checkNotNull(ships, "ships");
        Map<ShipType, Integer> newShips = Maps.newHashMap(this.ships);

        for (Ship ship : ships) {
            newShips.put(ship.getType(), newShips.getOrDefault(ship.getType(), 0) + ship.getAmount());
        }

        return new Ships(newShips);
    }

    public Ships plus(Ships ships) {
        Preconditions.checkNotNull(ships, "ships");

        return plus(ships.asList());
    }

    public Ships plus(ShipType type, int count) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(count >= 0, "count must be >= 0");

        return plus(Lists.newArrayList(new Ship(type, count)));
    }

    public Ships minus(List<Ship> ships) {
        Preconditions.checkNotNull(ships, "ships");
        Map<ShipType, Integer> newShips = Maps.newHashMap(this.ships);

        for (Ship ship : ships) {
            newShips.put(ship.getType(), newShips.getOrDefault(ship.getType(), 0) - ship.getAmount());
        }

        return new Ships(newShips);
    }

    public Ships minus(Ships ships) {
        Preconditions.checkNotNull(ships, "ships");

        return minus(ships.asList());
    }

    public Ships minus(ShipType type, int count) {
        Preconditions.checkNotNull(type, "type");
        Preconditions.checkArgument(count >= 0, "count must be >= 0");

        return minus(Lists.newArrayList(new Ship(type, count)));
    }

    public Map<ShipType, Integer> asMap() {
        return Collections.unmodifiableMap(ships);
    }

    public Stream<Ship> stream() {
        return ships.entrySet().stream().map(e -> new Ship(e.getKey(), e.getValue()));
    }

    public List<Ship> asList() {
        return stream().collect(Collectors.toList());
    }

    public long countByType(ShipType type) {
        Preconditions.checkNotNull(type, "type");

        return ships.getOrDefault(type, 0);
    }

    public boolean isEmpty() {
        return ships.isEmpty();
    }

    public long amount() {
        return ships.values().stream().mapToLong(a -> a).sum();
    }

    @Override
    public Iterator<Ship> iterator() {
        return asList().iterator();
    }

    @Override
    public String toString() {
        return ships.toString();
    }
}
