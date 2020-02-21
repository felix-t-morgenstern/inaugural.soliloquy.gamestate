package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticStub implements CharacterVariableStatistic {
    public boolean _isDeleted;
    public boolean _isCalculated;
    public CharacterVariableStatisticType _type;
    public Character _character;
    public int _maxValue;
    private int _currentValue;

    public CharacterVariableStatisticStub() {

    }

    CharacterVariableStatisticStub(CharacterVariableStatisticType type) {
        _type = type;
    }

    public CharacterVariableStatisticStub(CharacterVariableStatisticType type, Character character) {
        _type = type;
        _character = character;
    }

    @Override
    public CharacterVariableStatisticType type() throws IllegalStateException {
        return _type;
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
    public ReadableMap<String, Integer> representation() throws IllegalStateException {
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
