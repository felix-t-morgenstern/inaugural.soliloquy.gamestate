package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.ItemFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.EntityUuidFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryImplTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final PairFactory PAIR_FACTORY = new FakePairFactory();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final EntityUuid ID = new FakeEntityUuid();
    private final VariableCache DATA = new VariableCacheStub();

    private ItemFactory _itemFactory;

    @BeforeEach
    void setUp() {
        _itemFactory = new ItemFactoryImpl(ENTITY_UUID_FACTORY, DATA_FACTORY,
                PAIR_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(null,
                DATA_FACTORY, PAIR_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(ENTITY_UUID_FACTORY,
                null, PAIR_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(ENTITY_UUID_FACTORY,
                DATA_FACTORY, null));
    }

    @Test
    void testGetInterface() {
        assertEquals(ItemFactory.class.getCanonicalName(), _itemFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, null);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character,String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, item.id());
        assertSame(ITEM_TYPE, item.type());
        assertSame(((FakeVariableCacheFactory)DATA_FACTORY).Created.get(0), item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
        assertEquals(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET, item.getXTileWidthOffset());
        assertEquals(ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET, item.getYTileHeightOffset());
    }

    @Test
    void testMakeWithData() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, DATA);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character,String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, item.id());
        assertSame(ITEM_TYPE, item.type());
        assertSame(DATA, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
        assertEquals(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET, item.getXTileWidthOffset());
        assertEquals(ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET, item.getYTileHeightOffset());
    }

    @Test
    void testMakeWithId() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, null, ID);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character,String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(ID, item.id());
        assertSame(ITEM_TYPE, item.type());
        assertSame(((FakeVariableCacheFactory)DATA_FACTORY).Created.get(0), item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
        assertEquals(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET, item.getXTileWidthOffset());
        assertEquals(ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET, item.getYTileHeightOffset());
    }

    @Test
    void testMakeWithIdAndData() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, DATA, ID);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character,String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(ID, item.id());
        assertSame(ITEM_TYPE, item.type());
        assertSame(DATA, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
        assertEquals(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET, item.getXTileWidthOffset());
        assertEquals(ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET, item.getYTileHeightOffset());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(null, DATA));

        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(null, DATA, ID));
        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(ITEM_TYPE, DATA, null));
    }
}
