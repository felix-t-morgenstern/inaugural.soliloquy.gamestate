package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameZonesRepoImpl;
import inaugural.soliloquy.gamestate.test.stubs.GameZoneStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentGameZoneHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.GameZonesRepo;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameZonesRepoImplTests {
    private final GameZone GAME_ZONE = new GameZoneStub();
    private final PersistentValueTypeHandler<GameZone> GAME_ZONE_HANDLER =
            new PersistentGameZoneHandlerStub();
    private final HashMap<String, Path> FILE_LOCATIONS = new HashMap<>();
    private final String DIRECTORY_NAME = "gameZone1";
    private final String TEMP_FILE_RELATIVE_LOC = DIRECTORY_NAME + "\\sharedTempFile.txt";
    private final String ORIGINAL_FILE_TEXT = "This is a fake GameZone.";

    private GameZonesRepo _gameZonesRepo;

    @SuppressWarnings("WeakerAccess")
    @TempDir
    static Path sharedTempDir;

    @BeforeEach
    void setUp() throws Exception {
        Path sharedTempDirPath = sharedTempDir.resolve(DIRECTORY_NAME);
        new File(sharedTempDirPath.toString()).mkdir();
        Path sharedTempFilePath = sharedTempDir.resolve(TEMP_FILE_RELATIVE_LOC);
        Files.write(sharedTempFilePath, new ArrayList<>());
        Files.write(sharedTempFilePath, ORIGINAL_FILE_TEXT.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND);
        FILE_LOCATIONS.put(GAME_ZONE.id(), sharedTempFilePath);

        _gameZonesRepo = new GameZonesRepoImpl(GAME_ZONE_HANDLER, FILE_LOCATIONS);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZonesRepoImpl(null, FILE_LOCATIONS));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZonesRepoImpl(GAME_ZONE_HANDLER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameZonesRepo.class.getCanonicalName(), _gameZonesRepo.getInterfaceName());
    }

    @Test
    void testGetGameZone() {
        GameZone gameZone = _gameZonesRepo.getGameZone(GAME_ZONE.id());

        assertEquals(ORIGINAL_FILE_TEXT,
                ((PersistentGameZoneHandlerStub) GAME_ZONE_HANDLER).READ_INPUTS.get(0));
        assertSame(((PersistentGameZoneHandlerStub) GAME_ZONE_HANDLER).READ_OUTPUTS.get(0),
                gameZone);
    }

    @Test
    void testGetGameZoneWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> _gameZonesRepo.getGameZone(null));
        assertThrows(IllegalArgumentException.class, () -> _gameZonesRepo.getGameZone(""));
        assertThrows(IllegalArgumentException.class, () -> _gameZonesRepo.getGameZone("invalid"));
    }

    @Test
    void testSaveGameZone() {
        _gameZonesRepo.saveGameZone(GAME_ZONE);

        assertEquals(GAME_ZONE,
                ((PersistentGameZoneHandlerStub) GAME_ZONE_HANDLER).WRITE_INPUTS.get(0));
        try {
            assertEquals(((PersistentGameZoneHandlerStub) GAME_ZONE_HANDLER).WRITE_OUTPUTS.get(0),
                new String(Files.readAllBytes(sharedTempDir.resolve(TEMP_FILE_RELATIVE_LOC))));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testSaveGameZoneWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> _gameZonesRepo.saveGameZone(null));
        assertThrows(IllegalArgumentException.class,
                () -> _gameZonesRepo.saveGameZone(new GameZoneStub("InvalidId")));
    }
}
