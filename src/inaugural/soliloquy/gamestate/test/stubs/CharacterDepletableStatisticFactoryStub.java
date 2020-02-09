package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistic;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.CharacterDepletableStatisticType;

public class CharacterDepletableStatisticFactoryStub
        implements CharacterEntityOfTypeFactory<CharacterDepletableStatisticType,
        CharacterDepletableStatistic> {
    @Override
    public CharacterDepletableStatistic make(Character character, CharacterDepletableStatisticType type) throws IllegalArgumentException {
        return new CharacterDepletableStatisticStub(type, character);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
