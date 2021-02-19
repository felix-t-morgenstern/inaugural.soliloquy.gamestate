package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.GameState;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.GameStateFactory;

public class FakeGameStateFactory implements GameStateFactory {
    @Override
    public GameState make(Party party, VariableCache variableCache) throws IllegalArgumentException {
        return new FakeGameState(party, variableCache);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
