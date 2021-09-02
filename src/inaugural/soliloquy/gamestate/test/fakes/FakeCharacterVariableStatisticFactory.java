package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.factories.EntityMemberOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class FakeCharacterVariableStatisticFactory
        implements EntityMemberOfTypeFactory<CharacterVariableStatisticType,
                CharacterVariableStatistic, Character> {
    @Override
    public CharacterVariableStatistic make(Character character, CharacterVariableStatisticType type) throws IllegalArgumentException {
        return new FakeCharacterVariableStatistic(type, character);
    }

    @Override
    public CharacterVariableStatistic make(Character character, CharacterVariableStatisticType type, VariableCache data) throws IllegalArgumentException {
        return new FakeCharacterVariableStatistic(type, character, data);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
