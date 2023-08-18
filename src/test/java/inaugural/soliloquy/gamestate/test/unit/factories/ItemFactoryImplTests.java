package inaugural.soliloquy.gamestate.test.unit.factories;

import inaugural.soliloquy.gamestate.factories.ItemFactoryImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.factories.ItemFactory;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;

public class ItemFactoryImplTests {
    private final VariableCacheFactory DATA_FACTORY = new FakeVariableCacheFactory();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final UUID UUID = java.util.UUID.randomUUID();
    private final VariableCache DATA = new VariableCacheStub();

    private ItemFactory itemFactory;

    @Before
    public void setUp() {
        itemFactory = new ItemFactoryImpl(DATA_FACTORY);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ItemFactoryImpl(null));
    }

    @Test
    public void testGetInterface() {
        assertEquals(ItemFactory.class.getCanonicalName(), itemFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        Character character = new FakeCharacter();
        String equipmentSlotType = randomString();
        Item item = itemFactory.make(ITEM_TYPE, null);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character, String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertNotNull(item.uuid());
        assertSame(ITEM_TYPE, item.type());
        assertSame(((FakeVariableCacheFactory) DATA_FACTORY).Created.get(0), item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.item1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.item2());
        assertNotSame(item.uuid(), takenFromStack.uuid());
        assertEquals(Vertex.of(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET,
                ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET), item.getTileOffset());
    }

    @Test
    public void testMakeWithData() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = itemFactory.make(ITEM_TYPE, DATA);
        character.equipmentSlots().addCharacterEquipmentSlot(equipmentSlotType);
        character.equipmentSlots().equipItemToSlot(equipmentSlotType, item);
        Pair<Character, String> characterEquipmentSlot = item.equipmentSlot();
        item.setNumberInStack(10);
        Item takenFromStack = item.takeFromStack(5);

        assertNotNull(item);
        assertNotNull(item.uuid());
        assertSame(ITEM_TYPE, item.type());
        assertSame(DATA, item.data());
        assertNotNull(characterEquipmentSlot);
        assertSame(character, characterEquipmentSlot.item1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.item2());
        assertNotSame(item.uuid(), takenFromStack.uuid());
        assertEquals(Vertex.of(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET,
                ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET), item.getTileOffset());
    }

    @Test
    public void testMakeWithId() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = itemFactory.make(ITEM_TYPE, null, UUID);
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
        assertSame(character, characterEquipmentSlot.item1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.item2());
        assertNotSame(item.uuid(), takenFromStack.uuid());
        assertEquals(Vertex.of(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET,
                ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET), item.getTileOffset());
    }

    @Test
    public void testMakeWithIdAndData() {
        Character character = new FakeCharacter();
        String equipmentSlotType = "equipmentSlotType";
        Item item = itemFactory.make(ITEM_TYPE, DATA, UUID);
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
        assertSame(character, characterEquipmentSlot.item1());
        assertEquals(equipmentSlotType, characterEquipmentSlot.item2());
        assertNotSame(item.uuid(), takenFromStack.uuid());
        assertEquals(Vertex.of(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET,
                ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET), item.getTileOffset());
    }

    @Test
    public void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> itemFactory.make(null, DATA));

        assertThrows(IllegalArgumentException.class,
                () -> itemFactory.make(null, DATA, UUID));
        assertThrows(IllegalArgumentException.class,
                () -> itemFactory.make(ITEM_TYPE, DATA, null));
    }
}
