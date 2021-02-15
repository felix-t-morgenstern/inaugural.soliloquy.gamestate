package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

public class KeyBindingContextArchetype implements KeyBindingContext {
    @Override
    public List<KeyBinding> bindings() {
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
