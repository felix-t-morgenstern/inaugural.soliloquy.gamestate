package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.ItemHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.Map;
import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final String ITEM_TYPE_ID = randomString();
    private final int NUM_CHARGES = randomInt();
    private final int NUM_IN_STACK = randomInt();
    private final Vertex TILE_OFFSET = randomVertex();
    private final String DATA_WRITTEN = randomString();
    @SuppressWarnings("rawtypes") private final HandlerAndEntity<Map> MAP_HANDLER_AND_ENTITY =
            generateMockEntityAndHandler(Map.class, DATA_WRITTEN);
    @SuppressWarnings("rawtypes") private final TypeHandler<Map> MAP_HANDLER =
            MAP_HANDLER_AND_ENTITY.handler;
    @SuppressWarnings("unchecked")
    private final Map<String, Object> DATA = MAP_HANDLER_AND_ENTITY.entity;

    private final String WRITTEN_VALUE_WITH_CHARGES = String.format(
            "{\"uuid\":\"%s\",\"typeId\":\"%s\",\"xOffset\":%s,\"yOffset\":%s,\"charges\":%d," +
                    "\"data\":\"%s\"}",
            UUID, ITEM_TYPE_ID, TILE_OFFSET.X, TILE_OFFSET.Y, NUM_CHARGES,
            DATA_WRITTEN);
    private final String WRITTEN_VALUE_STACKABLE = String.format(
            "{\"uuid\":\"%s\",\"typeId\":\"%s\",\"xOffset\":%s,\"yOffset\":%s," +
                    "\"numberInStack\":%d,\"data\":\"%s\"}",
            UUID, ITEM_TYPE_ID, TILE_OFFSET.X, TILE_OFFSET.Y, NUM_IN_STACK,
            DATA_WRITTEN);

    @Mock private ItemType mockItemType;
    @Mock private Map<String, ItemType> mockGetItemType;
    @Mock private Item mockItemWithCharges;
    @Mock private Item mockItemStackable;
    @Mock private Item factoryOutput;
    @Mock private ItemFactory itemFactory;

    private TypeHandler<Item> itemHandler;

    @BeforeEach
    public void setUp() {
        mockItemType = generateMockWithId(ItemType.class, ITEM_TYPE_ID);

        lenient().when(mockGetItemType.get(anyString())).thenReturn(mockItemType);

        mockItemWithCharges = generateMockItem();
        lenient().when(mockItemWithCharges.getCharges()).thenReturn(NUM_CHARGES);

        mockItemStackable = generateMockItem();
        lenient().when(mockItemStackable.getNumberInStack()).thenReturn(NUM_IN_STACK);

        factoryOutput = mock(Item.class);

        itemFactory = mock(ItemFactory.class);
        lenient().when(itemFactory.make(any(), any(), any())).thenReturn(factoryOutput);

        itemHandler = new ItemHandler(mockGetItemType::get, MAP_HANDLER, itemFactory);
    }

    private Item generateMockItem() {
        var item = mock(Item.class);
        lenient().when(item.uuid()).thenReturn(UUID);
        lenient().when(item.type()).thenReturn(mockItemType);
        lenient().when(item.data()).thenReturn(DATA);
        lenient().when(item.getTileOffset()).thenReturn(TILE_OFFSET);
        return item;
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(null, MAP_HANDLER, itemFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(mockGetItemType::get, null, itemFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(mockGetItemType::get, MAP_HANDLER, null));
    }

    @Test
    public void testWriteWithCharges() {
        when(mockItemType.hasCharges()).thenReturn(true);

        String writtenValue = itemHandler.write(mockItemWithCharges);

        assertEquals(WRITTEN_VALUE_WITH_CHARGES, writtenValue);
    }

    @Test
    public void testWriteStackable() {
        when(mockItemType.isStackable()).thenReturn(true);

        String writtenValue = itemHandler.write(mockItemStackable);

        assertEquals(WRITTEN_VALUE_STACKABLE, writtenValue);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> itemHandler.write(null));
    }

    @Test
    public void testReadWithCharges() {
        when(mockItemType.hasCharges()).thenReturn(true);

        Item output = itemHandler.read(WRITTEN_VALUE_WITH_CHARGES);

        assertSame(factoryOutput, output);
        verify(mockGetItemType, once()).get(ITEM_TYPE_ID);
        verify(MAP_HANDLER, once()).read(DATA_WRITTEN);
        verify(itemFactory, once()).make(mockItemType, DATA, UUID);
        verify(factoryOutput, once()).setTileOffset(eq(TILE_OFFSET));
        verify(factoryOutput, once()).setCharges(NUM_CHARGES);
    }

    @Test
    public void testReadStackable() {
        when(mockItemType.isStackable()).thenReturn(true);

        Item output = itemHandler.read(WRITTEN_VALUE_STACKABLE);

        assertSame(factoryOutput, output);
        verify(mockGetItemType, once()).get(ITEM_TYPE_ID);
        verify(MAP_HANDLER, once()).read(DATA_WRITTEN);
        verify(itemFactory, once()).make(mockItemType, DATA, UUID);
        verify(factoryOutput, once()).setTileOffset(eq(TILE_OFFSET));
        verify(factoryOutput, once()).setNumberInStack(NUM_IN_STACK);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> itemHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> itemHandler.read(""));
    }
}
