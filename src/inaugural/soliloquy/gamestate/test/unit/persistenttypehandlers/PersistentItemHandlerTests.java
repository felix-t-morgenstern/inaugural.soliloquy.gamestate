package inaugural.soliloquy.gamestate.test.unit.persistenttypehandlers;

import inaugural.soliloquy.gamestate.persistentvaluetypehandlers.PersistentItemHandler;
import inaugural.soliloquy.gamestate.test.stubs.ItemFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.RegistryStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentEntityUuidHandlerStub;
import inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers.PersistentVariableCacheHandlerStub;
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
    private final Registry<ItemType> ITEM_TYPES_REGISTRY = new RegistryStub<>();
    private final PersistentValueTypeHandler<EntityUuid> ENTITY_UUID_HANDLER =
            new PersistentEntityUuidHandlerStub();
    private final PersistentValueTypeHandler<VariableCache> DATA_HANDLER =
            new PersistentVariableCacheHandlerStub();
    private final ItemFactory ITEM_FACTORY = new ItemFactoryStub();
    private final Item ITEM = new ItemStub();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final int NUM_CHARGES = 123;
    private final int NUM_IN_STACK = 456;

    private final String DATA_WITH_CHARGES = "{\"id\":\"EntityUuid0\",\"typeId\":\"ItemTypeStubId\",\"charges\":123,\"data\":\"VariableCache0\"}";
    private final String DATA_STACKABLE = "{\"id\":\"EntityUuid0\",\"typeId\":\"ItemTypeStubId\",\"numberInStack\":456,\"data\":\"VariableCache0\"}";

    private PersistentValueTypeHandler<Item> _persistentItemHandler;

    @BeforeEach
    void setUp() {
        ItemTypeStub._hasCharges = false;
        ItemTypeStub._isStackable = false;
        ITEM_TYPES_REGISTRY.add(ITEM_TYPE);
        _persistentItemHandler = new PersistentItemHandler(ITEM_TYPES_REGISTRY,
                ENTITY_UUID_HANDLER, DATA_HANDLER, ITEM_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(null, ENTITY_UUID_HANDLER,
                        DATA_HANDLER, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY, null,
                        DATA_HANDLER, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY, ENTITY_UUID_HANDLER,
                        null, ITEM_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentItemHandler(ITEM_TYPES_REGISTRY, ENTITY_UUID_HANDLER,
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
        assertSame(((PersistentEntityUuidHandlerStub)ENTITY_UUID_HANDLER).READ_OUTPUTS.get(0),
                readItem.id());
        assertSame(ITEM_TYPE, readItem.type());
        assertSame(((PersistentVariableCacheHandlerStub) DATA_HANDLER)
                .READ_OUTPUTS.get(0),
                    readItem.data());
        assertEquals(NUM_CHARGES, readItem.getCharges());
    }

    @Test
    void testReadStackable() {
        ItemTypeStub._isStackable = true;
        Item readItem = _persistentItemHandler.read(DATA_STACKABLE);

        assertNotNull(readItem);
        assertSame(((PersistentEntityUuidHandlerStub)ENTITY_UUID_HANDLER).READ_OUTPUTS.get(0),
                readItem.id());
        assertSame(ITEM_TYPE, readItem.type());
        assertSame(((PersistentVariableCacheHandlerStub) DATA_HANDLER)
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
