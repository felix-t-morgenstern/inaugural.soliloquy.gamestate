package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.*;

import static org.junit.jupiter.api.Assertions.*;

class TileFactoryImplTests {
    private final GameZone GAME_ZONE = new GameZoneStub();
    private final ReadableCoordinate LOCATION = new CoordinateStub();
    private final TileCharactersFactory TILE_CHARACTERS_FACTORY = new TileCharactersFactoryStub();
    private final TileItemsFactory TILE_ITEMS_FACTORY = new TileItemsFactoryStub();
    private final TileFixturesFactory TILE_FIXTURES_FACTORY = new TileFixturesFactoryStub();
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY =
            new TileWallSegmentsFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();

    private TileFactory _tileFactory;

    @BeforeEach
    void setUp() {
        _tileFactory = new TileFactoryImpl(TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new TileFactoryImpl(null, TILE_ITEMS_FACTORY,
                        TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new TileFactoryImpl(TILE_CHARACTERS_FACTORY, null,
                        TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new TileFactoryImpl(TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                        null, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new TileFactoryImpl(TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                        TILE_FIXTURES_FACTORY, null, MAP_FACTORY, COLLECTION_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new TileFactoryImpl(TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                        TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, null, COLLECTION_FACTORY,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new TileFactoryImpl(TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                        TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, null,
                        GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () ->
                new TileFactoryImpl(TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                        TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TileFactory.class.getCanonicalName(), _tileFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Tile tile = _tileFactory.make(GAME_ZONE, LOCATION);
        ((GameZoneStub) GAME_ZONE).RETURN_ACTUAL_TILE_AT_LOCATION = true;
        ((GameZoneStub) GAME_ZONE).TILES[LOCATION.getX()][LOCATION.getY()] = tile;

        assertNotNull(tile);
        assertSame(GAME_ZONE, tile.gameZone());
        assertEquals(LOCATION.getX(), tile.location().getX());
        assertEquals(LOCATION.getY(), tile.location().getY());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _tileFactory.make(GAME_ZONE, null));
        assertThrows(IllegalArgumentException.class, () -> _tileFactory.make(null, LOCATION));
    }
}
