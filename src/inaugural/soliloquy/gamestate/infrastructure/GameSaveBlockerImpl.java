package inaugural.soliloquy.gamestate.infrastructure;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.gameevents.TriggeredEvent;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.HashSet;

public class GameSaveBlockerImpl implements GameSaveBlocker {
    private final HashSet<TriggeredEvent> SAVE_BLOCKING_EVENTS;

    private boolean _manuallyBlocked;

    public GameSaveBlockerImpl() {
        SAVE_BLOCKING_EVENTS = new HashSet<>();
    }

    @Override
    public void placeTriggeredEventBlock(TriggeredEvent triggeredEvent)
            throws IllegalArgumentException {
        SAVE_BLOCKING_EVENTS.add(Check.ifNull(triggeredEvent, "triggeredEvent"));
    }

    @Override
    public void releaseTriggeredEventBlock(TriggeredEvent triggeredEvent)
            throws IllegalArgumentException {
        SAVE_BLOCKING_EVENTS.remove(Check.ifNull(triggeredEvent, "triggeredEvent"));
    }

    @Override
    public void placeManualBlock() {
        _manuallyBlocked = true;
    }

    @Override
    public void releaseManualBlock() {
        _manuallyBlocked = false;
    }

    @Override
    public boolean canSaveGame() {
        return SAVE_BLOCKING_EVENTS.isEmpty() && !_manuallyBlocked;
    }

    @Override
    public String getInterfaceName() {
        return GameSaveBlocker.class.getCanonicalName();
    }
}
