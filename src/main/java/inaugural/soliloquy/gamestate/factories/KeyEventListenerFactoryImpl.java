package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.KeyEventListenerImpl;
import soliloquy.specs.gamestate.entities.KeyEventListener;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

public class KeyEventListenerFactoryImpl implements KeyEventListenerFactory {
    @Override
    public KeyEventListener make(Long mostRecentTimestamp) {
        return new KeyEventListenerImpl(mostRecentTimestamp);
    }

    @Override
    public String getInterfaceName() {
        return KeyEventListenerFactory.class.getCanonicalName();
    }
}
