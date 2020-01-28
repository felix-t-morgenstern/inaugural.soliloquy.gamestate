package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

public class KeyBindingFactoryStub implements KeyBindingFactory {
    @Override
    public KeyBinding make() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
