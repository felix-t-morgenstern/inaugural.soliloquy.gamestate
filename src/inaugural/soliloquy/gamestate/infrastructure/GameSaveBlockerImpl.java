package inaugural.soliloquy.gamestate.infrastructure;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.HashSet;
import java.util.UUID;

public class GameSaveBlockerImpl implements GameSaveBlocker {
    private final HashSet<UUID> SAVE_BLOCKING_EVENT_SERIES;

    private boolean _manuallyBlocked;

    public GameSaveBlockerImpl() {
        SAVE_BLOCKING_EVENT_SERIES = new HashSet<>();
    }

    @Override
    public void placeEventFiringBlock(UUID blockId)
            throws IllegalArgumentException {
        SAVE_BLOCKING_EVENT_SERIES.add(Check.ifNull(blockId, "blockId"));
    }

    @Override
    public void releaseEventFiringBlock(UUID blockId)
            throws IllegalArgumentException {
        SAVE_BLOCKING_EVENT_SERIES.remove(Check.ifNull(blockId, "blockId"));
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
        return SAVE_BLOCKING_EVENT_SERIES.isEmpty() && !_manuallyBlocked;
    }

    @Override
    public String getInterfaceName() {
        return GameSaveBlocker.class.getCanonicalName();
    }
}
