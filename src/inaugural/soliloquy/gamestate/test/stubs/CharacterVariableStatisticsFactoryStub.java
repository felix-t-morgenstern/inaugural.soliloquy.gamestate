package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.CharacterVariableStatisticsFactory;

public class CharacterVariableStatisticsFactoryStub
        implements CharacterVariableStatisticsFactory {
    @Override
    public CharacterVariableStatistics make(Character character) {
        return new CharacterVariableStatisticsStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
