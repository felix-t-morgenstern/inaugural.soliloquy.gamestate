package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;

public class FakeKeyBindingContext implements KeyBindingContext {
    private List<KeyBinding> BINDINGS = listOf();

    private boolean _blocksAllLowerBindings;

    @Override
    public List<KeyBinding> bindings() {
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
