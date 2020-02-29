package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
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
    public CharacterVariableStatistic make(Character character, CharacterVariableStatisticType type, VariableCache variableCache) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
