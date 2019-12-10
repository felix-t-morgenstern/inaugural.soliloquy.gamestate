package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterStaticStatistic;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

public class CharacterStaticStatisticStub implements CharacterStaticStatistic {
    public boolean _isDeleted;
    public Character _character;
    public CharacterStaticStatisticType _type;

    public CharacterStaticStatisticStub() {

    }

    public CharacterStaticStatisticStub(Character character, CharacterStaticStatisticType type) {
        _character = character;
        _type = type;
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
    public ReadableMap<String, Integer> modifiersRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculateValue() throws IllegalStateException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
