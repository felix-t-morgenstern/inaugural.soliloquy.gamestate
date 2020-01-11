package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterDepletableStatistics;
import soliloquy.specs.gamestate.factories.CharacterDepletableStatisticsFactory;

public class CharacterDepletableStatisticsFactoryStub
        implements CharacterDepletableStatisticsFactory {
    @Override
    public CharacterDepletableStatistics make(Character character) {
        return new CharacterDepletableStatisticsStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
