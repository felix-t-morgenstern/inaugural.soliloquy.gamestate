package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.graphics.rendering.timing.GlobalClock;

public class FakeGlobalClock implements GlobalClock {
    public Long GlobalTimestamp;

    @Override
    public long globalTimestamp() throws UnsupportedOperationException {
        return GlobalTimestamp;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
