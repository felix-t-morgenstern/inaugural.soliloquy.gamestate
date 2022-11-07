package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.gamestate.entities.KeyBindingContext;

import java.util.ArrayList;
import java.util.List;

public class FakeKeyBindingContext implements KeyBindingContext {
    private List<KeyBinding> BINDINGS = new ArrayList<>();

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
