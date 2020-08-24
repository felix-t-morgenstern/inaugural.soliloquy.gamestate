package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.ItemImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.EntityUuidFactoryStub;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class ItemImplTests {
    private Item _item;

    private final EntityUuid ID = new FakeEntityUuid("64fd6bf1-4e57-492a-a8fb-5f1494f7ddf0");
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final VariableCache DATA = new VariableCacheStub();
    private final PairFactory PAIR_FACTORY = new FakePairFactory();
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();

    private final Character CHARACTER = new FakeCharacter();
    private final CharacterEquipmentSlots CHARACTER_EQUIPMENT_SLOTS =
            ((FakeCharacter) CHARACTER).EQUIPMENT;
    private final String CHARACTER_EQUIPMENT_SLOT_TYPE = "slotType";
    private final CharacterInventory CHARACTER_INVENTORY = ((FakeCharacter) CHARACTER).INVENTORY;
    private final Tile TILE = new FakeTile();
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();

    @BeforeEach
    void setUp() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = null;
        FakeCharacterInventory.OVERRIDE_CONTAINS = null;
        _item = new ItemImpl(ID, ITEM_TYPE, DATA, PAIR_FACTORY, ENTITY_UUID_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(null, ITEM_TYPE, DATA,
                PAIR_FACTORY, ENTITY_UUID_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(ID, null, DATA,
                PAIR_FACTORY, ENTITY_UUID_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(ID, ITEM_TYPE, null,
                PAIR_FACTORY, ENTITY_UUID_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(ID, ITEM_TYPE, DATA,
                null, ENTITY_UUID_FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(ID, ITEM_TYPE, DATA,
                PAIR_FACTORY, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Item.class.getCanonicalName(), _item.getInterfaceName());
    }

    @Test
    void testEquals() {
        Item item2 = new ItemImpl(ID, ITEM_TYPE, DATA, PAIR_FACTORY, ENTITY_UUID_FACTORY);

        assertEquals(_item, item2);
    }

    @Test
    void testType() {
        assertSame(ITEM_TYPE, _item.type());
    }

    @Test
    void testSetAndGetCharges() {
        _item.setCharges(123);

        assertEquals(123, (int) _item.getCharges());
    }

    @Test
    void testSetAndGetChargesWhenHasChargesIsFalse() {
        ItemTypeStub._hasCharges = false;

        assertThrows(UnsupportedOperationException.class, () -> _item.setCharges(123));
        assertNull(_item.getCharges());
    }

    @Test
    void testSetNegativeCharges() {
        assertThrows(IllegalArgumentException.class, () -> _item.setCharges(-1));
    }

    @Test
    void testSetAndGetItemsInStack() {
        ItemTypeStub._isStackable = true;
        _item.setNumberInStack(123);

        assertEquals(123, (int) _item.getNumberInStack());
    }

    @Test
    void testSetAndGetNumberInStackWhenIsStackableIsFalse() {
        ItemTypeStub._isStackable = false;

        assertThrows(UnsupportedOperationException.class, () -> _item.setNumberInStack(123));
        assertNull(_item.getNumberInStack());
    }

    @Test
    void testId() {
        assertEquals(ID, _item.id());
    }

    @Test
    void testData() {
        assertSame(DATA, _item.data());
    }

    @Test
    void testInvalidNumberFromStack() {
        ItemTypeStub._isStackable = true;
        _item.setNumberInStack(2);

        assertThrows(IllegalArgumentException.class, () -> _item.takeFromStack(0));
        assertThrows(IllegalArgumentException.class, () -> _item.takeFromStack(2));
    }

    @Test
    void testTakeFromStackWhenNotStackable() {
        ItemTypeStub._isStackable = false;

        assertThrows(UnsupportedOperationException.class, () -> _item.takeFromStack(1));
    }

    @Test
    void testTakeFromStack() {
        ItemTypeStub._isStackable = true;
        _item.setNumberInStack(10);

        Item takenFromStack = _item.takeFromStack(7);

        assertNotNull(takenFromStack);
        assertSame(EntityUuidFactoryStub.RANDOM_ENTITY_UUID, takenFromStack.id());
        assertSame(((VariableCacheStub)DATA)._cloneResult, takenFromStack.data());
        assertEquals((Integer) 7, takenFromStack.getNumberInStack());
        assertEquals((Integer) 3, _item.getNumberInStack());
    }

    @Test
    void testAssignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        Character character = new FakeCharacter();

        _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                character, CHARACTER_EQUIPMENT_SLOT_TYPE);

        Pair<Character, String> equipmentSlot = _item.equipmentSlot();

        assertNotNull(equipmentSlot);
        assertSame(character, equipmentSlot.getItem1());
        assertSame(CHARACTER_EQUIPMENT_SLOT_TYPE, equipmentSlot.getItem2());
    }

    @Test
    void testAssignCharacterInventoryToItemAfterAddingToCharacterInventory() {
        FakeCharacterInventory.OVERRIDE_CONTAINS = true;

        _item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER);

        assertSame(CHARACTER, _item.inventoryCharacter());
    }

    @Test
    void testAssignTileAfterAddedToTileEntitiesOfType() {
        TILE.items().add(_item);

        _item.assignTileAfterAddedToTileEntitiesOfType(TILE);

        assertSame(TILE, _item.tile());
    }

    @Test
    void testAssignTileFixtureAfterAddedItemToTileFixtureItems() {
        FakeTileFixtureItems tileFixtureItems = new FakeTileFixtureItems(null);

        _item.assignTileFixtureAfterAddedItemToTileFixtureItems(
                tileFixtureItems.TILE_FIXTURE);

        assertSame(tileFixtureItems.TILE_FIXTURE, _item.tileFixture());
    }

    @Test
    void testAssignEquipmentSlotToItemAfterAddedToEquipmentSlotNullifiesOtherAssignments() {
        CHARACTER_INVENTORY.add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.inventoryCharacter());

        TILE_FIXTURE.items().add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.tileFixture());

        TILE.items().add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.tile());
    }

    @Test
    void testAssignInventoryCharacterToItemAfterAddedToInventoryNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.equipmentSlot());

        TILE_FIXTURE.items().add(_item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.equipmentSlot());

        TILE.items().add(_item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.equipmentSlot());
    }

    @Test
    void testAssignTileFixtureToItemAfterAddingItemToTileFixtureItemsNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        TILE_FIXTURE.items().add(_item);
        assertNull(_item.equipmentSlot());

        CHARACTER_INVENTORY.add(_item);
        TILE_FIXTURE.items().add(_item);
        assertNull(_item.inventoryCharacter());

        TILE.items().add(_item);
        TILE_FIXTURE.items().add(_item);
        assertNull(_item.tile());
    }

    @Test
    void testAssignTileToItemAfterAddedToTileEntitiesNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        TILE.items().add(_item);
        assertNull(_item.equipmentSlot());

        CHARACTER_INVENTORY.add(_item);
        TILE.items().add(_item);
        assertNull(_item.inventoryCharacter());

        TILE_FIXTURE.items().add(_item);
        TILE.items().add(_item);
        assertNull(_item.tileFixture());
    }

    @Test
    void testAssignToNullCharacterEquipmentSlotsNullifiesGetEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                null, CHARACTER_EQUIPMENT_SLOT_TYPE);

        assertNull(_item.equipmentSlot());
    }

    @Test
    void testAssignToNullEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, null);

        assertNull(_item.equipmentSlot());
    }

    @Test
    void testAssignToEmptyEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, "");

        assertNull(_item.equipmentSlot());
    }

    @Test
    void testCharacterEquipmentSlotAssignmentInvariant() {
        CHARACTER_EQUIPMENT_SLOTS.addCharacterEquipmentSlot(CHARACTER_EQUIPMENT_SLOT_TYPE);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
    }

    @Test
    void testDelete() {
        _item.delete();

        assertTrue(_item.isDeleted());
    }

    @Test
    void testSetAndGetName() {
        String name = "ItemName";

        _item.setName(name);

        assertEquals(name, _item.getName());
    }

    @Test
    void testGetNameWithNullItemImplNameReturnsItemTypeName() {
        _item.setName(null);

        assertEquals(ItemTypeStub.ITEM_TYPE_NAME, _item.getName());
    }

    @Test
    void testGetNameWithEmptyItemImplNameReturnsItemTypeName() {
        _item.setName("");

        assertEquals(ItemTypeStub.ITEM_TYPE_NAME, _item.getName());
    }

    @Test
    void testSetAndGetPluralName() {
        String name = "ItemPluralName";

        _item.setPluralName(name);

        assertEquals(name, _item.getPluralName());
    }

    @Test
    void testGetNameWithNullItemImplPluralNameReturnsItemTypePluralName() {
        _item.setPluralName(null);

        assertEquals(ItemTypeStub.ITEM_TYPE_PLURAL_NAME, _item.getPluralName());
    }

    @Test
    void testGetNameWithEmptyItemImplPluralNameReturnsItemTypePluralName() {
        _item.setPluralName("");

        assertEquals(ItemTypeStub.ITEM_TYPE_PLURAL_NAME, _item.getPluralName());
    }

    @Test
    void testDeleteRemovesItemFromCharacterEquipmentSlot() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        assertSame(_item, CHARACTER_EQUIPMENT_SLOTS.itemInSlot(CHARACTER_EQUIPMENT_SLOT_TYPE));

        _item.delete();

        assertNull(CHARACTER_EQUIPMENT_SLOTS.itemInSlot(CHARACTER_EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testDeleteRemovesItemFromCharacterInventory() {
        CHARACTER_INVENTORY.add(_item);
        assertTrue(CHARACTER_INVENTORY.contains(_item));
        int originalCharacterInventorySize =
                ((FakeCharacterInventory)CHARACTER_INVENTORY).ITEMS.size();

        _item.delete();

        assertFalse(CHARACTER_INVENTORY.contains(_item));
        assertFalse(((FakeCharacterInventory)CHARACTER_INVENTORY).ITEMS.contains(_item));
        assertEquals(originalCharacterInventorySize - 1,
                ((FakeCharacterInventory)CHARACTER_INVENTORY).ITEMS.size());
    }

    @Test
    void testDeleteRemovesItemFromTileFixtureItems() {
        TILE_FIXTURE.items().add(_item);
        assertTrue(TILE_FIXTURE.items().contains(_item));
        int originalTileFixtureSize = ((FakeTileFixtureItems)TILE_FIXTURE.items())._items.size();

        _item.delete();

        assertFalse(TILE_FIXTURE.items().contains(_item));
        assertFalse(((FakeTileFixtureItems)TILE_FIXTURE.items())._items.contains(_item));
        assertEquals(originalTileFixtureSize - 1,
                ((FakeTileFixtureItems)TILE_FIXTURE.items())._items.size());
    }

    @Test
    void testDeleteRemovesItemFromTileItems() {
        TILE.items().add(_item);
        assertTrue(TILE.items().contains(_item));
        int originalTileFixtureSize = ((FakeTileEntities) TILE.items()).ENTITIES.size();

        _item.delete();

        assertFalse(TILE.items().contains(_item));
        assertFalse(((FakeTileEntities) TILE.items()).ENTITIES.containsKey(_item));
        assertEquals(originalTileFixtureSize - 1,
                ((FakeTileEntities) TILE.items()).ENTITIES.size());
    }

    @Test
    void testDeletionInvariant() {
        _item.delete();

        assertThrows(EntityDeletedException.class, () -> _item.type());
        assertThrows(EntityDeletedException.class, () -> _item.getCharges());
        assertThrows(EntityDeletedException.class, () -> _item.setCharges(1));
        assertThrows(EntityDeletedException.class, () -> _item.getNumberInStack());
        assertThrows(EntityDeletedException.class, () -> _item.setNumberInStack(1));
        assertThrows(EntityDeletedException.class, () -> _item.takeFromStack(1));
        assertThrows(EntityDeletedException.class, () -> _item.equipmentSlot());
        assertThrows(EntityDeletedException.class, () -> _item.inventoryCharacter());
        assertThrows(EntityDeletedException.class, () -> _item.tile());
        assertThrows(EntityDeletedException.class, () -> _item.tileFixture());
        assertThrows(EntityDeletedException.class, () ->
                _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class, () ->
                _item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(EntityDeletedException.class, () ->
                _item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(EntityDeletedException.class, () ->
                _item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(EntityDeletedException.class, () -> _item.getName());
        assertThrows(EntityDeletedException.class, () -> _item.setName(""));
        assertThrows(EntityDeletedException.class, () -> _item.getPluralName());
        assertThrows(EntityDeletedException.class, () -> _item.setPluralName(""));
        assertThrows(EntityDeletedException.class, () -> _item.delete());
    }

    @Test
    void testItemNotFoundInCharacterEquipmentSlotInvariant() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        FakeCharacterEquipmentSlots.EQUIPMENT_SLOTS.removeByKey(CHARACTER_EQUIPMENT_SLOT_TYPE);

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.tile());
        assertThrows(IllegalStateException.class, () -> _item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> _item.delete());
    }

    @Test
    void testItemNotFoundInCharacterInventoryInvariant() {
        CHARACTER_INVENTORY.add(_item);
        ((FakeCharacterInventory)CHARACTER_INVENTORY).ITEMS.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.tile());
        assertThrows(IllegalStateException.class, () -> _item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> _item.delete());
    }

    @Test
    void testItemNotFoundInTileItemsInvariant() {
        TILE.items().add(_item);
        ((FakeTileEntities) TILE.items()).ENTITIES.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.tile());
        assertThrows(IllegalStateException.class, () -> _item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> _item.delete());
    }

    @Test
    void testItemNotFoundInTileFixtureItemsInvariant() {
        FakeTileFixture tileFixture = new FakeTileFixture();
        tileFixture.items().add(_item);
        ((FakeTileFixtureItems)tileFixture.items())._items.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.tile());
        assertThrows(IllegalStateException.class, () -> _item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> _item.delete());
    }
}
