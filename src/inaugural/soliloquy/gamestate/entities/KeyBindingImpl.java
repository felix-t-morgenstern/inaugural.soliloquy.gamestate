package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingImpl implements KeyBinding {
    private final List<Character> BOUND_CHARACTERS;
    private final TimestampValidator TIMESTAMP_VALIDATOR;

    private Action<Long> _onPress;
    private Action<Long> _onRelease;
    private boolean _blocksLowerBindings;

    @SuppressWarnings("ConstantConditions")
    public KeyBindingImpl(ListFactory listFactory) {
        BOUND_CHARACTERS = Check.ifNull(listFactory, "listFactory").make(' ');
        // TODO: Consider having this accept a most recent timestamp on creation
        TIMESTAMP_VALIDATOR = new TimestampValidator(null);
    }

    // TODO: Ensure that this is a clone
    @Override
    public List<Character> boundCharacters() {
        return BOUND_CHARACTERS.makeClone();
    }

    @Override
    public void press(long timestamp) throws IllegalArgumentException {
        runAction(timestamp, _onPress);
    }

    @Override
    public String onPressActionId() {
        return _onPress == null ? null : _onPress.id();
    }

    @Override
    public void release(long timestamp) throws IllegalArgumentException {
        runAction(timestamp, _onRelease);
    }

    @Override
    public String onReleaseActionId() {
        return _onRelease == null ? null : _onRelease.id();
    }

    private void runAction(long timestamp, Action<Long> action) {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        if (action != null) {
            action.run(timestamp);
        }
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
