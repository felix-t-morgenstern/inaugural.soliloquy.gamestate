package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.KeyBindingContext;
import soliloquy.specs.gamestate.entities.KeyEventListener;

import java.util.List;
import java.util.Map;

public class FakeKeyEventListener implements KeyEventListener {
    @Override
    public void addContext(KeyBindingContext keyBindingContext, int i)
            throws IllegalArgumentException {

    }

    @Override
    public void removeContext(KeyBindingContext keyBindingContext) throws IllegalArgumentException {

    }

    @Override
    public List<Character> activeKeysRepresentation() {
        return null;
    }

    @Override
    public void press(char c, long l) throws IllegalArgumentException {

    }

    @Override
    public void release(char c, long l) throws IllegalArgumentException {

    }

    @Override
    public Map<Integer, List<KeyBindingContext>> contextsRepresentation() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
