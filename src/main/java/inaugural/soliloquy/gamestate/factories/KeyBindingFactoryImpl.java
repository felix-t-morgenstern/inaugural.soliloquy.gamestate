package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.KeyBindingImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

public class KeyBindingFactoryImpl implements KeyBindingFactory {
    @Override
    public KeyBinding make() {
        return new KeyBindingImpl();
    }

    @Override
    public String getInterfaceName() {
        return KeyBindingFactory.class.getCanonicalName();
    }
}
