package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.KeyEventListener;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

public class KeyEventListenerFactoryStub implements KeyEventListenerFactory {
    @Override
    public KeyEventListener make(Long mostRecentTimestamp) {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
