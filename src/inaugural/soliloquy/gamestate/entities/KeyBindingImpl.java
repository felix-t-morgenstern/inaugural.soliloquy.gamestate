package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingImpl implements KeyBinding {
    private final List<Character> BOUND_CHARACTERS;

    private Action<Long> _onPress;
    private Action<Long> _onRelease;
    private boolean _blocksLowerBindings;

    @SuppressWarnings("ConstantConditions")
    public KeyBindingImpl(ListFactory listFactory) {
        BOUND_CHARACTERS = Check.ifNull(listFactory, "listFactory").make(' ');
    }

    // TODO: Ensure that this is a clone
    @Override
    public List<Character> boundCharacters() {
        return BOUND_CHARACTERS.makeClone();
    }

    @Override
    public void press(long l) throws IllegalArgumentException {

    }

    @Override
    public String onPressActionId() {
        return null;
    }

    @Override
    public void release(long l) throws IllegalArgumentException {

    }

    @Override
    public String onReleaseActionId() {
        return null;
    }

    @Override
    public void setOnPress(Action<Long> onPress) {
        _onPress = onPress;
    }

    @Override
    public void setOnRelease(Action<Long> onRelease) {
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
