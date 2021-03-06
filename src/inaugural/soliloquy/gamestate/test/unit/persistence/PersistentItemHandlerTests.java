package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentItemHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeItemFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakePersistentEntityUuidHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistence.FakePersistentVariableCacheHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class PersistentItemHandlerTests {
    private final Registry<ItemType> ITEM_TYPES_REGISTRY = new FakeRegistry<>();
    private final PersistentValueTypeHandler<EntityUuid> UUID_HANDLER =
            new FakePersistentEntityUuidHandler();
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER =
            new FakePersistentVariableCacheHandler();
    private final ItemFactory ITEM_FACTORY = new FakeItemFactory();
    private final int NUM_CHARGES = 123;
    private final int NUM_IN_STACK = 456;
    private final float X_TILE_WIDTH_OFFSET = 0.546f;
    private final float Y_TILE_HEIGHT_OFFSET = 0.213f;

    private final String DATA_WITH_CHARGES = "{\"id\":\"EntityUuid0\",\"typeId\":\"ItemTypeStubId\",\"xOffset\":0.546,\"yOffset\":0.213,\"charges\":123,\"data\":\"VariableCache0\"}";
    private final String DATA_STACKABLE = "{\"id\":\"EntityUuid0\",\"typeId\":\"ItemTypeStubId\",\"xOffset\":0.546,\"yOffset\":0.213,\"numberInStack\":456,\"data\":\"VariableCache0\"}";

    private FakeItem _item;
    private ItemTypeStub _itemType;
    private PersistentValueTypeHandler<Item> _persistentItemHandler;

    @BeforeEach
    void setUp() {
        _itemType = new ItemTypeStub();
        _itemType.HasCharges = false;
        _itemType.IsStackable = false;
        _item = new FakeItem(_itemType);
        _item._xTileWidthOffset = X_TILE_WIDTH_OFFSET;
        _item._yTileHeightOffset = Y_TILE_HEIGHT_OFFSET;
        ITEM_TYPES_REGISTRY.add(_itemType);
        _persistentItemHandler = new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, UUID_HANDLER,
                DATA_HANDLER, ITEM_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(null, UUID_HANDLER,
                        DATA_HANDLER, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, null,
                        DATA_HANDLER, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, UUID_HANDLER,
                        null, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, UUID_HANDLER,
                        DATA_HANDLER, null));
    }

    @Test
    void testArchetype() {
        assertNotNull(_persistentItemHandler.getArchetype());
        assertEquals(Item.class.getCanonicalName(),
                _persistentItemHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                Item.class.getCanonicalName() + ">",
                _persistentItemHandler.getInterfaceName());
    }

    @Test
    void testWriteWithCharges() {
        _itemType.HasCharges = true;
        _item.setCharges(NUM_CHARGES);

        String writtenValue = _persistentItemHandler.write(_item);

        assertEquals(DATA_WITH_CHARGES, writtenValue);
    }

    @Test
    void testWriteStackable() {
        _itemType.IsStackable = true;
        _item.setNumberInStack(NUM_IN_STACK);

        String writtenValue = _persistentItemHandler.write(_item);

        assertEquals(DATA_STACKABLE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _persistentItemHandler.write(null));
    }

    @Test
    void testReadWithCharges() {
        _itemType.HasCharges = true;
        Item readItem = _persistentItemHandler.read(DATA_WITH_CHARGES);

        assertNotNull(readItem);
        assertSame(((FakePersistentEntityUuidHandler) UUID_HANDLER).READ_OUTPUTS.get(0),
                readItem.uuid());
        assertSame(_itemType, readItem.type());
        assertEquals(X_TILE_WIDTH_OFFSET, readItem.getXTileWidthOffset());
        assertEquals(Y_TILE_HEIGHT_OFFSET, readItem.getYTileHeightOffset());
        assertSame(((FakePersistentVariableCacheHandler) DATA_HANDLER)
                .READ_OUTPUTS.get(0),
                    readItem.data());
        assertEquals(NUM_CHARGES, readItem.getCharges());
    }

    @Test
    void testReadStackable() {
        _itemType.IsStackable = true;
        Item readItem = _persistentItemHandler.read(DATA_STACKABLE);

        assertNotNull(readItem);
        assertSame(((FakePersistentEntityUuidHandler) UUID_HANDLER).READ_OUTPUTS.get(0),
                readItem.uuid());
        assertSame(_itemType, readItem.type());
        assertEquals(X_TILE_WIDTH_OFFSET, readItem.getXTileWidthOffset());
        assertEquals(Y_TILE_HEIGHT_OFFSET, readItem.getYTileHeightOffset());
        assertSame(((FakePersistentVariableCacheHandler) DATA_HANDLER)
                .READ_OUTPUTS.get(0),
                    readItem.data());
        assertEquals(NUM_IN_STACK, readItem.getNumberInStack());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _persistentItemHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _persistentItemHandler.read(""));
    }
}
