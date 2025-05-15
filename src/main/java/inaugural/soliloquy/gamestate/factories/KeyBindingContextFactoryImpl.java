package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.KeyBindingContextImpl;
import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.factories.KeyBindingContextFactory;

public class KeyBindingContextFactoryImpl implements KeyBindingContextFactory {
    @Override
    public KeyBindingContext make() {
        return new KeyBindingContextImpl();
    }
}
