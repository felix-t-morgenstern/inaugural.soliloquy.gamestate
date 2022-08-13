package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.gameevents.TriggeredEvent;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.ArrayList;

public class FakeGameSaveBlocker implements GameSaveBlocker {
    public ArrayList<TriggeredEvent> PlaceTriggeredEventBlockInputs = new ArrayList<>();
    public ArrayList<TriggeredEvent> ReleaseTriggeredEventBlockInputs = new ArrayList<>();

    @Override
    public void placeTriggeredEventBlock(TriggeredEvent triggeredEvent)
            throws IllegalArgumentException {
        PlaceTriggeredEventBlockInputs.add(triggeredEvent);
    }

    @Override
    public void releaseTriggeredEventBlock(TriggeredEvent triggeredEvent)
            throws IllegalArgumentException {
        ReleaseTriggeredEventBlockInputs.add(triggeredEvent);
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
