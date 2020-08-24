package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.TileImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

import static org.junit.jupiter.api.Assertions.*;

class TileImplTests {
    private final int X = 123;
    private final int Y = 456;

    private final GameZone GAME_ZONE = new FakeGameZone();
    private final CoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY = new FakeTileEntitiesFactory();
    private final TileWallSegmentsFactory TILE_WALL_SEGMENTS_FACTORY =
            new FakeTileWallSegmentsFactory();
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private final FakeMapFactory MAP_FACTORY = new FakeMapFactory();
    private final VariableCache DATA = new VariableCacheStub();

    private Tile _tile;

    @BeforeEach
    void setUp() {
        _tile = new TileImpl(X, Y, COORDINATE_FACTORY, TILE_ENTITIES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, COLLECTION_FACTORY, MAP_FACTORY, DATA);
        ((FakeGameZone) GAME_ZONE).TILES = new Tile[999][999];
        ((FakeGameZone) GAME_ZONE).RETURN_ACTUAL_TILE_AT_LOCATION = true;
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(X, Y,
                null, TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY,
                COLLECTION_FACTORY, MAP_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(X, Y,
                COORDINATE_FACTORY, null, TILE_WALL_SEGMENTS_FACTORY,
                COLLECTION_FACTORY, MAP_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(X, Y,
                COORDINATE_FACTORY, TILE_ENTITIES_FACTORY, null,
                COLLECTION_FACTORY, MAP_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(X, Y,
                COORDINATE_FACTORY, TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY,
                null, MAP_FACTORY, DATA));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(X, Y,
                COORDINATE_FACTORY, TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY,
                COLLECTION_FACTORY, null, DATA));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(X, Y,
                COORDINATE_FACTORY, TILE_ENTITIES_FACTORY, TILE_WALL_SEGMENTS_FACTORY,
                COLLECTION_FACTORY, MAP_FACTORY, null));
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
        GroundType groundType = new FakeGroundType();

        _tile.setGroundType(groundType);

        assertSame(groundType, _tile.getGroundType());
    }

    @Test
    void testCharacters() {
        assertNotNull(_tile.characters());
        assertSame(_tile, ((FakeTileEntities)_tile.characters()).TILE);
    }

    @Test
    void testItems() {
        assertNotNull(_tile.items());
        assertSame(_tile, ((FakeTileEntities<Item>)_tile.items()).TILE);
    }

    @Test
    void testFixtures() {
        assertNotNull(_tile.fixtures());
        assertSame(_tile, ((FakeTileEntities<TileFixture>)_tile.fixtures()).TILE);
    }

    @Test
    void testWallSegments() {
        assertNotNull(_tile.wallSegments());
        assertSame(_tile, ((FakeTileWallSegments)_tile.wallSegments()).TILE);
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
        assertEquals(Sprite.class.getCanonicalName(),
                _tile.sprites().getFirstArchetype().getInterfaceName());
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
    void testAssignGameZoneAfterAddedToGameZone() {
        ((FakeGameZone)GAME_ZONE).TILES[X][Y] = _tile;
        _tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertSame(GAME_ZONE, _tile.gameZone());
    }

    @Test
    void testAssignGameZoneAfterAddedToGameZoneWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _tile.assignGameZoneAfterAddedToGameZone(null));
    }

    @Test
    void testAssignGameZoneAfterAddedToGameZoneWhenAlreadyAssigned() {
        _tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertThrows(IllegalArgumentException.class,
                () -> _tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Tile.class.getCanonicalName(), _tile.getInterfaceName());
    }

    @Test
    void testThrowsOnDeleteWhenGameZoneIsNotDeleted() {
        _tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertThrows(IllegalStateException.class, _tile::delete);
    }

    @Test
    void testDeletedInvariant() {
        ((FakeGameZone)GAME_ZONE).TILES[X][Y] = _tile;
        _tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        GAME_ZONE.delete();

        assertThrows(EntityDeletedException.class, () -> _tile.gameZone());
        assertThrows(EntityDeletedException.class, () -> _tile.location());
        assertThrows(EntityDeletedException.class, () -> _tile.getHeight());
        assertThrows(EntityDeletedException.class, () -> _tile.setHeight(0));
        assertThrows(EntityDeletedException.class, () -> _tile.getGroundType());
        assertThrows(EntityDeletedException.class, () -> _tile.setGroundType(new FakeGroundType()));
        assertThrows(EntityDeletedException.class, () -> _tile.characters());
        assertThrows(EntityDeletedException.class, () -> _tile.fixtures());
        assertThrows(EntityDeletedException.class, () -> _tile.items());
        assertThrows(EntityDeletedException.class, () -> _tile.wallSegments());
        assertThrows(EntityDeletedException.class, () -> _tile.movementEvents());
        assertThrows(EntityDeletedException.class, () -> _tile.abilityEvents());
        assertThrows(EntityDeletedException.class, () -> _tile.sprites());
        assertThrows(EntityDeletedException.class, () -> _tile.data());
    }

    @Test
    void testGameZoneLocationCorrespondenceInvariant() {
        _tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        ((FakeGameZone)GAME_ZONE).TILES[X][Y] = null;
        ((FakeGameZone)GAME_ZONE).TILES[X][Y+1] = _tile;

        assertThrows(IllegalStateException.class, () -> _tile.gameZone());
        assertThrows(IllegalStateException.class, () -> _tile.location());
        assertThrows(IllegalStateException.class, () -> _tile.getHeight());
        assertThrows(IllegalStateException.class, () -> _tile.setHeight(0));
        assertThrows(IllegalStateException.class, () -> _tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> _tile.setGroundType(new FakeGroundType()));
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
    void testGameZoneMismatchInvariant() {
        _tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertThrows(IllegalStateException.class, () -> _tile.gameZone());
        assertThrows(IllegalStateException.class, () -> _tile.location());
        assertThrows(IllegalStateException.class, () -> _tile.getHeight());
        assertThrows(IllegalStateException.class, () -> _tile.setHeight(0));
        assertThrows(IllegalStateException.class, () -> _tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> _tile.setGroundType(new FakeGroundType()));
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
