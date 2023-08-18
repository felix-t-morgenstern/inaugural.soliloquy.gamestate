package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.ItemHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final String ITEM_TYPE_ID = randomString();
    private final int NUM_CHARGES = randomInt();
    private final int NUM_IN_STACK = randomInt();
    private final float X_TILE_WIDTH_OFFSET = randomFloat();
    private final float Y_TILE_HEIGHT_OFFSET = randomFloat();
    private final String DATA_WRITTEN = randomString();
    private final HandlerAndEntity<VariableCache> DATA_HANDLER_AND_ENTITY =
            generateMockEntityAndHandler(VariableCache.class, DATA_WRITTEN);
    private final TypeHandler<VariableCache> DATA_HANDLER = DATA_HANDLER_AND_ENTITY.handler;
    private final VariableCache DATA = DATA_HANDLER_AND_ENTITY.entity;

    private final String WRITTEN_VALUE_WITH_CHARGES = String.format(
            "{\"uuid\":\"%s\",\"typeId\":\"%s\",\"xOffset\":%s,\"yOffset\":%s,\"charges\":%d," +
                    "\"data\":\"%s\"}",
            UUID, ITEM_TYPE_ID, X_TILE_WIDTH_OFFSET, Y_TILE_HEIGHT_OFFSET, NUM_CHARGES,
            DATA_WRITTEN);
    private final String WRITTEN_VALUE_STACKABLE = String.format(
            "{\"uuid\":\"%s\",\"typeId\":\"%s\",\"xOffset\":%s,\"yOffset\":%s," +
                    "\"numberInStack\":%d,\"data\":\"%s\"}",
            UUID, ITEM_TYPE_ID, X_TILE_WIDTH_OFFSET, Y_TILE_HEIGHT_OFFSET, NUM_IN_STACK,
            DATA_WRITTEN);

    @Mock private ItemType itemType;
    @Mock private Registry<ItemType> itemTypesRegistry;
    @Mock private Item itemWithCharges;
    @Mock private Item itemStackable;
    @Mock private Item factoryOutput;
    @Mock private ItemFactory itemFactory;

    private TypeHandler<Item> itemHandler;

    @Before
    public void setUp() {
        itemType = generateMockWithId(ItemType.class, ITEM_TYPE_ID);

        //noinspection unchecked
        itemTypesRegistry = (Registry<ItemType>) mock(Registry.class);
        when(itemTypesRegistry.get(anyString())).thenReturn(itemType);

        itemWithCharges = generateMockItem();
        when(itemWithCharges.getCharges()).thenReturn(NUM_CHARGES);

        itemStackable = generateMockItem();
        when(itemStackable.getNumberInStack()).thenReturn(NUM_IN_STACK);

        factoryOutput = mock(Item.class);

        itemFactory = mock(ItemFactory.class);
        when(itemFactory.make(any(), any(), any())).thenReturn(factoryOutput);

        itemHandler = new ItemHandler(itemTypesRegistry::get, DATA_HANDLER, itemFactory);
    }

    private Item generateMockItem() {
        Item item = mock(Item.class);
        when(item.uuid()).thenReturn(UUID);
        when(item.type()).thenReturn(itemType);
        when(item.data()).thenReturn(DATA);
        when(item.getTileOffset()).thenReturn(Vertex.of(X_TILE_WIDTH_OFFSET, Y_TILE_HEIGHT_OFFSET));
        return item;
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(null, DATA_HANDLER, itemFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(itemTypesRegistry::get, null, itemFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(itemTypesRegistry::get, DATA_HANDLER, null));
    }

    @Test
    public void testArchetype() {
        assertNotNull(itemHandler.archetype());
        assertEquals(Item.class.getCanonicalName(),
                itemHandler.archetype().getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Item.class.getCanonicalName() + ">",
                itemHandler.getInterfaceName());
    }

    @Test
    public void testWriteWithCharges() {
        when(itemType.hasCharges()).thenReturn(true);

        String writtenValue = itemHandler.write(itemWithCharges);

        assertEquals(WRITTEN_VALUE_WITH_CHARGES, writtenValue);
    }

    @Test
    public void testWriteStackable() {
        when(itemType.isStackable()).thenReturn(true);

        String writtenValue = itemHandler.write(itemStackable);

        assertEquals(WRITTEN_VALUE_STACKABLE, writtenValue);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> itemHandler.write(null));
    }

    @Test
    public void testReadWithCharges() {
        when(itemType.hasCharges()).thenReturn(true);

        Item output = itemHandler.read(WRITTEN_VALUE_WITH_CHARGES);

        assertSame(factoryOutput, output);
        verify(itemTypesRegistry, times(1)).get(ITEM_TYPE_ID);
        verify(DATA_HANDLER, times(1)).read(DATA_WRITTEN);
        verify(itemFactory, times(1)).make(itemType, DATA, UUID);
        verify(factoryOutput, times(1)).setTileOffset(eq(Vertex.of(X_TILE_WIDTH_OFFSET, Y_TILE_HEIGHT_OFFSET)));
        verify(factoryOutput, times(1)).setCharges(NUM_CHARGES);
    }

    @Test
    public void testReadStackable() {
        when(itemType.isStackable()).thenReturn(true);

        Item output = itemHandler.read(WRITTEN_VALUE_STACKABLE);

        assertSame(factoryOutput, output);
        verify(itemTypesRegistry, times(1)).get(ITEM_TYPE_ID);
        verify(DATA_HANDLER, times(1)).read(DATA_WRITTEN);
        verify(itemFactory, times(1)).make(itemType, DATA, UUID);
        verify(factoryOutput, times(1)).setTileOffset(eq(Vertex.of(X_TILE_WIDTH_OFFSET, Y_TILE_HEIGHT_OFFSET)));
        verify(factoryOutput, times(1)).setNumberInStack(NUM_IN_STACK);
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> itemHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> itemHandler.read(""));
    }
}
