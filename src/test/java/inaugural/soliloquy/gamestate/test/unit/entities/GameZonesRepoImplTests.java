package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.GameZonesRepoImpl;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeGameZoneHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZonesRepo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// TODO: Touch up this suite
// NB: This suite still uses the Jupiter API, since the JUnit version which supports @TempDir
//     (5.4.x) is not yet available on Maven central repo.
public class GameZonesRepoImplTests {
    private final TypeHandler<GameZone> GAME_ZONE_HANDLER =
            new FakeGameZoneHandler();
    private final Map<String, Path> FILE_LOCATIONS = mapOf();
    private final String DIRECTORY_NAME = randomString();
    private final String TEMP_FILE_RELATIVE_LOC =
            String.format("%s\\%s.txt", DIRECTORY_NAME, randomString());
    private final String ORIGINAL_FILE_TEXT = randomString();

    private GameZone mockGameZone;

    private GameZonesRepo gameZonesRepo;

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
        mockGameZone = mock(GameZone.class);
        when(mockGameZone.id()).thenReturn(randomString());
        FILE_LOCATIONS.put(mockGameZone.id(), sharedTempFilePath);

        gameZonesRepo = new GameZonesRepoImpl(GAME_ZONE_HANDLER, FILE_LOCATIONS);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZonesRepoImpl(null, FILE_LOCATIONS));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZonesRepoImpl(GAME_ZONE_HANDLER, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(GameZonesRepo.class.getCanonicalName(), gameZonesRepo.getInterfaceName());
    }

    @Test
    public void testGetGameZone() {
        var gameZone = gameZonesRepo.getGameZone(mockGameZone.id());

        assertEquals(ORIGINAL_FILE_TEXT,
                ((FakeGameZoneHandler) GAME_ZONE_HANDLER).READ_INPUTS.get(0));
        assertSame(((FakeGameZoneHandler) GAME_ZONE_HANDLER).READ_OUTPUTS.get(0),
                gameZone);
    }

    @Test
    public void testGetGameZoneWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> gameZonesRepo.getGameZone(null));
        assertThrows(IllegalArgumentException.class, () -> gameZonesRepo.getGameZone(""));
        assertThrows(IllegalArgumentException.class, () -> gameZonesRepo.getGameZone("invalid"));
    }

    @Test
    public void testSaveGameZone() {
        gameZonesRepo.saveGameZone(mockGameZone);

        assertEquals(mockGameZone,
                ((FakeGameZoneHandler) GAME_ZONE_HANDLER).WRITE_INPUTS.get(0));
        try {
            assertEquals(((FakeGameZoneHandler) GAME_ZONE_HANDLER).WRITE_OUTPUTS.get(0),
                    new String(Files.readAllBytes(sharedTempDir.resolve(TEMP_FILE_RELATIVE_LOC))));
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSaveGameZoneWithInvalidParameters() {
        when(mockGameZone.id()).thenReturn("InvalidId");

        assertThrows(IllegalArgumentException.class, () -> gameZonesRepo.saveGameZone(null));
        assertThrows(IllegalArgumentException.class,
                () -> gameZonesRepo.saveGameZone(mockGameZone));
    }
}
