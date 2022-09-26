package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class FakeKeyBinding implements KeyBinding {
    private final List<Character> BOUND_CHARACTERS = new FakeList<>();

    public Long _pressed;
    public Long _released;

    private boolean _blocksLowerBindings;

    @Override
    public List<Character> boundCharacters() {
        return BOUND_CHARACTERS;
    }

    @Override
    public void press(long timestamp) throws IllegalArgumentException {
        _pressed = timestamp;
    }

    @Override
    public String onPressActionId() {
        return null;
    }

    @Override
    public void release(long timestamp) throws IllegalArgumentException {
        _released = timestamp;
    }

    @Override
    public void setOnPress(Action action) {

    }

    @Override
    public String onReleaseActionId() {
        return null;
    }

    @Override
    public void setOnRelease(Action action) {

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
        return null;
    }
}
