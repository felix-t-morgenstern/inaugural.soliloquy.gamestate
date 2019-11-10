package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingImpl implements KeyBinding {
    private Action<Void> _onPress;
    private Action<Void> _onRelease;
    private boolean _blocksLowerBindings;

    @Override
    public Action<Void> getOnPress() {
        return _onPress;
    }

    @Override
    public void setOnPress(Action<Void> onPress) {
        _onPress = onPress;
    }

    @Override
    public Action<Void> getOnRelease() {
        return _onRelease;
    }

    @Override
    public void setOnRelease(Action<Void> onRelease) {
        _onRelease = onRelease;
    }

    @Override
    public boolean getBlocksLowerBindings() {
        return _blocksLowerBindings;
    }

    @Override
    public void setBlocksLowerBindings(boolean blocksLowerBindings) {
        _blocksLowerBindings = blocksLowerBindings;
    }

    @Override
    public String getInterfaceName() {
        return KeyBinding.class.getCanonicalName();
    }
}
