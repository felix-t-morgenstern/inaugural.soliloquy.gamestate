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
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.factories.TileCharactersFactory;
import soliloquy.specs.gamestate.factories.TileFixturesFactory;
import soliloquy.specs.gamestate.factories.TileItemsFactory;
import soliloquy.specs.gamestate.factories.TileWallSegmentsFactory;
import soliloquy.specs.ruleset.entities.GroundType;
import soliloquy.specs.sprites.entities.Sprite;

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
    private final Sprite SPRITE_ARCHETYPE = new SpriteStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();

    private Tile _tile;

    @BeforeEach
    void setUp() {
        _tile = new TileImpl(GAME_ZONE, LOCATION, TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY,
                TILE_FIXTURES_FACTORY, TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY,
                SPRITE_ARCHETYPE, GENERIC_PARAMS_SET_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(null, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, null,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                null, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, null, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, null,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                null, MAP_FACTORY, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, null, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, null, SPRITE_ARCHETYPE,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, null,
                GENERIC_PARAMS_SET_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new TileImpl(GAME_ZONE, LOCATION,
                TILE_CHARACTERS_FACTORY, TILE_ITEMS_FACTORY, TILE_FIXTURES_FACTORY,
                TILE_WALL_SEGMENTS_FACTORY, MAP_FACTORY, COLLECTION_FACTORY, SPRITE_ARCHETYPE,
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
    void testDelete() {
        TileCharacters tileCharacters = _tile.characters();
        TileFixtures tileFixtures = _tile.fixtures();
        TileItems tileItems = _tile.items();
        TileWallSegments tileWallSegments = _tile.wallSegments();

        GAME_ZONE.delete();

        assertTrue(_tile.isDeleted());
        assertTrue(tileCharacters.isDeleted());
        assertTrue(tileFixtures.isDeleted());
        assertTrue(tileItems.isDeleted());
        assertTrue(tileWallSegments.isDeleted());
    }
}
