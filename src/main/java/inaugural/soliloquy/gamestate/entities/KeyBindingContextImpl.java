package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.KeyBindingArchetype;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import java.util.ArrayList;
import java.util.List;

public class KeyBindingContextImpl implements KeyBindingContext {
    private final List<KeyBinding> BINDINGS;
    private static final KeyBinding ARCHETYPE = new KeyBindingArchetype();

    private boolean _blocksAllLowerBindings;

    public KeyBindingContextImpl() {
        BINDINGS = new ArrayList<>();
    }

    // TODO: Ensure that this is a clone
    @Override
    public List<KeyBinding> bindings() {
        return new ArrayList<>(BINDINGS);
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
