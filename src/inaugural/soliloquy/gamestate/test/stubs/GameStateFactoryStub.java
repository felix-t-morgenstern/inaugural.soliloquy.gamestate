package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameState;
import soliloquy.specs.gamestate.entities.Party;
import soliloquy.specs.gamestate.factories.GameStateFactory;

public class GameStateFactoryStub implements GameStateFactory {
    @Override
    public GameState make(Party party, VariableCache variableCache) throws IllegalArgumentException {
        return new GameStateStub(party, variableCache);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
