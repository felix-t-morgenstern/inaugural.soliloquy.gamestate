package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.gameevents.TriggeredEvent;

public class FakeTriggeredEvent implements TriggeredEvent {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void run() {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
