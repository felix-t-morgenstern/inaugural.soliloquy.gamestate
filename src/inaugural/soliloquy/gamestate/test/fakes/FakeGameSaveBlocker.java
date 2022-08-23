package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.ArrayList;
import java.util.UUID;

public class FakeGameSaveBlocker implements GameSaveBlocker {
    public ArrayList<UUID> PlaceTriggeredEventBlockInputs = new ArrayList<>();
    public ArrayList<UUID> ReleaseTriggeredEventBlockInputs = new ArrayList<>();

    @Override
    public void placeEventFiringBlock(UUID blockId)
            throws IllegalArgumentException {
        PlaceTriggeredEventBlockInputs.add(blockId);
    }

    @Override
    public void releaseEventFiringBlock(UUID blockId)
            throws IllegalArgumentException {
        ReleaseTriggeredEventBlockInputs.add(blockId);
    }

    @Override
    public void placeManualBlock() {

    }

    @Override
    public void releaseManualBlock() {

    }

    @Override
    public boolean canSaveGame() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
