package inaugural.soliloquy.gamestate.entities.gameevents.firings;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.gameevents.firings.TriggeredEvent;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

public class TriggeredEventImpl implements TriggeredEvent {
    private final int PRIORITY;
    private final Runnable FIRE;
    private final GameSaveBlocker GAME_SAVE_BLOCKER;

    public TriggeredEventImpl(int priority, Runnable fire, GameSaveBlocker gameSaveBlocker) {
        PRIORITY = priority;
        FIRE = Check.ifNull(fire, "fire");
        GAME_SAVE_BLOCKER = Check.ifNull(gameSaveBlocker, "gameSaveBlocker");
        GAME_SAVE_BLOCKER.placeTriggeredEventBlock(this);
    }

    @Override
    public int priority() {
        return PRIORITY;
    }

    @Override
    public void fire() {
        FIRE.run();
        GAME_SAVE_BLOCKER.releaseTriggeredEventBlock(this);
    }

    @Override
    public String getInterfaceName() {
        return TriggeredEvent.class.getCanonicalName();
    }
}
