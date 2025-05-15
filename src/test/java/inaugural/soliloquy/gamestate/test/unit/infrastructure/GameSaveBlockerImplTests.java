package inaugural.soliloquy.gamestate.test.unit.infrastructure;

import inaugural.soliloquy.gamestate.infrastructure.GameSaveBlockerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.infrastructure.GameSaveBlocker;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class GameSaveBlockerImplTests {
    private GameSaveBlocker gameSaveBlocker;

    @BeforeEach
    public void setUp() {
        gameSaveBlocker = new GameSaveBlockerImpl();
    }

    @Test
    public void testPlaceAndReleaseTriggeredEventBlockAndCanSaveGame() {
        var block1 = UUID.randomUUID();
        var block2 = UUID.randomUUID();

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
    public void testPlaceAndReleaseTriggeredEventBlockWithInvalidArgs() {
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
        var block = UUID.randomUUID();

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
