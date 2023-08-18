package inaugural.soliloquy.gamestate.test.unit.infrastructure;

import inaugural.soliloquy.gamestate.infrastructure.GameSaveBlockerImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.UUID;

import static org.junit.Assert.*;

public class GameSaveBlockerImplTests {
    private GameSaveBlocker gameSaveBlocker;

    @Before
    public void setUp() {
        gameSaveBlocker = new GameSaveBlockerImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(GameSaveBlocker.class.getCanonicalName(),
                gameSaveBlocker.getInterfaceName());
    }

    @Test
    public void testPlaceAndReleaseTriggeredEventBlockAndCanSaveGame() {
        UUID block1 = UUID.randomUUID();
        UUID block2 = UUID.randomUUID();

        assertTrue(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.placeEventFiringBlock(block1);

        assertFalse(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.placeEventFiringBlock(block2);
        gameSaveBlocker.releaseEventFiringBlock(block1);

        assertFalse(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.releaseEventFiringBlock(block2);

        assertTrue(gameSaveBlocker.canSaveGame());
    }

    @Test
    public void testPlaceAndReleaseTriggeredEventBlockWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                gameSaveBlocker.placeEventFiringBlock(null));
        assertThrows(IllegalArgumentException.class, () ->
                gameSaveBlocker.releaseEventFiringBlock(null));
    }

    @Test
    public void testPlaceAndReleaseManualBlockAndCanSaveGame() {
        assertTrue(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.placeManualBlock();

        assertFalse(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.releaseManualBlock();

        assertTrue(gameSaveBlocker.canSaveGame());
    }

    @Test
    public void testPlaceAndReleaseManualAndTriggeredEventBlocksAndCanSaveGame() {
        UUID block = UUID.randomUUID();

        assertTrue(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.placeEventFiringBlock(block);

        assertFalse(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.placeManualBlock();

        assertFalse(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.releaseEventFiringBlock(block);

        assertFalse(gameSaveBlocker.canSaveGame());

        gameSaveBlocker.releaseManualBlock();

        assertTrue(gameSaveBlocker.canSaveGame());
    }
}
