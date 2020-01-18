package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameZoneFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.GameZoneFactory;
import soliloquy.specs.gamestate.factories.TileFactory;

import static org.junit.jupiter.api.Assertions.*;

class GameZoneFactoryImplTests {
    private final TileFactory TILE_FACTORY = new TileFactoryStub();
    private final CoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final VariableCacheFactory DATA_FACTORY = new VariableCacheFactoryStub();
    private final String ID = "GameZoneId";
    private final String NAME = "GameZoneName";
    private final String TYPE = "GameZoneType";
    private final int MAX_COORDINATE_VALUE = 5;
    private final ReadableCoordinate MAX_COORDINATES =
            new ReadableCoordinateStub(MAX_COORDINATE_VALUE, MAX_COORDINATE_VALUE);
    private final VariableCache DATA = new VariableCacheStub();

    private GameZoneFactory _gameZoneFactory;

    @BeforeEach
    void setUp() {
        _gameZoneFactory = new GameZoneFactoryImpl(TILE_FACTORY, COORDINATE_FACTORY,
                COLLECTION_FACTORY, DATA_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(null, COORDINATE_FACTORY, COLLECTION_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(TILE_FACTORY, null, COLLECTION_FACTORY,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(TILE_FACTORY, COORDINATE_FACTORY, null,
                        DATA_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneFactoryImpl(TILE_FACTORY, COORDINATE_FACTORY, COLLECTION_FACTORY,
                        null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameZoneFactory.class.getCanonicalName(),
                _gameZoneFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        GameZone gameZone = _gameZoneFactory.make(ID, NAME, TYPE, MAX_COORDINATES, null);

        assertNotNull(gameZone);
        assertEquals(ID, gameZone.id());
        assertEquals(NAME, gameZone.getName());
        assertEquals(TYPE, gameZone.type());
        assertEquals(MAX_COORDINATE_VALUE, gameZone.getMaxCoordinates().getX());
        assertEquals(MAX_COORDINATE_VALUE, gameZone.getMaxCoordinates().getY());
        for (int x = 0; x <= MAX_COORDINATE_VALUE; x++) {
            for (int y = 0; y <= MAX_COORDINATE_VALUE; y++) {
                Tile tile = gameZone.tile(new ReadableCoordinateStub(x,y));
                assertNotNull(tile);
                assertEquals(x, tile.location().getX());
                assertEquals(y, tile.location().getY());
            }
        }
        assertNotNull(gameZone.onEntry());
        assertNotNull(gameZone.onExit());
        assertSame(((VariableCacheFactoryStub)DATA_FACTORY)._mostRecentlyCreated, gameZone.data());
    }

    @Test
    void testMakeWithData() {
        GameZone gameZone = _gameZoneFactory.make(ID, NAME, TYPE, MAX_COORDINATES, DATA);

        assertNotNull(gameZone);
        assertEquals(ID, gameZone.id());
        assertEquals(NAME, gameZone.getName());
        assertEquals(TYPE, gameZone.type());
        assertEquals(MAX_COORDINATE_VALUE, gameZone.getMaxCoordinates().getX());
        assertEquals(MAX_COORDINATE_VALUE, gameZone.getMaxCoordinates().getY());
        for (int x = 0; x <= MAX_COORDINATE_VALUE; x++) {
            for (int y = 0; y <= MAX_COORDINATE_VALUE; y++) {
                Tile tile = gameZone.tile(new ReadableCoordinateStub(x,y));
                assertNotNull(tile);
                assertEquals(x, tile.location().getX());
                assertEquals(y, tile.location().getY());
            }
        }
        assertNotNull(gameZone.onEntry());
        assertNotNull(gameZone.onExit());
        assertSame(DATA, gameZone.data());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _gameZoneFactory.make(null, NAME, TYPE, MAX_COORDINATES, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _gameZoneFactory.make("", NAME, TYPE, MAX_COORDINATES, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _gameZoneFactory.make(ID, null, TYPE, MAX_COORDINATES, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _gameZoneFactory.make(ID, "", TYPE, MAX_COORDINATES, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _gameZoneFactory.make(ID, NAME, TYPE, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _gameZoneFactory.make(ID, NAME, TYPE, new ReadableCoordinateStub(-1,0),
                        DATA));
        assertThrows(IllegalArgumentException.class,
                () -> _gameZoneFactory.make(ID, NAME, TYPE, new ReadableCoordinateStub(0,-1),
                        DATA));
    }
}
