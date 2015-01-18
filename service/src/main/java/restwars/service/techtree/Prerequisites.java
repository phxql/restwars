package restwars.service.techtree;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import restwars.service.building.BuildingType;
import restwars.service.technology.TechnologyType;

import java.util.Collections;
import java.util.List;

/**
 * Prerequisites.
 */
public class Prerequisites {
    /**
     * No prerequisites.
     */
    public static final Prerequisites NONE = new Prerequisites(Collections.emptyList(), Collections.emptyList());

    /**
     * Building prerequisite.
     */
    public static class Building {
        private final BuildingType type;
        private final int level;

        public Building(BuildingType type, int level) {
            Preconditions.checkArgument(level > 0, "level must be > 0");
            this.type = Preconditions.checkNotNull(type, "type");
            this.level = level;
        }

        public BuildingType getType() {
            return type;
        }

        public int getLevel() {
            return level;
        }
    }

    /**
     * Technology prerequisite.
     */
    public static class Technology {
        private final TechnologyType type;
        private final int level;

        public Technology(TechnologyType type, int level) {
            Preconditions.checkArgument(level > 0, "level must be > 0");
            this.type = Preconditions.checkNotNull(type, "type");
            this.level = level;
        }

        public TechnologyType getType() {
            return type;
        }

        public int getLevel() {
            return level;
        }
    }

    private final List<Building> buildings;
    private final List<Technology> technologies;

    public Prerequisites(List<Building> buildings, List<Technology> technologies) {
        this.buildings = ImmutableList.copyOf(buildings);
        this.technologies = ImmutableList.copyOf(technologies);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public static Prerequisites building(BuildingType type, int level) {
        return new Prerequisites(Lists.newArrayList(new Building(type, level)), Collections.emptyList());
    }

    public static Prerequisites buildings(Building... buildings) {
        return new Prerequisites(Lists.newArrayList(buildings), Collections.emptyList());
    }

    public static Prerequisites technology(TechnologyType type, int level) {
        return new Prerequisites(Collections.emptyList(), Lists.newArrayList(new Technology(type, level)));
    }

    public boolean fulfilled(List<Building> availableBuildings, List<Technology> availableTechnologies) {
        // Check that every building requirement and technology requirement is fulfilled
        return buildings.stream().allMatch(b -> availableBuildings.stream().anyMatch(ab -> ab.getType().equals(b.getType()) && ab.getLevel() >= b.getLevel())) &&
                technologies.stream().allMatch(t -> availableTechnologies.stream().anyMatch(at -> at.getType().equals(t.getType()) && at.getLevel() >= t.getLevel()));
    }
}
