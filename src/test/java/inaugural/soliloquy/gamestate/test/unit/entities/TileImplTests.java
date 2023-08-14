package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.TileImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.GameZone;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.gamestate.factories.TileEntitiesFactory;
import soliloquy.specs.ruleset.entities.GroundType;

import static org.junit.jupiter.api.Assertions.*;

class TileImplTests {
    private final int X = 123;
    private final int Y = 456;

    private final GameZone GAME_ZONE = new FakeGameZone();
    private final TileEntitiesFactory TILE_ENTITIES_FACTORY = new FakeTileEntitiesFactory();
    private final VariableCache DATA = new VariableCacheStub();

    private Tile tile;

    @BeforeEach
    void setUp() {
        tile = new TileImpl(X, Y, TILE_ENTITIES_FACTORY, DATA);
        ((FakeGameZone) GAME_ZONE).TILES = new Tile[999][999];
        ((FakeGameZone) GAME_ZONE).RETURN_ACTUAL_TILE_AT_LOCATION = true;
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileImpl(X, Y, null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new TileImpl(X, Y, TILE_ENTITIES_FACTORY, null));
    }

    @Test
    void testLocation() {
        Coordinate location = tile.location();

        assertNotNull(location);
        assertEquals(X, location.x());
        assertEquals(Y, location.y());
    }

    @Test
    void testSetAndGetHeight() {
        tile.setHeight(123);

        assertEquals(123, tile.getHeight());
    }

    @Test
    void testSetAndGetGroundType() {
        GroundType groundType = new FakeGroundType();

        tile.setGroundType(groundType);

        assertSame(groundType, tile.getGroundType());
    }

    @Test
    void testCharacters() {
        assertNotNull(tile.characters());
        //noinspection rawtypes
        assertSame(tile, ((FakeTileEntities) tile.characters()).TILE);
    }

    @Test
    void testItems() {
        assertNotNull(tile.items());
        assertSame(tile, ((FakeTileEntities<Item>) tile.items()).TILE);
    }

    @Test
    void testFixtures() {
        assertNotNull(tile.fixtures());
        assertSame(tile, ((FakeTileEntities<TileFixture>) tile.fixtures()).TILE);
    }

    @Test
    void testMovementEvents() {
        assertNotNull(tile.movementEvents());
    }

    @Test
    void testAbilityEvents() {
        assertNotNull(tile.abilityEvents());
    }

    @Test
    void testSprites() {
        assertNotNull(tile.sprites());
    }

    @Test
    void testData() {
        assertNotNull(tile.data());
    }

    @Test
    void testMakeGameEventTarget() {
        GameEventTarget gameEventTarget = tile.makeGameEventTarget();

        assertNotNull(gameEventTarget);
        assertNotNull(gameEventTarget.tile());
        assertNull(gameEventTarget.tileFixture());
        assertNull(gameEventTarget.tileWallSegment());
        assertEquals(GameEventTarget.class.getCanonicalName(), gameEventTarget.getInterfaceName());
    }

    @Test
    void testAssignGameZoneAfterAddedToGameZone() {
        ((FakeGameZone) GAME_ZONE).TILES[X][Y] = tile;
        tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertSame(GAME_ZONE, tile.gameZone());
    }

    @Test
    void testAssignGameZoneAfterAddedToGameZoneWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> tile.assignGameZoneAfterAddedToGameZone(null));
    }

    @Test
    void testAssignGameZoneAfterAddedToGameZoneWhenAlreadyAssigned() {
        tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertThrows(IllegalArgumentException.class,
                () -> tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Tile.class.getCanonicalName(), tile.getInterfaceName());
    }

    @Test
    void testThrowsOnDeleteWhenGameZoneIsNotDeleted() {
        tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertThrows(IllegalStateException.class, tile::delete);
    }

    @Test
    void testDeletedInvariant() {
        ((FakeGameZone) GAME_ZONE).TILES[X][Y] = tile;
        tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        GAME_ZONE.delete();

        assertThrows(EntityDeletedException.class, () -> tile.gameZone());
        assertThrows(EntityDeletedException.class, () -> tile.location());
        assertThrows(EntityDeletedException.class, () -> tile.getHeight());
        assertThrows(EntityDeletedException.class, () -> tile.setHeight(0));
        assertThrows(EntityDeletedException.class, () -> tile.getGroundType());
        assertThrows(EntityDeletedException.class, () -> tile.setGroundType(new FakeGroundType()));
        assertThrows(EntityDeletedException.class, () -> tile.characters());
        assertThrows(EntityDeletedException.class, () -> tile.fixtures());
        assertThrows(EntityDeletedException.class, () -> tile.items());
        assertThrows(EntityDeletedException.class, () -> tile.movementEvents());
        assertThrows(EntityDeletedException.class, () -> tile.abilityEvents());
        assertThrows(EntityDeletedException.class, () -> tile.sprites());
        assertThrows(EntityDeletedException.class, () -> tile.data());
    }

    @Test
    void testGameZoneLocationCorrespondenceInvariant() {
        tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        ((FakeGameZone) GAME_ZONE).TILES[X][Y] = null;
        ((FakeGameZone) GAME_ZONE).TILES[X][Y + 1] = tile;

        assertThrows(IllegalStateException.class, () -> tile.gameZone());
        assertThrows(IllegalStateException.class, () -> tile.location());
        assertThrows(IllegalStateException.class, () -> tile.getHeight());
        assertThrows(IllegalStateException.class, () -> tile.setHeight(0));
        assertThrows(IllegalStateException.class, () -> tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> tile.setGroundType(new FakeGroundType()));
        assertThrows(IllegalStateException.class, () -> tile.characters());
        assertThrows(IllegalStateException.class, () -> tile.fixtures());
        assertThrows(IllegalStateException.class, () -> tile.items());
        assertThrows(IllegalStateException.class, () -> tile.movementEvents());
        assertThrows(IllegalStateException.class, () -> tile.abilityEvents());
        assertThrows(IllegalStateException.class, () -> tile.sprites());
        assertThrows(IllegalStateException.class, () -> tile.data());
    }

    @Test
    void testGameZoneMismatchInvariant() {
        tile.assignGameZoneAfterAddedToGameZone(GAME_ZONE);

        assertThrows(IllegalStateException.class, () -> tile.gameZone());
        assertThrows(IllegalStateException.class, () -> tile.location());
        assertThrows(IllegalStateException.class, () -> tile.getHeight());
        assertThrows(IllegalStateException.class, () -> tile.setHeight(0));
        assertThrows(IllegalStateException.class, () -> tile.getGroundType());
        assertThrows(IllegalStateException.class, () -> tile.setGroundType(new FakeGroundType()));
        assertThrows(IllegalStateException.class, () -> tile.characters());
        assertThrows(IllegalStateException.class, () -> tile.fixtures());
        assertThrows(IllegalStateException.class, () -> tile.items());
        assertThrows(IllegalStateException.class, () -> tile.movementEvents());
        assertThrows(IllegalStateException.class, () -> tile.abilityEvents());
        assertThrows(IllegalStateException.class, () -> tile.sprites());
        assertThrows(IllegalStateException.class, () -> tile.data());
    }
}
