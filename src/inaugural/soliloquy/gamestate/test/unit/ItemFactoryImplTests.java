package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.ItemFactoryImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryImplTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final EntityUuid ID = new EntityUuidStub();
    private final GenericParamsSet GENERIC_PARAMS_SET = new GenericParamsSetStub();

    private ItemFactory _itemFactory;

    @BeforeEach
    void setUp() {
        _itemFactory = new ItemFactoryImpl(ENTITY_UUID_FACTORY, GENERIC_PARAMS_SET_FACTORY,
                PAIR_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(null,
                GENERIC_PARAMS_SET_FACTORY, PAIR_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(ENTITY_UUID_FACTORY,
                null, PAIR_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(ENTITY_UUID_FACTORY,
                GENERIC_PARAMS_SET_FACTORY, null));
    }

    @Test
    void testGetInterface() {
        assertEquals(ItemFactory.class.getCanonicalName(), _itemFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Character character = new CharacterStub();
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
        assertSame(GenericParamsSetFactoryStub.GENERIC_PARAMS_SET, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
    }

    @Test
    void testMakeWithData() {
        Character character = new CharacterStub();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, GENERIC_PARAMS_SET);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character,String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, item.id());
        assertSame(ITEM_TYPE, item.type());
        assertSame(GENERIC_PARAMS_SET, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
    }

    @Test
    void testMakeWithId() {
        Character character = new CharacterStub();
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
        assertSame(GenericParamsSetFactoryStub.GENERIC_PARAMS_SET, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
    }

    @Test
    void testMakeWithIdAndData() {
        Character character = new CharacterStub();
        String equipmentSlotType = "equipmentSlotType";
        Item item = _itemFactory.make(ITEM_TYPE, GENERIC_PARAMS_SET, ID);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character,String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertSame(ID, item.id());
        assertSame(ITEM_TYPE, item.type());
        assertSame(GENERIC_PARAMS_SET, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.getItem1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.getItem2());
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(null, GENERIC_PARAMS_SET));

        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(null, GENERIC_PARAMS_SET, ID));
        assertThrows(IllegalArgumentException.class,
                () -> _itemFactory.make(ITEM_TYPE, GENERIC_PARAMS_SET, null));
    }
}
