package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.logger.Logger;

public class KeyBindingStub implements KeyBinding {
    private final Collection<Character> BOUND_CHARACTERS = new CollectionStub<>();
    private final Action<Void> PRESS_ACTION = new PressAction();
    private final Action<Void> RELEASE_ACTION = new ReleaseAction();

    public boolean _pressed;
    public boolean _released;

    private boolean _blocksLowerBindings;

    @Override
    public Collection<Character> boundCharacters() {
        return BOUND_CHARACTERS;
    }

    @Override
    public Action<Void> getOnPress() {
        return PRESS_ACTION;
    }

    @Override
    public void setOnPress(Action<Void> action) {

    }

    @Override
    public Action<Void> getOnRelease() {
        return RELEASE_ACTION;
    }

    @Override
    public void setOnRelease(Action<Void> action) {

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

    private class PressAction implements Action<Void> {

        @Override
        public void run(Void aVoid) throws IllegalArgumentException {
            _pressed = true;
        }

        @Override
        public Game game() {
            return null;
        }

        @Override
        public Logger logger() {
            return null;
        }

        @Override
        public String id() throws IllegalStateException {
            return null;
        }

        @Override
        public Void getArchetype() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return null;
        }
    }

    private class ReleaseAction implements Action<Void> {

        @Override
        public void run(Void aVoid) throws IllegalArgumentException {
            _released = true;
        }

        @Override
        public Game game() {
            return null;
        }

        @Override
        public Logger logger() {
            return null;
        }

        @Override
        public String id() throws IllegalStateException {
            return null;
        }

        @Override
        public Void getArchetype() {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return null;
        }
    }
}
