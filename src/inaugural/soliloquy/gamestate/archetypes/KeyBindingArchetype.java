package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.gamestate.entities.KeyBinding;

public class KeyBindingArchetype implements KeyBinding {
    @Override
    public Collection<Character> boundCharacters() {
        return null;
    }

    @Override
    public Action<Void> getOnPress() {
        return null;
    }

    @Override
    public void setOnPress(Action<Void> action) {

    }

    @Override
    public Action<Void> getOnRelease() {
        return null;
    }

    @Override
    public void setOnRelease(Action<Void> action) {

    }

    @Override
    public Action<Void> getOnType() {
        return null;
    }

    @Override
    public void setOnType(Action<Void> action) {

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
