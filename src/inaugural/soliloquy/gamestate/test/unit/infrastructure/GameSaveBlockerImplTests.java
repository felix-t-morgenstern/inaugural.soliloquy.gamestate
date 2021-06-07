package inaugural.soliloquy.gamestate.test.unit.infrastructure;

import inaugural.soliloquy.gamestate.infrastructure.GameSaveBlockerImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeTriggeredEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import static org.junit.jupiter.api.Assertions.*;

class GameSaveBlockerImplTests {
    private GameSaveBlocker _gameSaveBlocker;

    @BeforeEach
    void setUp() {
        _gameSaveBlocker = new GameSaveBlockerImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameSaveBlocker.class.getCanonicalName(),
                _gameSaveBlocker.getInterfaceName());
    }

    @Test
    void testPlaceAndReleaseTriggeredEventBlockAndCanSaveGame() {
        FakeTriggeredEvent triggeredEvent1 = new FakeTriggeredEvent();
        FakeTriggeredEvent triggeredEvent2 = new FakeTriggeredEvent();

        assertTrue(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeTriggeredEventBlock(triggeredEvent1);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeTriggeredEventBlock(triggeredEvent2);
        _gameSaveBlocker.releaseTriggeredEventBlock(triggeredEvent1);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.releaseTriggeredEventBlock(triggeredEvent2);

        assertTrue(_gameSaveBlocker.canSaveGame());
    }

    @Test
    void testPlaceAndReleaseTriggeredEventBlockWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _gameSaveBlocker.placeTriggeredEventBlock(null));
        assertThrows(IllegalArgumentException.class, () ->
                _gameSaveBlocker.releaseTriggeredEventBlock(null));
    }

    @Test
    void testPlaceAndReleaseManualBlockAndCanSaveGame() {
        assertTrue(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeManualBlock();

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.releaseManualBlock();

        assertTrue(_gameSaveBlocker.canSaveGame());
    }

    @Test
    void testPlaceAndReleaseManualAndTriggeredEventBlocksAndCanSaveGame() {
        FakeTriggeredEvent triggeredEvent = new FakeTriggeredEvent();

        assertTrue(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeTriggeredEventBlock(triggeredEvent);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeManualBlock();

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.releaseTriggeredEventBlock(triggeredEvent);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.releaseManualBlock();

        assertTrue(_gameSaveBlocker.canSaveGame());
    }
}
