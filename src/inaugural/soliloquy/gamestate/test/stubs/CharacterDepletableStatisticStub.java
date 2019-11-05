package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

public class CharacterDepletableStatisticStub implements CharacterDepletableStatistic {
    public boolean _isDeleted;

    @Override
    public CharacterDepletableStatisticType type() throws IllegalStateException {
        return null;
    }

    @Override
    public int getCurrentValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setCurrentValue(int i) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public ReadableMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculateValue() throws IllegalStateException {

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
