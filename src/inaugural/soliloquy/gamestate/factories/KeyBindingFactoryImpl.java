package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.KeyBindingImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.factories.KeyBindingFactory;

public class KeyBindingFactoryImpl implements KeyBindingFactory {
    private final ListFactory LIST_FACTORY;

    public KeyBindingFactoryImpl(ListFactory listFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @Override
    public KeyBinding make() {
        return new KeyBindingImpl(LIST_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return KeyBindingFactory.class.getCanonicalName();
    }
}
