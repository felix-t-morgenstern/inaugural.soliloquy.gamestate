package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

public class CharacterDepletableStatisticStub implements CharacterDepletableStatistic {
    public boolean _isDeleted;
    public boolean _isCalculated;
    private CharacterDepletableStatisticType _type;
    public int _maxValue;
    private int _currentValue;

    public CharacterDepletableStatisticStub() {

    }

    CharacterDepletableStatisticStub(CharacterDepletableStatisticType type) {
        _type = type;
    }

    @Override
    public CharacterDepletableStatisticType type() throws IllegalStateException {
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
