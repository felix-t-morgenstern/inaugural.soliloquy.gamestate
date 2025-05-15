package inaugural.soliloquy.gamestate.infrastructure;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.Set;
import java.util.UUID;

import static inaugural.soliloquy.tools.collections.Collections.setOf;

public class GameSaveBlockerImpl implements GameSaveBlocker {
    private final Set<UUID> SAVE_BLOCKING_EVENT_SERIES;

    private boolean manuallyBlocked;

    public GameSaveBlockerImpl() {
        SAVE_BLOCKING_EVENT_SERIES = setOf();
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
        manuallyBlocked = true;
    }

    @Override
    public void releaseManualBlock() {
        manuallyBlocked = false;
    }

    @Override
    public boolean canSaveGame() {
        return SAVE_BLOCKING_EVENT_SERIES.isEmpty() && !manuallyBlocked;
    }
}
