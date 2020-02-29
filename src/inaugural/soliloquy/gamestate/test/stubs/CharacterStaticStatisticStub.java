package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStatistic;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

public class CharacterStaticStatisticStub implements CharacterStatistic<CharacterStaticStatisticType> {
    public boolean _isDeleted;
    private CharacterStaticStatisticType _type;

    CharacterStaticStatisticStub(CharacterStaticStatisticType type) {
        _type = type;
    }

    CharacterStaticStatisticStub(Character character, CharacterStaticStatisticType type,
                                 VariableCache data) {
        this(type);
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
    public ReadableMap<String, Integer> representation() throws IllegalStateException {
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
        return null;
    }
}
