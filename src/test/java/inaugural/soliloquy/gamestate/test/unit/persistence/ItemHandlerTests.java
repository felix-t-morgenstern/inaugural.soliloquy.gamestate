package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.ItemHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.fakes.FakeItemFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeUuidHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakeVariableCacheHandler;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemHandlerTests {
    private final Registry<ItemType> ITEM_TYPES_REGISTRY = new FakeRegistry<>();
    private final TypeHandler<UUID> UUID_HANDLER = new FakeUuidHandler();
    private final TypeHandler<VariableCache> DATA_HANDLER =
            new FakeVariableCacheHandler();
    private final ItemFactory ITEM_FACTORY = new FakeItemFactory();
    private final int NUM_CHARGES = 123;
    private final int NUM_IN_STACK = 456;
    private final float X_TILE_WIDTH_OFFSET = 0.546f;
    private final float Y_TILE_HEIGHT_OFFSET = 0.213f;

    private final String DATA_WITH_CHARGES =
            "{\"uuid\":\"UUID0\",\"typeId\":\"ItemTypeStubId\",\"xOffset\":0.546,\"yOffset\":0" +
                    ".213,\"charges\":123,\"data\":\"VariableCache0\"}";
    private final String DATA_STACKABLE =
            "{\"uuid\":\"UUID0\",\"typeId\":\"ItemTypeStubId\",\"xOffset\":0.546,\"yOffset\":0" +
                    ".213,\"numberInStack\":456,\"data\":\"VariableCache0\"}";

    private FakeItem item;
    private ItemTypeStub itemType;

    private TypeHandler<Item> persistentItemHandler;

    @BeforeEach
    void setUp() {
        itemType = new ItemTypeStub();
        itemType.HasCharges = false;
        itemType.IsStackable = false;
        item = new FakeItem(itemType);
        item.xTileWidthOffset = X_TILE_WIDTH_OFFSET;
        item.yTileHeightOffset = Y_TILE_HEIGHT_OFFSET;
        ITEM_TYPES_REGISTRY.add(itemType);
        persistentItemHandler = new ItemHandler(ITEM_TYPES_REGISTRY::get, UUID_HANDLER,
                DATA_HANDLER, ITEM_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(null, UUID_HANDLER,
                        DATA_HANDLER, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(ITEM_TYPES_REGISTRY::get, null,
                        DATA_HANDLER, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(ITEM_TYPES_REGISTRY::get, UUID_HANDLER,
                        null, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemHandler(ITEM_TYPES_REGISTRY::get, UUID_HANDLER,
                        DATA_HANDLER, null));
    }

    @Test
    void testArchetype() {
        assertNotNull(persistentItemHandler.getArchetype());
        assertEquals(Item.class.getCanonicalName(),
                persistentItemHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Item.class.getCanonicalName() + ">",
                persistentItemHandler.getInterfaceName());
    }

    @Test
    void testWriteWithCharges() {
        itemType.HasCharges = true;
        item.setCharges(NUM_CHARGES);

        String writtenValue = persistentItemHandler.write(item);

        assertEquals(DATA_WITH_CHARGES, writtenValue);
    }

    @Test
    void testWriteStackable() {
        itemType.IsStackable = true;
        item.setNumberInStack(NUM_IN_STACK);

        String writtenValue = persistentItemHandler.write(item);

        assertEquals(DATA_STACKABLE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> persistentItemHandler.write(null));
    }

    @Test
    void testReadWithCharges() {
        itemType.HasCharges = true;
        Item readItem = persistentItemHandler.read(DATA_WITH_CHARGES);

        assertNotNull(readItem);
        assertSame(((FakeUuidHandler) UUID_HANDLER).READ_OUTPUTS.get(0),
                readItem.uuid());
        assertSame(itemType, readItem.type());
        assertEquals(Vertex.of(X_TILE_WIDTH_OFFSET, Y_TILE_HEIGHT_OFFSET),
                readItem.getTileOffset());
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER)
                        .READ_OUTPUTS.get(0),
                readItem.data());
        assertEquals(NUM_CHARGES, readItem.getCharges());
    }

    @Test
    void testReadStackable() {
        itemType.IsStackable = true;
        Item readItem = persistentItemHandler.read(DATA_STACKABLE);

        assertNotNull(readItem);
        assertSame(((FakeUuidHandler) UUID_HANDLER).READ_OUTPUTS.get(0),
                readItem.uuid());
        assertSame(itemType, readItem.type());
        assertEquals(Vertex.of(X_TILE_WIDTH_OFFSET, Y_TILE_HEIGHT_OFFSET),
                readItem.getTileOffset());
        assertSame(((FakeVariableCacheHandler) DATA_HANDLER)
                        .READ_OUTPUTS.get(0),
                readItem.data());
        assertEquals(NUM_IN_STACK, readItem.getNumberInStack());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> persistentItemHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> persistentItemHandler.read(""));
    }
}
