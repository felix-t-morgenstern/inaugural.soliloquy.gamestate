package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.game.Game;
import soliloquy.specs.gamestate.entities.KeyBinding;
import soliloquy.specs.logger.Logger;

@SuppressWarnings("rawtypes")
public class FakeKeyBinding implements KeyBinding {
    private final Collection<Character> BOUND_CHARACTERS = new FakeCollection<>();
    private final Action PRESS_ACTION = new PressAction();
    private final Action RELEASE_ACTION = new ReleaseAction();
    private final Action TYPE_ACTION = new TypeAction();

    public boolean _pressed;
    public boolean _released;
    public boolean _typed;

    private boolean _blocksLowerBindings;

    @Override
    public Collection<Character> boundCharacters() {
        return BOUND_CHARACTERS;
    }

    @Override
    public Action getOnPress() {
        return PRESS_ACTION;
    }

    @Override
    public void setOnPress(Action action) {

    }

    @Override
    public Action getOnRelease() {
        return RELEASE_ACTION;
    }

    @Override
    public void setOnRelease(Action action) {

    }

    @Override
    public Action getOnType() {
        return TYPE_ACTION;
    }

    @Override
    public void setOnType(Action action) {

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
