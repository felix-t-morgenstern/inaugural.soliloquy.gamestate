package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.KeyBindingArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

public class KeyBindingContextImpl implements KeyBindingContext {
    private final Collection<KeyBinding> BINDINGS;
    private static final KeyBinding ARCHETYPE = new KeyBindingArchetype();

    private boolean _blocksAllLowerBindings;

    public KeyBindingContextImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "KeyBindingContextImpl: collectionFactory cannot be null");
        }
        BINDINGS = collectionFactory.make(ARCHETYPE);
    }

    @Override
    public Collection<KeyBinding> bindings() {
        return BINDINGS;
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
