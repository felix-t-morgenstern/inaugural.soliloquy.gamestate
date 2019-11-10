package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import java.util.Iterator;

public class KeyBindingContextArchetype implements KeyBindingContext {
    @Override
    public Collection<KeyBinding> bindings() {
        return null;
    }

    @Override
    public boolean getBlocksAllLowerBindings() {
        return false;
    }

    @Override
    public void setBlocksAllLowerBindings(boolean b) {

    }

    @Override
    public String getInterfaceName() {
        return KeyBindingContext.class.getCanonicalName();
    }
}
