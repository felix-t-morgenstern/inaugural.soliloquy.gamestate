package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

import java.util.HashMap;
import java.util.Iterator;

public class FakeCharacterVariableStatistics implements CharacterVariableStatistics {
    private final Character _character;

    private final HashMap<CharacterVariableStatisticType, CharacterVariableStatistic> STATS =
            new HashMap<>();

    public boolean _isDeleted;

    public FakeCharacterVariableStatistics(Character character) {
        _character = character;
    }

    FakeCharacterVariableStatistics() {
        _character = new FakeCharacter();
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> currentValues() {
        FakeMap<CharacterVariableStatisticType, Integer> currentValues = new FakeMap<>();
        STATS.forEach((t,s) -> currentValues.put(t, s.getCurrentValue()));
        return currentValues;
    }

    @Override
    public Map<CharacterVariableStatisticType, Integer> maxValues() {
        FakeMap<CharacterVariableStatisticType, Integer> maxValues = new FakeMap<>();
        STATS.forEach((t,s) -> maxValues.put(t, s.totalValue()));
        return maxValues;
    }

    @Override
    public void add(CharacterVariableStatisticType type) throws IllegalArgumentException {
        if (!STATS.containsKey(type)) {
            STATS.put(type, new FakeCharacterVariableStatistic(type));
        }
    }

    @Override
    public void add(CharacterVariableStatisticType type, VariableCache variableCache) throws IllegalArgumentException {
        if (!STATS.containsKey(type)) {
            STATS.put(type, new FakeCharacterVariableStatistic(type, _character, variableCache));
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
    public List<CharacterVariableStatistic> representation() {
        List<CharacterVariableStatistic> representation = new FakeList<>();
        representation.addAll(STATS.values());
        return representation;
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
