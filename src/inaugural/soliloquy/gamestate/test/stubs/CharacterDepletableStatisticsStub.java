package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistics;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

import java.util.HashMap;
import java.util.Iterator;

public class CharacterDepletableStatisticsStub implements CharacterDepletableStatistics {
    HashMap<CharacterDepletableStatisticType, CharacterDepletableStatistic> STATS =
            new HashMap<>();

    public final Character _character;

    public boolean _isDeleted;

    public CharacterDepletableStatisticsStub(Character character) {
        _character = character;
    }

    @Override
    public ReadableMap<CharacterDepletableStatisticType, Integer> currentValues() {
        MapStub<CharacterDepletableStatisticType, Integer> currentValues = new MapStub<>();
        STATS.forEach((t,s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues.readOnlyRepresentation();
    }

    @Override
    public ReadableMap<CharacterDepletableStatisticType, Integer> maxValues() {
        MapStub<CharacterDepletableStatisticType, Integer> maxValues = new MapStub<>();
        STATS.forEach((t,s) -> maxValues.put(t, s.totalValue()));
        return maxValues.readOnlyRepresentation();
    }

    @Override
    public void add(CharacterDepletableStatisticType type) throws IllegalArgumentException {
        if (!STATS.containsKey(type)) {
            STATS.put(type, new CharacterDepletableStatisticStub(_character, type));
        }
    }

    @Override
    public CharacterDepletableStatistic get(CharacterDepletableStatisticType type) throws IllegalArgumentException {
        return STATS.get(type);
    }

    @Override
    public int size() {
        return STATS.size();
    }

    @Override
    public boolean remove(CharacterDepletableStatisticType type) throws IllegalArgumentException {
        return STATS.remove(type) != null;
    }

    @Override
    public boolean contains(CharacterDepletableStatisticType type) throws IllegalArgumentException {
        return STATS.containsKey(type);
    }

    @Override
    public void clear() {
        STATS.clear();
    }

    @Override
    public ReadableCollection<CharacterDepletableStatistic> representation() {
        Collection<CharacterDepletableStatistic> representation = new CollectionStub<>();
        STATS.values().forEach(representation::add);
        return representation.readOnlyRepresentation();
    }

    @Override
    public Iterator<CharacterDepletableStatistic> iterator() {
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
