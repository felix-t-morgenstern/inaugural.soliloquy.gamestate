package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

public class KeyBindingContextStub implements KeyBindingContext {
    private Collection<KeyBinding> BINDINGS = new CollectionStub<>();

    private boolean _blocksAllLowerBindings;

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
        return null;
    }
}
