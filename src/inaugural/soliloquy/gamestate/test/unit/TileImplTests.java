package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileCharactersFactory;
import soliloquy.specs.gamestate.factories.TileFixturesFactory;
import soliloquy.specs.gamestate.factories.TileItemsFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;

import static org.junit.jupiter.api.Assertions.*;

class TileImplTests {
    private final int X = 123;
    private final int Y = 123;

    private final GameZone GAME_ZONE = new GameZoneStub();
    private final Coordinate LOCATION = new CoordinateStub(X, Y);
    private final TileCharactersFactory TILE_CHARACTERS_FACTORY = new TileCharactersFactoryStub();
    private final TileItemsFactory TILE_ITEMS_FACTORY = new TileItemsFactoryStub();
    private final TileFixturesFactory TILE_FIXTURES_FACTORY = new TileFixturesFactoryStub();
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY =
            new TileWallSegmentsFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();

    private Tile _tile;

    @BeforeEach
    void setUp() {
        _tile = new TileImpl(GAME_ZONE, LOCATION, TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY);
        ((GameZoneStub) GAME_ZONE).TILES[LOCATION.getX()][LOCATION.getY()] = _tile;
        ((GameZoneStub) GAME_ZONE).RETURN_ACTUAL_TILE_AT_LOCATION = true;
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(null, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, null,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                null, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, null, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, null,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                null, MAP_FACTORY, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, null, COLLECTION_FACTORY,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, null,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                null));
    }

    @Test
    void testGameZone() {
        assertSame(GAME_ZONE, _tile.gameZone());
    }

    @Test
    void testLocation() {
        ReadableCoordinate location = _tile.location();

        assertNotNull(location);
        assertFalse(location instanceof Coordinate);
        assertEquals(X, location.getX());
        assertEquals(Y, location.getY());
    }

    @Test
    void testSetAndGetHeight() {
        _tile.setHeight(123);

        assertEquals(123, _tile.getHeight());
    }

    @Test
    void testSetAndGetGroundType() {
        GroundType groundType = new GroundTypeStub();

        _tile.setGroundType(groundType);

        assertSame(groundType, _tile.getGroundType());
    }

    @Test
    void testCharacters() {
        assertNotNull(_tile.characters());
        assertSame(_tile, ((TileCharactersStub)_tile.characters()).TILE);
    }

    @Test
    void testItems() {
        assertNotNull(_tile.items());
        assertSame(_tile, ((TileItemsStub)_tile.items()).TILE);
    }

    @Test
    void testFixtures() {
        assertNotNull(_tile.fixtures());
        assertSame(_tile, ((TileFixturesStub)_tile.fixtures()).TILE);
    }

    @Test
    void testWallSegments() {
        assertNotNull(_tile.wallSegments());
        assertSame(_tile, ((TileWallSegmentsStub)_tile.wallSegments()).TILE);
    }

    @Test
    void testMovementEvents() {
        assertNotNull(_tile.movementEvents());
        assertEquals(GameMovementEvent.class.getCanonicalName(),
                _tile.movementEvents().getArchetype().getInterfaceName());
    }

    @Test
    void testAbilityEvents() {
        assertNotNull(_tile.abilityEvents());
        assertEquals(GameAbilityEvent.class.getCanonicalName(),
                _tile.abilityEvents().getArchetype().getInterfaceName());
    }

    @Test
    void testSprites() {
        assertNotNull(_tile.sprites());
        assertNotNull(_tile.sprites().getFirstArchetype());
        assertNotNull(_tile.sprites().getSecondArchetype());
        assertNotNull(_tile.sprites().getSecondArchetype().getArchetype());
    }

    @Test
    void testData() {
        assertNotNull(_tile.data());
    }

    @Test
    void testMakeGameEventTarget() {
        GameEventTarget gameEventTarget = _tile.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNotNull(gameEventTarget.tile());
        assertNull(gameEventTarget.tileFixture());
        assertEquals(GameEventTarget.class.getCanonicalName(), gameEventTarget.getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Tile.class.getCanonicalName(), _tile.getInterfaceName());
    }

    @Test
    void testThrowsOnDeleteWhenGameZoneIsNotDeleted() {
        assertThrows(IllegalStateException.class, _tile::delete);
    }

    @Test
    void testDeletedInvariant() {
        GAME_ZONE.delete();

        assertThrows(IllegalStateException.class, () -> _tile.gameZone());
        assertThrows(IllegalStateException.class, () -> _tile.location());
        assertThrows(IllegalStateException.class, () -> _tile.getHeight());
        assertThrows(IllegalStateException.class, () -> _tile.setHeight(0));
        assertThrows(IllegalStateException.class, () -> _tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> _tile.setGroundType(new GroundTypeStub()));
        assertThrows(IllegalStateException.class, () -> _tile.characters());
        assertThrows(IllegalStateException.class, () -> _tile.fixtures());
        assertThrows(IllegalStateException.class, () -> _tile.items());
        assertThrows(IllegalStateException.class, () -> _tile.wallSegments());
        assertThrows(IllegalStateException.class, () -> _tile.movementEvents());
        assertThrows(IllegalStateException.class, () -> _tile.abilityEvents());
        assertThrows(IllegalStateException.class, () -> _tile.sprites());
        assertThrows(IllegalStateException.class, () -> _tile.data());
    }

    @Test
    void testGameZoneLocationCorrespondenceInvariant() {
        ((GameZoneStub)GAME_ZONE).TILES[LOCATION.getX()][LOCATION.getY()] = null;
        ((GameZoneStub)GAME_ZONE).TILES[LOCATION.getX()][LOCATION.getY()+1] = _tile;

        assertThrows(IllegalStateException.class, () -> _tile.gameZone());
        assertThrows(IllegalStateException.class, () -> _tile.location());
        assertThrows(IllegalStateException.class, () -> _tile.getHeight());
        assertThrows(IllegalStateException.class, () -> _tile.setHeight(0));
        assertThrows(IllegalStateException.class, () -> _tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> _tile.setGroundType(new GroundTypeStub()));
        assertThrows(IllegalStateException.class, () -> _tile.characters());
        assertThrows(IllegalStateException.class, () -> _tile.fixtures());
        assertThrows(IllegalStateException.class, () -> _tile.items());
        assertThrows(IllegalStateException.class, () -> _tile.wallSegments());
        assertThrows(IllegalStateException.class, () -> _tile.movementEvents());
        assertThrows(IllegalStateException.class, () -> _tile.abilityEvents());
        assertThrows(IllegalStateException.class, () -> _tile.sprites());
        assertThrows(IllegalStateException.class, () -> _tile.data());
    }
}