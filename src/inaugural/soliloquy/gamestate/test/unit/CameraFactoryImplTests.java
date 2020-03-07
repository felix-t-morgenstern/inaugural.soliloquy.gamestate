package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.CameraFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.spydoubles.TileVisibilitySpyDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.Camera;
import soliloquy.specs.gamestate.factories.CameraFactory;
import soliloquy.specs.ruleset.gameconcepts.TileVisibility;

import static org.junit.jupiter.api.Assertions.*;

class CameraFactoryImplTests {
    private final CoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();
    private final TileVisibility TILE_VISIBILITY = new TileVisibilitySpyDouble();
    private final FakeGameZone GAME_ZONE = new FakeGameZone();

    private CameraFactory _cameraFactory;

    @BeforeEach
    void setUp() {
        _cameraFactory = new CameraFactoryImpl(COORDINATE_FACTORY, COLLECTION_FACTORY, MAP_FACTORY,
                TILE_VISIBILITY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CameraFactoryImpl(null, COLLECTION_FACTORY, MAP_FACTORY,
                        TILE_VISIBILITY));
        assertThrows(IllegalArgumentException.class,
                () -> new CameraFactoryImpl(COORDINATE_FACTORY, null, MAP_FACTORY,
                        TILE_VISIBILITY));
        assertThrows(IllegalArgumentException.class,
                () -> new CameraFactoryImpl(COORDINATE_FACTORY, COLLECTION_FACTORY, null,
                        TILE_VISIBILITY));
        assertThrows(IllegalArgumentException.class,
                () -> new CameraFactoryImpl(COORDINATE_FACTORY, COLLECTION_FACTORY, MAP_FACTORY,
                        null));
    }

    @Test
    void testMake() {
        Camera camera = _cameraFactory.make(() -> GAME_ZONE);

        assertNotNull(camera);
        camera.setTileLocation(1,1);
        camera.setTileRenderingRadius(1);
        camera.coordinatesProvidingVisibility().put(new FakeCoordinate(1,1), 1);
        camera.calculateVisibleTiles();
        assertEquals(1, camera.visibleTiles().size());
        ReadableCoordinate visibleTileLocation = camera.visibleTiles().get(0);
        assertSame(GAME_ZONE.TILES[1][1],
                GAME_ZONE.TILES[visibleTileLocation.getX()][visibleTileLocation.getY()]);
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
