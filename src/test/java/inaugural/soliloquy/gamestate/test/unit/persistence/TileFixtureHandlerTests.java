package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.entities.CharacterImpl;
import inaugural.soliloquy.gamestate.entities.TileFixtureImpl;
import inaugural.soliloquy.gamestate.persistence.TileFixtureHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.TileFixture;
import soliloquy.specs.gamestate.entities.TileFixtureItems;
import soliloquy.specs.gamestate.factories.TileFixtureFactory;
import soliloquy.specs.ruleset.entities.FixtureType;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TileFixtureHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final String FIXTURE_TYPE_ID = randomString();
    private final Vertex TILE_OFFSET = randomVertex();
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
    @SuppressWarnings("rawtypes")
    private final HandlerAndEntity<Map> MOCK_DATA_AND_HANDLER =
            generateMockEntityAndHandler(Map.class, DATA_STR);
    @SuppressWarnings("rawtypes")
    private final TypeHandler<Map> MOCK_MAP_HANDLER = MOCK_DATA_AND_HANDLER.handler;
    @SuppressWarnings("unchecked")
    private final Map<String, Object> MOCK_DATA = MOCK_DATA_AND_HANDLER.entity;

    @Mock private FixtureType mockFixtureType;
    @Mock private TileFixture mockTileFixture;
    @Mock private TileFixtureItems mockTileFixtureItems;
    @Mock private TileFixtureFactory mockTileFixtureFactory;
    private Function<String, FixtureType> mockGetFixtureType;

    private final String WRITTEN_VALUE = String.format(
            "{\"uuid\":\"%s\",\"typeId\":\"%s\",\"widthOffset\":%s,\"heightOffset\":%s," +
                    "\"items\":[\"%s\",\"%s\",\"%s\"],\"data\":\"%s\",\"name\":\"%s\"}",
            UUID, FIXTURE_TYPE_ID, TILE_OFFSET.X, TILE_OFFSET.Y, ITEM_STR_1, ITEM_STR_2,
            ITEM_STR_3, DATA_STR, NAME);

    private TypeHandler<TileFixture> handler;

    @BeforeEach
    public void setUp() {
        lenient().when(mockFixtureType.id()).thenReturn(FIXTURE_TYPE_ID);

        lenient().when(mockTileFixtureFactory.make(any(), any(), any())).thenReturn(mockTileFixture);

        lenient().when(mockTileFixture.getTileOffset())
                .thenReturn(TILE_OFFSET);
        lenient().when(mockTileFixture.items()).thenReturn(mockTileFixtureItems);
        lenient().when(mockTileFixture.data()).thenReturn(MOCK_DATA);
        lenient().when(mockTileFixture.getName()).thenReturn(NAME);
        lenient().when(mockTileFixture.uuid()).thenReturn(UUID);
        lenient().when(mockTileFixture.type()).thenReturn(mockFixtureType);

        lenient().when(mockTileFixtureItems.representation()).thenReturn(MOCK_ITEMS);

        mockGetFixtureType = generateMockLookupFunction(pairOf(FIXTURE_TYPE_ID, mockFixtureType));

        handler = new TileFixtureHandler(mockGetFixtureType, mockTileFixtureFactory,
                MOCK_MAP_HANDLER, MOCK_ITEM_HANDLER);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(null, mockTileFixtureFactory,
                        MOCK_MAP_HANDLER, MOCK_ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(mockGetFixtureType, null,
                        MOCK_MAP_HANDLER, MOCK_ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(mockGetFixtureType, mockTileFixtureFactory,
                        null, MOCK_ITEM_HANDLER));
        assertThrows(IllegalArgumentException.class,
                () -> new TileFixtureHandler(mockGetFixtureType, mockTileFixtureFactory,
                        MOCK_MAP_HANDLER, null));
    }

    @Test
    public void testTypeHandled() {
        assertEquals(TileFixtureImpl.class.getCanonicalName(), handler.typeHandled());
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
        verify(MOCK_MAP_HANDLER).write(MOCK_DATA);
        verify(mockTileFixture).getName();
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var tileFixture = handler.read(WRITTEN_VALUE);

        assertNotNull(tileFixture);
        assertSame(mockTileFixture, tileFixture);
        verify(mockGetFixtureType).apply(FIXTURE_TYPE_ID);
        verify(MOCK_MAP_HANDLER).read(DATA_STR);
        verify(mockTileFixtureFactory).make(same(mockFixtureType), same(MOCK_DATA), eq(UUID));
        verify(mockTileFixture).setTileOffset(eq(TILE_OFFSET));
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
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
