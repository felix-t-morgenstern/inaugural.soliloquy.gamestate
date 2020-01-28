package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.KeyPressListener;
import soliloquy.specs.gamestate.factories.KeyPressListenerFactory;

public class KeyPressListenerFactoryStub implements KeyPressListenerFactory {
    @Override
    public KeyPressListener make() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
