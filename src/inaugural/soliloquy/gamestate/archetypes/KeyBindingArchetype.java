package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingArchetype implements KeyBinding {
    @Override
    public Collection<Character> boundCharacters() {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action getOnPress() {
        return null;
    }

    @Override
    public void setOnPress(Action action) {

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action getOnRelease() {
        return null;
    }

    @Override
    public void setOnRelease(Action action) {

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action getOnType() {
        return null;
    }

    @Override
    public void setOnType(Action action) {

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
