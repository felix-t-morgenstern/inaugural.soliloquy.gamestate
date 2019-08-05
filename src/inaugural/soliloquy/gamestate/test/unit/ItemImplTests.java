package inaugural.soliloquy.gamestate.test.unit;

import inaugural.soliloquy.gamestate.ItemImpl;
import inaugural.soliloquy.gamestate.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class ItemImplTests {
    private Item _item;

    private final EntityUuid ID = new EntityUuidStub();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();

    private final CharacterEquipmentSlots CHARACTER_EQUIPMENT_SLOTS =
            new CharacterEquipmentSlotsStub(new CharacterStub());
    private final String CHARACTER_EQUIPMENT_SLOT_TYPE = "slotType";
    private final CharacterInventory CHARACTER_INVENTORY =
            new CharacterInventoryStub(new CharacterStub());
    private final TileItems TILE_ITEMS = new TileItemsStub();
    private final TileFixtureItems TILE_FIXTURE_ITEMS = new TileFixtureItemsStub(null);

    @BeforeEach
    void setUp() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = null;
        CharacterInventoryStub.OVERRIDE_CONTAINS = null;
        _item = new ItemImpl(ID, ITEM_TYPE, DATA, PAIR_FACTORY, ENTITY_UUID_FACTORY);
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
    void testItemType() {
        assertSame(ITEM_TYPE, _item.itemType());
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
        assertEquals(DATA, takenFromStack.data());
        assertEquals((Integer) 7, takenFromStack.getNumberInStack());
        assertEquals((Integer) 3, _item.getNumberInStack());
    }

    @Test
    void testAssignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        CharacterEquipmentSlots characterEquipmentSlots =
                new CharacterEquipmentSlotsStub(new CharacterStub());

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                characterEquipmentSlots, CHARACTER_EQUIPMENT_SLOT_TYPE);

        Pair<CharacterEquipmentSlots, String> equipmentSlot = _item.getCharacterEquipmentSlot();

        assertNotNull(equipmentSlot);
        assertSame(characterEquipmentSlots, equipmentSlot.getItem1());
        assertSame(CHARACTER_EQUIPMENT_SLOT_TYPE, equipmentSlot.getItem2());
    }

    @Test
    void testAssignCharacterInventoryToItemAfterAddingToCharacterInventory() {
        CharacterInventoryStub.OVERRIDE_CONTAINS = true;

        _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER_INVENTORY);

        assertSame(CHARACTER_INVENTORY, _item.getCharacterInventory());
    }

    @Test
    void testAssignTileItemsToItemAfterAddingItemToTileItems() {
        TILE_ITEMS.add(_item);

        _item.assignTileItemsToItemAfterAddingItemToTileItems(TILE_ITEMS);

        assertSame(TILE_ITEMS, _item.getTileItems());
    }

    @Test
    void testAssignTileFixtureToItemAfterAddingItemToTileFixtureItems() {
        TileFixtureItems tileFixtureItems = new TileFixtureItemsStub(null);

        _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(tileFixtureItems);

        assertSame(tileFixtureItems, _item.getTileFixtureItems());
    }

    @Test
    void testAssignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlotNullifiesOtherAssignments() {
        CHARACTER_INVENTORY.add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.getCharacterInventory());

        TILE_FIXTURE_ITEMS.add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.getTileFixtureItems());

        TILE_ITEMS.add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.getTileItems());
    }

    @Test
    void testAssignCharacterInventoryToItemAfterAddingToCharacterInventoryNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.getCharacterEquipmentSlot());

        TILE_FIXTURE_ITEMS.add(_item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.getCharacterEquipmentSlot());

        TILE_ITEMS.add(_item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.getCharacterEquipmentSlot());
    }

    @Test
    void testAssignTileFixtureToItemAfterAddingItemToTileFixtureItemsNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        TILE_FIXTURE_ITEMS.add(_item);
        assertNull(_item.getCharacterEquipmentSlot());

        CHARACTER_INVENTORY.add(_item);
        TILE_FIXTURE_ITEMS.add(_item);
        assertNull(_item.getCharacterInventory());

        TILE_ITEMS.add(_item);
        TILE_FIXTURE_ITEMS.add(_item);
        assertNull(_item.getTileItems());
    }

    @Test
    void testAssignTileItemsToItemAfterAddingItemToTileItemsNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        TILE_ITEMS.add(_item);
        assertNull(_item.getCharacterEquipmentSlot());

        CHARACTER_INVENTORY.add(_item);
        TILE_ITEMS.add(_item);
        assertNull(_item.getCharacterInventory());

        TILE_FIXTURE_ITEMS.add(_item);
        TILE_ITEMS.add(_item);
        assertNull(_item.getTileFixtureItems());
    }

    @Test
    void testAssignToNullCharacterEquipmentSlotsNullifiesGetEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                null, CHARACTER_EQUIPMENT_SLOT_TYPE);

        assertNull(_item.getCharacterEquipmentSlot());
    }

    @Test
    void testAssignToNullEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER_EQUIPMENT_SLOTS, null);

        assertNull(_item.getCharacterEquipmentSlot());
    }

    @Test
    void testAssignToEmptyEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER_EQUIPMENT_SLOTS, "");

        assertNull(_item.getCharacterEquipmentSlot());
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
        int originalCharacterInventorySize = CharacterInventoryStub.ITEMS.size();

        _item.delete();

        assertFalse(CHARACTER_INVENTORY.contains(_item));
        assertFalse(CharacterInventoryStub.ITEMS.contains(_item));
        assertEquals(originalCharacterInventorySize - 1, CharacterInventoryStub.ITEMS.size());
    }

    @Test
    void testDeleteRemovesItemFromTileFixtureItems() {
    }

    @Test
    void testDeleteRemovesItemFromTileItems() {
    }

    @Test
    void testDeletionInvariant() {
        _item.delete();
        assertThrows(IllegalStateException.class, () -> _item.itemType());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getCharacterInventory());
        assertThrows(IllegalStateException.class, () -> _item.getTileFixtureItems());
        assertThrows(IllegalStateException.class, () -> _item.getTileItems());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(
                        CHARACTER_INVENTORY));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
                        TILE_FIXTURE_ITEMS));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileItemsToItemAfterAddingItemToTileItems(
                        TILE_ITEMS));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> _item.delete());
    }

    @Test
    void testItemNotFoundInCharacterEquipmentSlotInvariant() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        CharacterEquipmentSlotsStub.EQUIPMENT_SLOTS.removeByKey(CHARACTER_EQUIPMENT_SLOT_TYPE);

        assertThrows(IllegalStateException.class, () -> _item.itemType());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getCharacterInventory());
        assertThrows(IllegalStateException.class, () -> _item.getTileFixtureItems());
        assertThrows(IllegalStateException.class, () -> _item.getTileItems());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(
                        CHARACTER_INVENTORY));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
                        TILE_FIXTURE_ITEMS));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileItemsToItemAfterAddingItemToTileItems(
                        TILE_ITEMS));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }

    @Test
    void testItemNotFoundInCharacterInventoryInvariant() {
        CHARACTER_INVENTORY.add(_item);
        CharacterInventoryStub.ITEMS.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.itemType());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getCharacterInventory());
        assertThrows(IllegalStateException.class, () -> _item.getTileFixtureItems());
        assertThrows(IllegalStateException.class, () -> _item.getTileItems());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(
                        CHARACTER_INVENTORY));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
                        TILE_FIXTURE_ITEMS));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileItemsToItemAfterAddingItemToTileItems(
                        TILE_ITEMS));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }

    @Test
    void testItemNotFoundInTileItemsInvariant() {
        TILE_ITEMS.add(_item);
        TileItemsStub.ITEMS.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.itemType());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getCharacterInventory());
        assertThrows(IllegalStateException.class, () -> _item.getTileFixtureItems());
        assertThrows(IllegalStateException.class, () -> _item.getTileItems());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(
                        CHARACTER_INVENTORY));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
                        TILE_FIXTURE_ITEMS));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileItemsToItemAfterAddingItemToTileItems(
                        TILE_ITEMS));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }

    @Test
    void testItemNotFoundInTileFixtureItemsInvariant() {
        TILE_ITEMS.add(_item);
        TileItemsStub.ITEMS.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.itemType());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getCharacterInventory());
        assertThrows(IllegalStateException.class, () -> _item.getTileFixtureItems());
        assertThrows(IllegalStateException.class, () -> _item.getTileItems());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER_EQUIPMENT_SLOTS, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(
                        CHARACTER_INVENTORY));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
                        TILE_FIXTURE_ITEMS));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileItemsToItemAfterAddingItemToTileItems(
                        TILE_ITEMS));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }
}
