package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.KeyEventListener;
import soliloquy.specs.gamestate.factories.KeyEventListenerFactory;

public class FakeKeyEventListenerFactory implements KeyEventListenerFactory {
    public FakeKeyEventListener CREATED;

    @Override
    public KeyEventListener make(Long aLong) {
        return CREATED = new FakeKeyEventListener();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
