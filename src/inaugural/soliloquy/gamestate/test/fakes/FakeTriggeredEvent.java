package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.gameevents.firings.TriggeredEvent;

public class FakeTriggeredEvent implements TriggeredEvent {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void fire() {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
