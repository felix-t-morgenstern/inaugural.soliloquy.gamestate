package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.KeyBindingContextImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

public class KeyBindingContextFactoryImpl implements KeyBindingContextFactory {
    private final ListFactory LIST_FACTORY;

    public KeyBindingContextFactoryImpl(ListFactory listFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @Override
    public KeyBindingContext make() {
        return new KeyBindingContextImpl(LIST_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return KeyBindingContextFactory.class.getCanonicalName();
    }
}
