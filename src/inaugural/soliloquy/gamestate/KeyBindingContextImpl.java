package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.KeyBindingArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

public class KeyBindingContextImpl implements KeyBindingContext {
    private final List<KeyBinding> BINDINGS;
    private static final KeyBinding ARCHETYPE = new KeyBindingArchetype();

    private boolean _blocksAllLowerBindings;

    public KeyBindingContextImpl(ListFactory listFactory) {
        BINDINGS = Check.ifNull(listFactory, "listFactory").make(ARCHETYPE);
    }

    // TODO: Ensure that this is a clone
    @Override
    public List<KeyBinding> bindings() {
        return BINDINGS.makeClone();
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
