package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.ItemFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryImplTests {
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final UUID UUID = java.util.UUID.randomUUID();
    private final UUID GENERATED_UUID = java.util.UUID.randomUUID();
    private final Supplier<UUID> UUID_FACTORY = () -> GENERATED_UUID;
    private final VariableCache DATA = new VariableCacheStub();

    private ItemFactory _itemFactory;

    @BeforeEach
    void setUp() {
        _itemFactory = new ItemFactoryImpl(UUID_FACTORY, DATA_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(null,
                DATA_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(UUID_FACTORY,
                null));
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
        Pair<Character, String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(GENERATED_UUID, item.uuid());
        assertSame(ITEM_TYPE, item.type());
        assertSame(((FakeVariableCacheFactory) DATA_FACTORY).Created.get(0), item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(GENERATED_UUID, takenFromStack.uuid());
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
        Pair<Character, String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(GENERATED_UUID, item.uuid());
        assertSame(ITEM_TYPE, item.type());
        assertSame(DATA, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(GENERATED_UUID, takenFromStack.uuid());
        assertEquals(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET, item.getXTileWidthOffset());
        assertEquals(ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET, item.getYTileHeightOffset());
    }

    @Test
    void testMakeWithId() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, null, UUID);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character, String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(UUID, item.uuid());
        assertSame(ITEM_TYPE, item.type());
        assertSame(((FakeVariableCacheFactory) DATA_FACTORY).Created.get(0), item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(GENERATED_UUID, takenFromStack.uuid());
        assertEquals(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET, item.getXTileWidthOffset());
        assertEquals(ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET, item.getYTileHeightOffset());
    }

    @Test
    void testMakeWithIdAndData() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, DATA, UUID);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character, String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(UUID, item.uuid());
        assertSame(ITEM_TYPE, item.type());
        assertSame(DATA, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(GENERATED_UUID, takenFromStack.uuid());
        assertEquals(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET, item.getXTileWidthOffset());
        assertEquals(ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET, item.getYTileHeightOffset());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(null, DATA));

        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(null, DATA, UUID));
        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(ITEM_TYPE, DATA, null));
    }
}