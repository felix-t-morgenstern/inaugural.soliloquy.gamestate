package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentItemHandler;
import inaugural.soliloquy.gamestate.test.fakes.FakeItemFactory;
import inaugural.soliloquy.gamestate.test.fakes.FakeItem;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.fakes.FakeRegistry;
import inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers.FakePersistentEntityUuidHandler;
import inaugural.soliloquy.gamestate.test.fakes.persistenttypehandlers.FakePersistentVariableCacheHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class PersistentItemHandlerTests {
    private final Registry<ItemType> ITEM_TYPES_REGISTRY = new FakeRegistry<>();
    private final PersistentValueTypeHandler<EntityUuid> ID_HANDLER =
            new FakePersistentEntityUuidHandler();
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER =
            new FakePersistentVariableCacheHandler();
    private final ItemFactory ITEM_FACTORY = new FakeItemFactory();
    private final FakeItem ITEM = new FakeItem();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final int NUM_CHARGES = 123;
    private final int NUM_IN_STACK = 456;
    private final float X_TILE_WIDTH_OFFSET = 0.546f;
    private final float Y_TILE_HEIGHT_OFFSET = 0.213f;

    private final String DATA_WITH_CHARGES = "{\"id\":\"EntityUuid0\",\"typeId\":\"ItemTypeStubId\",\"xOffset\":0.546,\"yOffset\":0.213,\"charges\":123,\"data\":\"VariableCache0\"}";
    private final String DATA_STACKABLE = "{\"id\":\"EntityUuid0\",\"typeId\":\"ItemTypeStubId\",\"xOffset\":0.546,\"yOffset\":0.213,\"numberInStack\":456,\"data\":\"VariableCache0\"}";

    private PersistentValueTypeHandler<Item> _persistentItemHandler;

    @BeforeEach
    void setUp() {
        ItemTypeStub._hasCharges = false;
        ItemTypeStub._isStackable = false;
        ITEM._xTileWidthOffset = X_TILE_WIDTH_OFFSET;
        ITEM._yTileHeightOffset = Y_TILE_HEIGHT_OFFSET;
        ITEM_TYPES_REGISTRY.add(ITEM_TYPE);
        _persistentItemHandler = new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, ID_HANDLER,
                DATA_HANDLER, ITEM_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(null, ID_HANDLER, DATA_HANDLER, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, null, DATA_HANDLER,
                        ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, ID_HANDLER, null,
                        ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY::get, ID_HANDLER, DATA_HANDLER,
                        null));
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
        ItemTypeStub._hasCharges = true;
        ITEM.setCharges(NUM_CHARGES);

        String writtenValue = _persistentItemHandler.write(ITEM);

        assertEquals(DATA_WITH_CHARGES, writtenValue);
    }

    @Test
    void testWriteStackable() {
        ItemTypeStub._isStackable = true;
        ITEM.setNumberInStack(NUM_IN_STACK);

        String writtenValue = _persistentItemHandler.write(ITEM);

        assertEquals(DATA_STACKABLE, writtenValue);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _persistentItemHandler.write(null));
    }

    @Test
    void testReadWithCharges() {
        ItemTypeStub._hasCharges = true;
        Item readItem = _persistentItemHandler.read(DATA_WITH_CHARGES);

        assertNotNull(readItem);
        assertSame(((FakePersistentEntityUuidHandler) ID_HANDLER).READ_OUTPUTS.get(0),
                readItem.id());
        assertSame(ITEM_TYPE, readItem.type());
        assertEquals(X_TILE_WIDTH_OFFSET, readItem.getXTileWidthOffset());
        assertEquals(Y_TILE_HEIGHT_OFFSET, readItem.getYTileHeightOffset());
        assertSame(((FakePersistentVariableCacheHandler) DATA_HANDLER)
                .READ_OUTPUTS.get(0),
                    readItem.data());
        assertEquals(NUM_CHARGES, readItem.getCharges());
    }

    @Test
    void testReadStackable() {
        ItemTypeStub._isStackable = true;
        Item readItem = _persistentItemHandler.read(DATA_STACKABLE);

        assertNotNull(readItem);
        assertSame(((FakePersistentEntityUuidHandler) ID_HANDLER).READ_OUTPUTS.get(0),
                readItem.id());
        assertSame(ITEM_TYPE, readItem.type());
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
