package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.graphics.rendering.FrameExecutor;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FakeFrameExecutor implements FrameExecutor {
    public ArrayList<Consumer<Long>> RegisteredFrameBlockingEvents = new ArrayList<>();
    public long GlobalTimestamp;

    @Override
    public void registerFrameBlockingEvent(Consumer<Long> frameBlockingEvent)
            throws IllegalArgumentException {
        RegisteredFrameBlockingEvents.add(frameBlockingEvent);
    }

    @Override
    public void execute() {
        RegisteredFrameBlockingEvents.forEach(event -> event.accept(GlobalTimestamp));
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
