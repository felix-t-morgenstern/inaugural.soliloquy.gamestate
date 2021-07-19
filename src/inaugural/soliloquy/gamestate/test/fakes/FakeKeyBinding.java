package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.logger.Logger;

@SuppressWarnings("rawtypes")
public class FakeKeyBinding implements KeyBinding {
    private final List<Character> BOUND_CHARACTERS = new FakeList<>();
    private final Action PRESS_ACTION = new PressAction();
    private final Action RELEASE_ACTION = new ReleaseAction();
    private final Action TYPE_ACTION = new TypeAction();

    public boolean _pressed;
    public boolean _released;
    public boolean _typed;

    private boolean _blocksLowerBindings;

    @Override
    public List<Character> boundCharacters() {
        return BOUND_CHARACTERS;
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

    private class TypeAction implements Action<Void> {

        @Override
        public void run(Void aVoid) throws IllegalArgumentException {
            _typed = true;
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
