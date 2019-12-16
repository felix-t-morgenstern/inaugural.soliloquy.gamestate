package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.*;

import static org.junit.jupiter.api.Assertions.*;

class TileFactoryImplTests {
    private final GameZone GAME_ZONE = new GameZoneStub();
    private final ReadableCoordinate LOCATION = new CoordinateStub();
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY = new TileEntitiesFactoryStub();
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY =
            new TileWallSegmentsFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();

    private TileFactory _tileFactory;

    @BeforeEach
    void setUp() {
        _tileFactory = new TileFactoryImpl(TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY,
                COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(null, TILE_WALL_SEGMENTS_FACTORY,
                        COLLECTION_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(TILE_ENTITIES_FACTORY, null,
                        COLLECTION_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFactoryImpl(TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFactory.class.getCanonicalName(), _tileFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Tile tile = _tileFactory.make(GAME_ZONE, LOCATION, DATA);
        ((GameZoneStub) GAME_ZONE).RETURN_ACTUAL_TILE_AT_LOCATION = true;
        ((GameZoneStub) GAME_ZONE).TILES[LOCATION.getX()][LOCATION.getY()] = tile;

        assertNotNull(tile);
        assertSame(GAME_ZONE, tile.gameZone());
        assertEquals(LOCATION.getX(), tile.location().getX());
        assertEquals(LOCATION.getY(), tile.location().getY());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tileFactory.make(null, LOCATION, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _tileFactory.make(GAME_ZONE, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _tileFactory.make(GAME_ZONE, LOCATION, null));
    }
}
