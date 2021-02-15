package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

public class FakeCharacterStaticStatistic implements CharacterStatistic<CharacterStaticStatisticType> {
    public boolean _isDeleted;
    private CharacterStaticStatisticType _type;
    private VariableCache _data;

    FakeCharacterStaticStatistic(CharacterStaticStatisticType type) {
        _type = type;
    }

    FakeCharacterStaticStatistic(CharacterStaticStatisticType type,
                                 VariableCache data) {
        this(type);
        _data = data;
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
    public CharacterStaticStatisticType type() {
        return _type;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public Map<String, Integer> representation() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculate() throws IllegalStateException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
    }
}
