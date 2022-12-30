package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.entities.CameraImpl;
import inaugural.soliloquy.gamestate.factories.CameraFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CameraFactoryImplTests {
    @Mock private TileVisibility mockTileVisibility;
    @Mock private GameZone mockGameZone;

    private CameraFactory cameraFactory;

    @BeforeEach
    void setUp() {
        mockTileVisibility = mock(TileVisibility.class);

        mockGameZone = mock(GameZone.class);

        cameraFactory = new CameraFactoryImpl(mockTileVisibility);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CameraFactoryImpl(null));
    }

    @Test
    void testMake() {
        mockGameZone = mock(GameZone.class);

        Camera camera = cameraFactory.make(() -> mockGameZone);

        assertNotNull(camera);
        assertTrue(camera instanceof CameraImpl);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> cameraFactory.make(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CameraFactory.class.getCanonicalName(), cameraFactory.getInterfaceName());
    }
}
