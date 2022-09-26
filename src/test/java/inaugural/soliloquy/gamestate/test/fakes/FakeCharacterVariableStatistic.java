package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class FakeCharacterVariableStatistic implements CharacterVariableStatistic {
    public boolean _isDeleted;
    public boolean _isCalculated;
    public CharacterVariableStatisticType _type;
    public VariableCache _data;
    public Character _character;
    public int _maxValue;
    private int _currentValue;

    FakeCharacterVariableStatistic(CharacterVariableStatisticType type) {
        _type = type;
    }

    public FakeCharacterVariableStatistic(CharacterVariableStatisticType type,
                                          Character character) {
        _type = type;
        _character = character;
    }

    public FakeCharacterVariableStatistic(CharacterVariableStatisticType type, Character character,
                                          VariableCache data) {
        this(type, character);
        _data = data;
    }

    @Override
    public CharacterVariableStatisticType type() throws IllegalStateException {
        return _type;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
    }

    @Override
    public int getCurrentValue() throws IllegalStateException {
        return _currentValue;
    }

    @Override
    public void setCurrentValue(int i) throws IllegalStateException, IllegalArgumentException {
        _currentValue = i;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return _maxValue;
    }

    @Override
    public Map<String, Integer> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculate() throws IllegalStateException {
        _isCalculated = true;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
