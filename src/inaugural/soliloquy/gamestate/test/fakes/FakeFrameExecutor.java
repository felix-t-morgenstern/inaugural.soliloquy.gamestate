package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.gameevents.firings.TriggeredEvent;
import soliloquy.specs.graphics.rendering.FrameExecutor;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FakeFrameExecutor implements FrameExecutor {
    public ArrayList<Consumer<Long>> RegisteredFrameBlockingEvents = new ArrayList<>();
    public long GlobalTimestamp;

    @Override
    public void placeTriggeredEventFiringBlock(TriggeredEvent triggeredEvent)
            throws IllegalArgumentException {

    }

    @Override
    public void releaseTriggeredEventFiringBlock(TriggeredEvent triggeredEvent)
            throws IllegalArgumentException {

    }

    @Override
    public void registerTriggeredEventToFire(TriggeredEvent triggeredEvent)
            throws IllegalArgumentException {

    }

    @Override
    public void registerFrameBlockingEvent(Consumer<Long> frameBlockingEvent)
            throws IllegalArgumentException {
        RegisteredFrameBlockingEvents.add(frameBlockingEvent);
    }

    @Override
    public void execute() {
        RegisteredFrameBlockingEvents.forEach(event -> event.accept(GlobalTimestamp));
    }
}
