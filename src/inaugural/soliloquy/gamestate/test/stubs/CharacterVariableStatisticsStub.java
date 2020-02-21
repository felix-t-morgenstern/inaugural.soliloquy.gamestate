package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import java.util.HashMap;
import java.util.Iterator;

public class CharacterVariableStatisticsStub implements CharacterVariableStatistics {
    private HashMap<CharacterVariableStatisticType, CharacterVariableStatistic> STATS =
            new HashMap<>();

    public boolean _isDeleted;

    CharacterVariableStatisticsStub() {
    }

    @Override
    public ReadableMap<CharacterVariableStatisticType, Integer> currentValues() {
        MapStub<CharacterVariableStatisticType, Integer> currentValues = new MapStub<>();
        STATS.forEach((t,s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues.readOnlyRepresentation();
    }

    @Override
    public ReadableMap<CharacterVariableStatisticType, Integer> maxValues() {
        MapStub<CharacterVariableStatisticType, Integer> maxValues = new MapStub<>();
        STATS.forEach((t,s) -> maxValues.put(t, s.totalValue()));
        return maxValues.readOnlyRepresentation();
    }

    @Override
    public void add(CharacterVariableStatisticType type) throws IllegalArgumentException {
        if (!STATS.containsKey(type)) {
            STATS.put(type, new CharacterVariableStatisticStub(type));
        }
    }

    @Override
    public CharacterVariableStatistic get(CharacterVariableStatisticType type) throws IllegalArgumentException {
        return STATS.get(type);
    }

    @Override
    public int size() {
        return STATS.size();
    }

    @Override
    public boolean remove(CharacterVariableStatisticType type) throws IllegalArgumentException {
        return STATS.remove(type) != null;
    }

    @Override
    public boolean contains(CharacterVariableStatisticType type) throws IllegalArgumentException {
        return STATS.containsKey(type);
    }

    @Override
    public void clear() {
        STATS.clear();
    }

    @Override
    public ReadableCollection<CharacterVariableStatistic> representation() {
        Collection<CharacterVariableStatistic> representation = new CollectionStub<>();
        STATS.values().forEach(representation::add);
        return representation.representation();
    }

    @Override
    public Iterator<CharacterVariableStatistic> iterator() {
        return STATS.values().iterator();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}
