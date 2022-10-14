package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.CameraFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.spydoubles.TileVisibilitySpyDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import static org.junit.jupiter.api.Assertions.*;

class CameraFactoryImplTests {
    private final TileVisibility TILE_VISIBILITY = new TileVisibilitySpyDouble();
    private final FakeGameZone GAME_ZONE = new FakeGameZone();

    private CameraFactory _cameraFactory;

    @BeforeEach
    void setUp() {
        _cameraFactory = new CameraFactoryImpl(TILE_VISIBILITY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CameraFactoryImpl(null));
    }

    @Test
    void testMake() {
        Camera camera = _cameraFactory.make(() -> GAME_ZONE);

        assertNotNull(camera);

        camera.setTileLocation(Coordinate.of(1, 1));
        camera.setTileRenderingRadius(1);
        camera.coordinatesProvidingVisibility().put(Coordinate.of(1, 1), 1);
        camera.calculateVisibleTiles();

        assertEquals(1, camera.visibleTiles().size());
        Coordinate visibleTileLocation = camera.visibleTiles().get(0);
        assertSame(GAME_ZONE.TILES[1][1],
                GAME_ZONE.TILES[visibleTileLocation.x()][visibleTileLocation.y()]);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _cameraFactory.make(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CameraFactory.class.getCanonicalName(), _cameraFactory.getInterfaceName());
    }
}
