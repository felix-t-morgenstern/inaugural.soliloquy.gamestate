package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterVariableStatisticType;

public class CharacterVariableStatisticFactoryStub
        implements CharacterEntityOfTypeFactory<CharacterVariableStatisticType,
        CharacterVariableStatistic> {
    @Override
    public CharacterVariableStatistic make(Character character, CharacterVariableStatisticType type) throws IllegalArgumentException {
        return new CharacterVariableStatisticStub(type, character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
