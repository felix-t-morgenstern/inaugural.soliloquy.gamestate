package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class KeyBindingContextImpl implements KeyBindingContext {
    private final List<KeyBinding> BINDINGS;

    private boolean _blocksAllLowerBindings;

    public KeyBindingContextImpl() {
        BINDINGS = listOf();
    }

    // TODO: Ensure that this is a clone
    @Override
    public List<KeyBinding> bindings() {
        return listOf(BINDINGS);
    }

    @Override
    public boolean getBlocksAllLowerBindings() {
        return _blocksAllLowerBindings;
    }

    @Override
    public void setBlocksAllLowerBindings(boolean blocksAllLowerBindings) {
        _blocksAllLowerBindings = blocksAllLowerBindings;
    }

    @Override
    public String getInterfaceName() {
        return KeyBindingContext.class.getCanonicalName();
    }
}
