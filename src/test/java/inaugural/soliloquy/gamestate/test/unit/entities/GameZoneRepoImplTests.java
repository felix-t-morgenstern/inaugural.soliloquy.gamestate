package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.GameZoneRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZoneRepo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameZoneRepoImplTests {
    private final Map<String, Path> FILE_LOCATIONS = mapOf();
    private final String DIRECTORY_NAME = randomString();
    private final String TEMP_FILE_RELATIVE_LOC =
            String.format("%s\\%s.txt", DIRECTORY_NAME, randomString());
    private final String ORIGINAL_FILE_TEXT = randomString();
    private final String HANDLER_OUTPUT = randomString();
    private final String INVALID_ID = "invalid";
    private final String GAME_ZONE_ID = randomString();

    @Mock private TypeHandler<GameZone> mockGameZoneHandler;
    @Mock private GameZone mockGameZone;

    private GameZoneRepo gameZoneRepo;

    @SuppressWarnings("WeakerAccess")
    @TempDir
    static Path sharedTempDir;

    @BeforeEach
    public void setUp() throws Exception {
        var sharedTempDirPath = sharedTempDir.resolve(DIRECTORY_NAME);
        //noinspection ResultOfMethodCallIgnored
        new File(sharedTempDirPath.toString()).mkdir();
        var sharedTempFilePath = sharedTempDir.resolve(TEMP_FILE_RELATIVE_LOC);
        Files.write(sharedTempFilePath, listOf());
        Files.writeString(sharedTempFilePath, ORIGINAL_FILE_TEXT,
                StandardOpenOption.APPEND);

        lenient().when(mockGameZone.id()).thenReturn(GAME_ZONE_ID);

        lenient().when(mockGameZoneHandler.write(any())).thenReturn(HANDLER_OUTPUT);
        lenient().when(mockGameZoneHandler.read(anyString())).thenReturn(mockGameZone);

        FILE_LOCATIONS.put(GAME_ZONE_ID, sharedTempFilePath);

        gameZoneRepo = new GameZoneRepoImpl(mockGameZoneHandler, FILE_LOCATIONS);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneRepoImpl(null, FILE_LOCATIONS));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneRepoImpl(mockGameZoneHandler, null));
    }

    @Test
    public void testCurrentGameZoneNullBeforeLoading() {
        assertNull(gameZoneRepo.currentGameZone());
    }

    @Test
    public void testLoadGameZoneAndCurrentGameZone() {
        gameZoneRepo.loadGameZone(GAME_ZONE_ID);
        var currentGameZone = gameZoneRepo.currentGameZone();

        assertSame(mockGameZone, currentGameZone);
        verify(mockGameZoneHandler, once()).read(ORIGINAL_FILE_TEXT);
    }

    @Test
    public void testLoadGameZoneWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> gameZoneRepo.loadGameZone(null));
        assertThrows(IllegalArgumentException.class, () -> gameZoneRepo.loadGameZone(""));
        assertThrows(IllegalArgumentException.class, () -> gameZoneRepo.loadGameZone(INVALID_ID));
    }

    @Test
    public void testSaveGameZone() {
        gameZoneRepo.saveGameZone(mockGameZone);

        verify(mockGameZoneHandler, once()).write(mockGameZone);
        try {
            assertEquals(HANDLER_OUTPUT,
                    new String(Files.readAllBytes(sharedTempDir.resolve(TEMP_FILE_RELATIVE_LOC))));
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSaveGameZoneWithInvalidArgs() {
        when(mockGameZone.id()).thenReturn(INVALID_ID);

        assertThrows(IllegalArgumentException.class, () -> gameZoneRepo.saveGameZone(null));
        assertThrows(IllegalArgumentException.class,
                () -> gameZoneRepo.saveGameZone(mockGameZone));
    }

    @Test
    public void testUnloadGameZone() {
        gameZoneRepo.loadGameZone(GAME_ZONE_ID);

        gameZoneRepo.unloadGameZone();
        var currentGameZone = gameZoneRepo.currentGameZone();

        assertNull(currentGameZone);
    }

    @Test
    public void testLoadGameZoneWhenAlreadyLoaded() {
        gameZoneRepo.loadGameZone(GAME_ZONE_ID);

        assertThrows(UnsupportedOperationException.class,
                () -> gameZoneRepo.loadGameZone(GAME_ZONE_ID));
    }
}
