package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.gameevents.firings.TriggeredEventImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.gameevents.firings.TriggeredEvent;
import soliloquy.specs.gamestate.factories.TriggeredEventFactory;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

public class TriggeredEventFactoryImpl implements TriggeredEventFactory {
    private final GameSaveBlocker GAME_SAVE_BLOCKER;

    public TriggeredEventFactoryImpl(GameSaveBlocker gameSaveBlocker) {
        GAME_SAVE_BLOCKER = Check.ifNull(gameSaveBlocker, "gameSaveBlocker");
    }

    @Override
    public TriggeredEvent make(int priority, Runnable eventAction) throws IllegalArgumentException {
        return new TriggeredEventImpl(priority, eventAction, GAME_SAVE_BLOCKER);
    }

    @Override
    public String getInterfaceName() {
        return TriggeredEventFactory.class.getCanonicalName();
    }
}
