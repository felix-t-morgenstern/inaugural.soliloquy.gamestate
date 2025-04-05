package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.TileHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;
import soliloquy.specs.gamestate.factories.TileFactory;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.ruleset.entities.GroundType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TileHandlerTests {
    @Mock private TileFactory mockTileFactory;

    private final String CHAR_STR = randomString();
    private final HandlerAndEntity<Character> MOCK_CHAR_AND_HANDLER =
            generateMockEntityAndHandler(Character.class, CHAR_STR);
    private final TypeHandler<Character> MOCK_CHAR_HANDLER = MOCK_CHAR_AND_HANDLER.handler;
    private final Character MOCK_CHAR = MOCK_CHAR_AND_HANDLER.entity;
    private final int CHAR_Z = randomInt();

    private final String ITEM_STR = randomString();
    private final HandlerAndEntity<Item> MOCK_ITEM_AND_HANDLER =
            generateMockEntityAndHandler(Item.class, ITEM_STR);
    private final TypeHandler<Item> MOCK_ITEM_HANDLER = MOCK_ITEM_AND_HANDLER.handler;
    private final Item MOCK_ITEM = MOCK_ITEM_AND_HANDLER.entity;
    private final int ITEM_Z = randomInt();

    private final String FIXTURE_STR = randomString();
    private final HandlerAndEntity<TileFixture> MOCK_FIXTURE_AND_HANDLER =
            generateMockEntityAndHandler(TileFixture.class, FIXTURE_STR);
    private final TypeHandler<TileFixture> MOCK_FIXTURE_HANDLER = MOCK_FIXTURE_AND_HANDLER.handler;
    private final TileFixture MOCK_FIXTURE = MOCK_FIXTURE_AND_HANDLER.entity;
    private final int FIXTURE_Z = randomInt();

    private final String SPRITE_STR = randomString();
    private final HandlerAndEntity<Sprite> MOCK_SPRITE_AND_HANDLER =
            generateMockEntityAndHandler(Sprite.class, SPRITE_STR);
    private final TypeHandler<Sprite> MOCK_SPRITE_HANDLER = MOCK_SPRITE_AND_HANDLER.handler;
    private final Sprite MOCK_SPRITE = MOCK_SPRITE_AND_HANDLER.entity;
    private final int SPRITE_Z = randomInt();

    private final String DATA_STR = randomString();
    private final HandlerAndEntity<VariableCache> MOCK_DATA_AND_HANDLER =
            generateMockEntityAndHandler(VariableCache.class, DATA_STR);
    private final TypeHandler<VariableCache> MOCK_DATA_HANDLER = MOCK_DATA_AND_HANDLER.handler;
    private final VariableCache MOCK_DATA = MOCK_DATA_AND_HANDLER.entity;

    private final String MOVE_EVENT_ID = randomString();
    private final GameMovementEvent MOCK_MOVE_EVENT =
            generateMockWithId(GameMovementEvent.class, MOVE_EVENT_ID);
    private final Function<String, GameMovementEvent> MOVE_EVENTS_LOOKUP =
            generateMockLookupFunctionWithId(MOCK_MOVE_EVENT);

    private final String ABILITY_EVENT_ID = randomString();
    private final GameAbilityEvent MOCK_ABILITY_EVENT =
            generateMockWithId(GameAbilityEvent.class, ABILITY_EVENT_ID);
    private final Function<String, GameAbilityEvent> ABILITY_EVENTS_LOOKUP =
            generateMockLookupFunctionWithId(MOCK_ABILITY_EVENT);

    private final String GROUND_TYPE_ID = randomString();
    private final GroundType MOCK_GROUND_TYPE =
            generateMockWithId(GroundType.class, GROUND_TYPE_ID);
    private final Function<String, GroundType> MOCK_GROUND_TYPES_LOOKUP =
            generateMockLookupFunctionWithId(MOCK_GROUND_TYPE);

    private final String WRITTEN_DATA = String.format(
            "{\"groundTypeId\":\"%s\",\"characters\":[{\"z\":%d," +
                    "\"entity\":\"%s\"}],\"items\":[{\"z\":%d," +
                    "\"entity\":\"%s\"}],\"fixtures\":[{\"z\":%d," + "\"entity\":\"%s\"}]," +
                    "\"movementEvents\":[\"%s\"]," +
                    "\"abilityEvents\":[\"%s\"],\"sprites\":[{\"z\":%d,\"entity\":\"%s\"}]," +
                    "\"data\":\"%s\"}",
            GROUND_TYPE_ID, CHAR_Z, CHAR_STR, ITEM_Z, ITEM_STR, FIXTURE_Z, FIXTURE_STR,
            MOVE_EVENT_ID, ABILITY_EVENT_ID, SPRITE_Z, SPRITE_STR, DATA_STR);

    @Mock private Tile mockCharacterTile;
    @Mock private Tile mockItemTile;
    @Mock private Tile mockFixtureTile;

    private TypeHandler<Tile> tileHandler;

    @Before
    public void setUp() {
        when(mockCharacterTile.location()).thenReturn(
                Coordinate3d.of(randomInt(), randomInt(), CHAR_Z));
        when(MOCK_CHAR.tile()).thenReturn(mockCharacterTile);
        when(mockItemTile.location()).thenReturn(Coordinate3d.of(randomInt(), randomInt(), ITEM_Z));
        when(MOCK_ITEM.tile()).thenReturn(mockItemTile);
        when(mockFixtureTile.location()).thenReturn(
                Coordinate3d.of(randomInt(), randomInt(), FIXTURE_Z));
        when(MOCK_FIXTURE.tile()).thenReturn(mockFixtureTile);

        tileHandler = new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER,
                MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER, MOVE_EVENTS_LOOKUP,
                ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(null, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER,
                        MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER,
                        MOVE_EVENTS_LOOKUP, ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, null, MOCK_ITEM_HANDLER,
                        MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER,
                        MOVE_EVENTS_LOOKUP, ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, null,
                        MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER,
                        MOVE_EVENTS_LOOKUP, ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER, null,
                        MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER, MOVE_EVENTS_LOOKUP,
                        ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER,
                        MOCK_FIXTURE_HANDLER, null, MOCK_DATA_HANDLER, MOVE_EVENTS_LOOKUP,
                        ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER,
                        MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, null, MOVE_EVENTS_LOOKUP,
                        ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER,
                        MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER, null,
                        ABILITY_EVENTS_LOOKUP, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER,
                        MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER,
                        MOVE_EVENTS_LOOKUP, null, MOCK_GROUND_TYPES_LOOKUP));
        assertThrows(IllegalArgumentException.class,
                () -> new TileHandler(mockTileFactory, MOCK_CHAR_HANDLER, MOCK_ITEM_HANDLER,
                        MOCK_FIXTURE_HANDLER, MOCK_SPRITE_HANDLER, MOCK_DATA_HANDLER,
                        MOVE_EVENTS_LOOKUP, ABILITY_EVENTS_LOOKUP, null));
    }

    @Test
    public void testWrite() {
        var mockTile = mock(Tile.class);
        var mockCharacters = mockTileEntities(MOCK_CHAR);
        when(mockTile.characters()).thenReturn(mockCharacters);
        var mockItems = mockTileEntities(MOCK_ITEM);
        when(mockTile.items()).thenReturn(mockItems);
        var mockFixtures = mockTileEntities(MOCK_FIXTURE);
        when(mockTile.fixtures()).thenReturn(mockFixtures);
        var mockSprites = generateMockMap(pairOf(MOCK_SPRITE, SPRITE_Z));
        when(mockTile.sprites()).thenReturn(mockSprites);
        when(mockTile.data()).thenReturn(MOCK_DATA);
        var mockMoveEvents = generateMockList(MOCK_MOVE_EVENT);
        when(mockTile.movementEvents()).thenReturn(mockMoveEvents);
        var mockAbilityEvents = generateMockList(MOCK_ABILITY_EVENT);
        when(mockTile.abilityEvents()).thenReturn(mockAbilityEvents);
        when(mockTile.getGroundType()).thenReturn(MOCK_GROUND_TYPE);

        assertEquals(WRITTEN_DATA, tileHandler.write(mockTile));
        verify(mockTile, times(2)).characters();
        verify(mockCharacters).size();
        verify(mockCharacters).iterator();
        verify(MOCK_CHAR_HANDLER).write(MOCK_CHAR);
        verify(mockTile, times(2)).items();
        verify(mockItems).size();
        verify(mockItems).iterator();
        verify(MOCK_ITEM_HANDLER).write(MOCK_ITEM);
        verify(mockTile, times(2)).fixtures();
        verify(mockFixtures).size();
        verify(mockFixtures).iterator();
        verify(MOCK_FIXTURE_HANDLER).write(MOCK_FIXTURE);
        verify(mockTile, times(3)).sprites();
        verify(mockSprites).size();
        verify(mockSprites).keySet();
        verify(mockSprites).get(MOCK_SPRITE);
        verify(MOCK_SPRITE_HANDLER).write(MOCK_SPRITE);
        verify(mockTile).data();
        verify(MOCK_DATA_HANDLER).write(MOCK_DATA);
        verify(mockTile, times(2)).movementEvents();
        verify(mockMoveEvents).size();
        verify(mockMoveEvents).iterator();
        // This (and some others below) are at least once since .id() is called once in the
        // mocking setup
        verify(MOCK_MOVE_EVENT, atLeastOnce()).id();
        verify(mockTile, times(2)).abilityEvents();
        verify(mockAbilityEvents).size();
        verify(mockAbilityEvents).iterator();
        verify(MOCK_ABILITY_EVENT, atLeastOnce()).id();
        verify(mockTile).getGroundType();
        verify(MOCK_GROUND_TYPE, atLeastOnce()).id();
    }

    private <T extends TileEntity> TileEntities<T> mockTileEntities(T entity) {
        @SuppressWarnings("unchecked")
        var mockTileEntities = (TileEntities<T>) mock(TileEntities.class);
        var entities = listOf(pairOf(entity, entity.tile().location().Z));
        var iterator = entities.iterator();
        when(mockTileEntities.iterator()).thenReturn(iterator);
        when(mockTileEntities.size()).thenReturn(1);
        return mockTileEntities;
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileHandler.write(null));
    }

    @Test
    public void testRead() {
        var mockTile = mock(Tile.class);
        //noinspection unchecked
        var mockTileChars = (TileEntities<Character>) mock(TileEntities.class);
        when(mockTile.characters()).thenReturn(mockTileChars);
        //noinspection unchecked
        var mockTileFixtures = (TileEntities<TileFixture>) mock(TileEntities.class);
        when(mockTile.fixtures()).thenReturn(mockTileFixtures);
        //noinspection unchecked
        var mockTileItems = (TileEntities<Item>) mock(TileEntities.class);
        when(mockTile.items()).thenReturn(mockTileItems);
        when(mockTileFactory.make(any())).thenReturn(mockTile);
        //noinspection unchecked
        var mockMoveEvents = (List<GameMovementEvent>) mock(List.class);
        when(mockTile.movementEvents()).thenReturn(mockMoveEvents);
        //noinspection unchecked
        var mockAbilityEvents = (List<GameAbilityEvent>) mock(List.class);
        when(mockTile.abilityEvents()).thenReturn(mockAbilityEvents);
        //noinspection unchecked
        var mockSprites = (Map<Sprite, Integer>) mock(Map.class);
        when(mockTile.sprites()).thenReturn(mockSprites);

        var readTile = tileHandler.read(WRITTEN_DATA);

        assertNotNull(readTile);
        assertSame(mockTile, readTile);
        verify(MOCK_DATA_HANDLER).read(DATA_STR);
        verify(mockTileFactory).make(MOCK_DATA);
        verify(MOCK_GROUND_TYPES_LOOKUP).apply(GROUND_TYPE_ID);
        verify(mockTile).setGroundType(MOCK_GROUND_TYPE);
        verify(MOCK_CHAR_HANDLER).read(CHAR_STR);
        verify(mockTileChars).add(MOCK_CHAR, CHAR_Z);
        verify(MOCK_ITEM_HANDLER).read(ITEM_STR);
        verify(mockTileItems).add(MOCK_ITEM, ITEM_Z);
        verify(MOCK_FIXTURE_HANDLER).read(FIXTURE_STR);
        verify(mockTileFixtures).add(MOCK_FIXTURE, FIXTURE_Z);
        verify(MOVE_EVENTS_LOOKUP).apply(MOVE_EVENT_ID);
        verify(mockTile).movementEvents();
        verify(mockMoveEvents).add(MOCK_MOVE_EVENT);
        verify(ABILITY_EVENTS_LOOKUP).apply(ABILITY_EVENT_ID);
        verify(mockTile).abilityEvents();
        verify(mockAbilityEvents).add(MOCK_ABILITY_EVENT);
        verify(MOCK_SPRITE_HANDLER).read(SPRITE_STR);
        verify(mockTile).sprites();
        verify(mockSprites).put(MOCK_SPRITE, SPRITE_Z);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> tileHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> tileHandler.read(""));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Tile.class.getCanonicalName() + ">",
                tileHandler.getInterfaceName());
    }
}
