package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterVariableStatistics;
import soliloquy.specs.gamestate.factories.CharacterVariableStatisticsFactory;

public class FakeCharacterVariableStatisticsFactory
        implements CharacterVariableStatisticsFactory {
    @Override
    public CharacterVariableStatistics make(Character character) {
        return new FakeCharacterVariableStatistics();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
