package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.TileFixtureHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.random.Random.randomFloat;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TileFixtureHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final String FIXTURE_TYPE_ID = randomString();
    private final float TILE_WIDTH_OFFSET = randomFloat();
    private final float TILE_HEIGHT_OFFSET = randomFloat();
    private final String NAME = randomString();
    private final String DATA_STR = randomString();
    private final String ITEM_STR_1 = randomString();
    private final String ITEM_STR_2 = randomString();
    private final String ITEM_STR_3 = randomString();
    private final HandlerAndEntities<Item> MOCK_ITEMS_AND_HANDLER =
            generateMockHandlerAndEntities(Item.class, arrayOf(ITEM_STR_1, ITEM_STR_2, ITEM_STR_3));
    private final TypeHandler<Item> MOCK_ITEM_HANDLER = MOCK_ITEMS_AND_HANDLER.handler;
    private final Item MOCK_ITEM_1 = MOCK_ITEMS_AND_HANDLER.entities.get(ITEM_STR_1);
    private final Item MOCK_ITEM_2 = MOCK_ITEMS_AND_HANDLER.entities.get(ITEM_STR_2);
    private final Item MOCK_ITEM_3 = MOCK_ITEMS_AND_HANDLER.entities.get(ITEM_STR_3);
    private final List<Item> MOCK_ITEMS = generateMockList(MOCK_ITEM_1, MOCK_ITEM_2, MOCK_ITEM_3);
    private final HandlerAndEntity<VariableCache> MOCK_DATA_AND_HANDLER =
            generateMockEntityAndHandler(VariableCache.class, DATA_STR);
    private final TypeHandler<VariableCache> MOCK_DATA_HANDLER = MOCK_DATA_AND_HANDLER.handler;
    private final VariableCache MOCK_DATA = MOCK_DATA_AND_HANDLER.entity;

    @Mock private FixtureType mockFixtureType;
    @Mock private TileFixture mockTileFixture;
    @Mock private TileFixtureItems mockTileFixtureItems;
    @Mock private TileFixtureFactory mockTileFixtureFactory;
    private Function<String, FixtureType> mockGetFixtureType;

    private final String WRITTEN_VALUE = String.format(
            "{\"uuid\":\"%s\",\"typeId\":\"%s\",\"widthOffset\":%s,\"heightOffset\":%s," +
                    "\"items\":[\"%s\",\"%s\",\"%s\"],\"data\":\"%s\",\"name\":\"%s\"}",
            UUID, FIXTURE_TYPE_ID, TILE_WIDTH_OFFSET, TILE_HEIGHT_OFFSET, ITEM_STR_1, ITEM_STR_2,
            ITEM_STR_3, DATA_STR, NAME);

    private TypeHandler<TileFixture> handler;

    @Before
    public void setUp() {
        when(mockFixtureType.id()).thenReturn(FIXTURE_TYPE_ID);

        when(mockTileFixtureFactory.make(any(), any(), any())).thenReturn(mockTileFixture);

        when(mockTileFixture.getTileOffset())
                .thenReturn(Vertex.of(TILE_WIDTH_OFFSET, TILE_HEIGHT_OFFSET));
        when(mockTileFixture.items()).thenReturn(mockTileFixtureItems);
        when(mockTileFixture.data()).thenReturn(MOCK_DATA);
        when(mockTileFixture.getName()).thenReturn(NAME);
        when(mockTileFixture.uuid()).thenReturn(UUID);
        when(mockTileFixture.type()).thenReturn(mockFixtureType);

        when(mockTileFixtureItems.representation()).thenReturn(MOCK_ITEMS);

        mockGetFixtureType = generateMockLookupFunction(pairOf(FIXTURE_TYPE_ID, mockFixtureType));

        handler = new TileFixtureHandler(mockGetFixtureType, mockTileFixtureFactory,
                MOCK_DATA_HANDLER, MOCK_ITEM_HANDLER);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(null, mockTileFixtureFactory,
                        MOCK_DATA_HANDLER, MOCK_ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(mockGetFixtureType, null,
                        MOCK_DATA_HANDLER, MOCK_ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(mockGetFixtureType, mockTileFixtureFactory,
                        null, MOCK_ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(mockGetFixtureType, mockTileFixtureFactory,
                        MOCK_DATA_HANDLER, null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        TileFixture.class.getCanonicalName() + ">",
                handler.getInterfaceName());
    }

    @Test
    public void testArchetype() {
        assertNotNull(handler.archetype());
        assertEquals(TileFixture.class.getCanonicalName(),
                handler.archetype().getInterfaceName());
    }

    @Test
    public void testWrite() {
        var writtenValue = handler.write(mockTileFixture);

        assertEquals(WRITTEN_VALUE, writtenValue);
        verify(mockTileFixture).uuid();
        verify(mockTileFixture).type();
        verify(mockFixtureType).id();
        verify(mockTileFixture).items();
        verify(mockTileFixtureItems).representation();
        verify(MOCK_ITEMS, atLeast(1)).size();
        verify(MOCK_ITEMS, times(3)).get(anyInt());
        verify(MOCK_ITEMS).get(0);
        verify(MOCK_ITEMS).get(1);
        verify(MOCK_ITEMS).get(2);
        verify(MOCK_ITEM_HANDLER, times(3)).write(any());
        verify(MOCK_ITEM_HANDLER).write(MOCK_ITEM_1);
        verify(MOCK_ITEM_HANDLER).write(MOCK_ITEM_2);
        verify(MOCK_ITEM_HANDLER).write(MOCK_ITEM_3);
        verify(mockTileFixture).data();
        verify(MOCK_DATA_HANDLER).write(MOCK_DATA);
        verify(mockTileFixture).getName();
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var tileFixture = handler.read(WRITTEN_VALUE);

        assertNotNull(tileFixture);
        assertSame(mockTileFixture, tileFixture);
        verify(mockGetFixtureType).apply(FIXTURE_TYPE_ID);
        verify(MOCK_DATA_HANDLER).read(DATA_STR);
        verify(mockTileFixtureFactory).make(same(mockFixtureType), same(MOCK_DATA), eq(UUID));
        verify(mockTileFixture).setTileOffset(eq(Vertex.of(TILE_WIDTH_OFFSET, TILE_HEIGHT_OFFSET)));
        verify(MOCK_ITEM_HANDLER, times(3)).read(any());
        verify(MOCK_ITEM_HANDLER).read(ITEM_STR_1);
        verify(MOCK_ITEM_HANDLER).read(ITEM_STR_2);
        verify(MOCK_ITEM_HANDLER).read(ITEM_STR_3);
        verify(mockTileFixture, times(3)).items();
        verify(mockTileFixtureItems).add(MOCK_ITEM_1);
        verify(mockTileFixtureItems).add(MOCK_ITEM_2);
        verify(mockTileFixtureItems).add(MOCK_ITEM_3);
        verify(mockTileFixture).setName(NAME);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
