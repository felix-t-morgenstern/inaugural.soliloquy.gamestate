package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.TileFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameZone;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileEntitiesFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeTileWallSegmentsFactory;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFactoryImplTests {
    private final GameZone GAME_ZONE = new FakeGameZone();
    private final int X = 123;
    private final int Y = 456;
    private final CoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY = new FakeTileEntitiesFactory();
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY =
            new FakeTileWallSegmentsFactory();
    private final VariableCache DATA = new VariableCacheStub();

    private TileFactory _tileFactory;

    @BeforeEach
    void setUp() {
        _tileFactory = new TileFactoryImpl(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY);
        ((FakeGameZone) GAME_ZONE).TILES = new Tile[999][999];
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(null, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(COORDINATE_FACTORY, null,
                        TILE_WALL_SEGMENTS_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFactory.class.getCanonicalName(), _tileFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Tile tile = _tileFactory.make(X, Y, DATA);

        assertNotNull(tile);
        assertEquals(X, tile.location().getX());
        assertEquals(Y, tile.location().getY());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileFactory.make(-1, Y, DATA));
        assertThrows(IllegalArgumentException.class, () -> _tileFactory.make(X, -1, DATA));
        assertThrows(IllegalArgumentException.class, () -> _tileFactory.make(X, Y, null));
    }
}
