package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;

import static org.junit.jupiter.api.Assertions.*;

class TileFactoryImplTests {
    private final GameZone GAME_ZONE = new GameZoneStub();
    private final int X = 123;
    private final int Y = 456;
    private final CoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY = new TileEntitiesFactoryStub();
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY =
            new TileWallSegmentsFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final VariableCache DATA = new VariableCacheStub();

    private TileFactory _tileFactory;

    @BeforeEach
    void setUp() {
        _tileFactory = new TileFactoryImpl(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, COLLECTION_FACTORY, MAP_FACTORY);
        ((GameZoneStub) GAME_ZONE).TILES = new Tile[999][999];
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(null, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, COLLECTION_FACTORY, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(COORDINATE_FACTORY, null,
                        TILE_WALL_SEGMENTS_FACTORY, COLLECTION_FACTORY, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        null, COLLECTION_FACTORY, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                        TILE_WALL_SEGMENTS_FACTORY, COLLECTION_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFactory.class.getCanonicalName(), _tileFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Tile tile = _tileFactory.make(GAME_ZONE, X, Y, DATA);
        ((GameZoneStub) GAME_ZONE).RETURN_ACTUAL_TILE_AT_LOCATION = true;
        ((GameZoneStub) GAME_ZONE).TILES[X][Y] = tile;

        assertNotNull(tile);
        assertSame(GAME_ZONE, tile.gameZone());
        assertEquals(X, tile.location().getX());
        assertEquals(Y, tile.location().getY());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileFactory.make(null, X, Y, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _tileFactory.make(GAME_ZONE, X, Y, null));
    }
}
