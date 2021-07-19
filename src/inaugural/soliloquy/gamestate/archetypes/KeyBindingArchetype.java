package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingArchetype implements KeyBinding {
    @Override
    public List<Character> boundCharacters() {
        return null;
    }

    @Override
    public void press(long l) throws IllegalArgumentException {

    }

    @Override
    public void setOnPress(Action<Long> action) {

    }

    @Override
    public String onPressActionId() {
        return null;
    }

    @Override
    public void release(long l) throws IllegalArgumentException {

    }

    @Override
    public void setOnRelease(Action<Long> action) {

    }

    @Override
    public String onReleaseActionId() {
        return null;
    }

    @Override
    public boolean getBlocksLowerBindings() {
        return false;
    }

    @Override
    public void setBlocksLowerBindings(boolean b) {

    }

    @Override
    public String getInterfaceName() {
        return KeyBinding.class.getCanonicalName();
    }
}
