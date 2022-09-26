package inaugural.soliloquy.gamestate.test.unit.infrastructure;

import inaugural.soliloquy.gamestate.infrastructure.GameSaveBlockerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.UUID;

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
        UUID block1 = UUID.randomUUID();
        UUID block2 = UUID.randomUUID();

        assertTrue(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeEventFiringBlock(block1);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeEventFiringBlock(block2);
        _gameSaveBlocker.releaseEventFiringBlock(block1);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.releaseEventFiringBlock(block2);

        assertTrue(_gameSaveBlocker.canSaveGame());
    }

    @Test
    void testPlaceAndReleaseTriggeredEventBlockWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _gameSaveBlocker.placeEventFiringBlock(null));
        assertThrows(IllegalArgumentException.class, () ->
                _gameSaveBlocker.releaseEventFiringBlock(null));
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
        UUID block = UUID.randomUUID();

        assertTrue(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeEventFiringBlock(block);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.placeManualBlock();

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.releaseEventFiringBlock(block);

        assertFalse(_gameSaveBlocker.canSaveGame());

        _gameSaveBlocker.releaseManualBlock();

        assertTrue(_gameSaveBlocker.canSaveGame());
    }
}
