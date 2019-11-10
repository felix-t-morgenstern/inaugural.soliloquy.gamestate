package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.GameZoneImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.factories.TileFactory;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameZoneImplTests {
    private final String ID = "zoneId";
    private final String NAME = "zoneName";
    private final String ZONE_TYPE = "zoneType";
    private final int MAX_COORDINATE = 2;
    private final ReadableCoordinate MAX_COORDINATES =
            new CoordinateStub(MAX_COORDINATE, MAX_COORDINATE);
    private final TileFactory TILE_FACTORY = new TileFactoryStub();
    private final CoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();

    private GameZone _gameZone;

    @BeforeEach
    void setUp() {
        _gameZone = new GameZoneImpl(ID, NAME, ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                COORDINATE_FACTORY, COLLECTION_FACTORY, DATA);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(null, NAME, ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                        COORDINATE_FACTORY, COLLECTION_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl("", NAME, ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                        COORDINATE_FACTORY, COLLECTION_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, null, ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                        COORDINATE_FACTORY, COLLECTION_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, "", ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                        COORDINATE_FACTORY, COLLECTION_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, NAME, ZONE_TYPE, null, TILE_FACTORY,
                        COORDINATE_FACTORY, COLLECTION_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, NAME, ZONE_TYPE, MAX_COORDINATES, null,
                        COORDINATE_FACTORY, COLLECTION_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, NAME, ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                        null, COLLECTION_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, NAME, ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                        COORDINATE_FACTORY, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new GameZoneImpl(ID, NAME, ZONE_TYPE, MAX_COORDINATES, TILE_FACTORY,
                        COORDINATE_FACTORY, COLLECTION_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GameZone.class.getCanonicalName(), _gameZone.getInterfaceName());
    }

    @Test
    void testZoneType() {
        assertEquals(ZONE_TYPE, _gameZone.type());
    }

    @Test
    void testMaxCoordinates() {
        assertEquals(MAX_COORDINATE, _gameZone.getMaxCoordinates().getX());
        assertEquals(MAX_COORDINATE, _gameZone.getMaxCoordinates().getY());
    }

    @Test
    void testTile() {
        for(int x = 0; x <= MAX_COORDINATE; x++) {
            for(int y = 0; y <= MAX_COORDINATE; y++) {
                Tile tile = _gameZone.tile(new CoordinateStub(x,y));
                assertNotNull(tile);
                assertSame(_gameZone, tile.gameZone());
                assertEquals(x, tile.location().getX());
                assertEquals(y, tile.location().getY());
            }
        }
    }

    @Test
    void testOnEntry() {
        assertNotNull(_gameZone.onEntry());
        assertNotSame(_gameZone.onEntry(), _gameZone.onExit());
        assertEquals(Action.class.getCanonicalName() + "<" + Void.class.getCanonicalName() + ">",
                _gameZone.onEntry().getArchetype().getInterfaceName());
    }

    @Test
    void testOnExit() {
        assertNotNull(_gameZone.onExit());
        assertNotSame(_gameZone.onEntry(), _gameZone.onExit());
        assertEquals(Action.class.getCanonicalName() + "<" + Void.class.getCanonicalName() + ">",
                _gameZone.onExit().getArchetype().getInterfaceName());
    }

    @Test
    void testId() {
        assertEquals(ID, _gameZone.id());
    }

    @Test
    void testGetName() {
        assertEquals(NAME, _gameZone.getName());
    }

    @Test
    void testSetName() {
        String newName = "newName";

        _gameZone.setName(newName);

        assertEquals(newName, _gameZone.getName());
    }

    @Test
    void testSetNullOrEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> _gameZone.setName(null));
        assertThrows(IllegalArgumentException.class, () -> _gameZone.setName(""));
    }

    @Test
    void testData() {
        assertSame(DATA, _gameZone.data());
    }

    @Test
    void testDelete() {
        HashSet<Tile> tiles = new HashSet<>();
        for(int x = 0; x <= MAX_COORDINATE; x++) {
            for(int y = 0; y <= MAX_COORDINATE; y++) {
                tiles.add(_gameZone.tile(new CoordinateStub(x,y)));
            }
        }

        _gameZone.delete();

        assertTrue(_gameZone.isDeleted());
        tiles.forEach(tile -> assertTrue(tile.isDeleted()));
    }
}
