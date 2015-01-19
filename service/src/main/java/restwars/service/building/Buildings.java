package restwars.service.building;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

public class Buildings extends ForwardingList<Building> {
    public static final Buildings NONE = new Buildings(Collections.emptyList());

    private final ImmutableList<Building> buildings;

    public Buildings(Iterable<Building> buildings) {
        Preconditions.checkNotNull(buildings, "buildings");

        this.buildings = ImmutableList.copyOf(buildings);
    }

    /**
     * Returns the level of the given building. If the building doesn't exist, 0 is returned.
     *
     * @param type Building.
     * @return Level of the building or 0, if the building doesn't exist.
     */
    public int getLevel(BuildingType type) {
        return buildings.stream().filter(b -> b.getType().equals(type)).findAny().map(Building::getLevel).orElse(0);
    }

    public boolean has(BuildingType type) {
        return buildings.stream().anyMatch(b -> b.getType().equals(type));
    }

    @Override
    protected List<Building> delegate() {
        return buildings;
    }
}
