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
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.ItemType;

import static org.junit.jupiter.api.Assertions.*;

class ItemImplTests {
    private Item _item;

    private final EntityUuid ID = new EntityUuidStub();
    private final ItemType ITEM_TYPE = new ItemTypeStub();
    private final GenericParamsSet DATA = new GenericParamsSetStub();
    private final PairFactory PAIR_FACTORY = new PairFactoryStub();
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();

    private final Character CHARACTER = new CharacterStub();
    private final CharacterEquipmentSlots CHARACTER_EQUIPMENT_SLOTS =
            ((CharacterStub) CHARACTER).EQUIPMENT;
    private final String CHARACTER_EQUIPMENT_SLOT_TYPE = "slotType";
    private final CharacterInventory CHARACTER_INVENTORY = ((CharacterStub) CHARACTER).INVENTORY;
    private final Tile TILE = new TileStub();
    private final TileFixture TILE_FIXTURE = new TileFixtureStub();

    @BeforeEach
    void setUp() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = null;
        CharacterInventoryStub.OVERRIDE_CONTAINS = null;
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
        assertEquals(DATA, takenFromStack.data());
        assertEquals((Integer) 7, takenFromStack.getNumberInStack());
        assertEquals((Integer) 3, _item.getNumberInStack());
    }

    @Test
    void testAssignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        Character character = new CharacterStub();

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                character, CHARACTER_EQUIPMENT_SLOT_TYPE);

        Pair<Character, String> equipmentSlot = _item.getCharacterEquipmentSlot();

        assertNotNull(equipmentSlot);
        assertSame(character, equipmentSlot.getItem1());
        assertSame(CHARACTER_EQUIPMENT_SLOT_TYPE, equipmentSlot.getItem2());
    }

    @Test
    void testAssignCharacterInventoryToItemAfterAddingToCharacterInventory() {
        CharacterInventoryStub.OVERRIDE_CONTAINS = true;

        _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER);

        assertSame(CHARACTER, _item.getInventoryCharacter());
    }

    @Test
    void testAssignTileItemsToItemAfterAddingItemToTileItems() {
        TILE.items().add(_item);

        _item.assignTileToItemAfterAddingItemToTileItems(TILE);

        assertSame(TILE, _item.getContainingTile());
    }

    @Test
    void testAssignTileFixtureToItemAfterAddingItemToTileFixtureItems() {
        TileFixtureItemsStub tileFixtureItems = new TileFixtureItemsStub(null);

        _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
                tileFixtureItems.TILE_FIXTURE);

        assertSame(tileFixtureItems.TILE_FIXTURE, _item.getContainingTileFixture());
    }

    @Test
    void testAssignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlotNullifiesOtherAssignments() {
        CHARACTER_INVENTORY.add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.getInventoryCharacter());

        TILE_FIXTURE.items().add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.getContainingTileFixture());

        TILE.items().add(_item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertNull(_item.getContainingTile());
    }

    @Test
    void testAssignCharacterInventoryToItemAfterAddingToCharacterInventoryNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.getCharacterEquipmentSlot());

        TILE_FIXTURE.items().add(_item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.getCharacterEquipmentSlot());

        TILE.items().add(_item);
        CHARACTER_INVENTORY.add(_item);

        assertNull(_item.getCharacterEquipmentSlot());
    }

    @Test
    void testAssignTileFixtureToItemAfterAddingItemToTileFixtureItemsNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        TILE_FIXTURE.items().add(_item);
        assertNull(_item.getCharacterEquipmentSlot());

        CHARACTER_INVENTORY.add(_item);
        TILE_FIXTURE.items().add(_item);
        assertNull(_item.getInventoryCharacter());

        TILE.items().add(_item);
        TILE_FIXTURE.items().add(_item);
        assertNull(_item.getContainingTile());
    }

    @Test
    void testAssignTileItemsToItemAfterAddingItemToTileItemsNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);
        TILE.items().add(_item);
        assertNull(_item.getCharacterEquipmentSlot());

        CHARACTER_INVENTORY.add(_item);
        TILE.items().add(_item);
        assertNull(_item.getInventoryCharacter());

        TILE_FIXTURE.items().add(_item);
        TILE.items().add(_item);
        assertNull(_item.getContainingTileFixture());
    }

    @Test
    void testAssignToNullCharacterEquipmentSlotsNullifiesGetEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                null, CHARACTER_EQUIPMENT_SLOT_TYPE);

        assertNull(_item.getCharacterEquipmentSlot());
    }

    @Test
    void testAssignToNullEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER, null);

        assertNull(_item.getCharacterEquipmentSlot());
    }

    @Test
    void testAssignToEmptyEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        CharacterEquipmentSlotsStub.ITEM_IN_SLOT_RESULT_OVERRIDE = _item;

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                CHARACTER, "");

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
        assertEquals(originalCharacterInventorySize - 1,
                CharacterInventoryStub.ITEMS.size());
    }

    @Test
    void testDeleteRemovesItemFromTileFixtureItems() {
        TILE_FIXTURE.items().add(_item);
        assertTrue(TILE_FIXTURE.items().contains(_item));
        int originalTileFixtureSize = TileFixtureItemsStub.ITEMS.size();

        _item.delete();

        assertFalse(TILE_FIXTURE.items().contains(_item));
        assertFalse(TileFixtureItemsStub.ITEMS.contains(_item));
        assertEquals(originalTileFixtureSize - 1, TileFixtureItemsStub.ITEMS.size());
    }

    @Test
    void testDeleteRemovesItemFromTileItems() {
        TILE.items().add(_item);
        assertTrue(TILE.items().contains(_item));
        int originalTileFixtureSize = TileItemsStub.ITEMS.size();

        _item.delete();

        assertFalse(TILE.items().contains(_item));
        assertFalse(TileItemsStub.ITEMS.contains(_item));
        assertEquals(originalTileFixtureSize - 1, TileItemsStub.ITEMS.size());
    }

    @Test
    void testDeletionInvariant() {
        _item.delete();

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getInventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTile());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileToItemAfterAddingItemToTileItems(TILE));
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

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getInventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTile());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileToItemAfterAddingItemToTileItems(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }

    @Test
    void testItemNotFoundInCharacterInventoryInvariant() {
        CHARACTER_INVENTORY.add(_item);
        CharacterInventoryStub.ITEMS.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getInventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTile());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileToItemAfterAddingItemToTileItems(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }

    @Test
    void testItemNotFoundInTileItemsInvariant() {
        TILE.items().add(_item);
        TileItemsStub.ITEMS.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getInventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTile());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileToItemAfterAddingItemToTileItems(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }

    @Test
    void testItemNotFoundInTileFixtureItemsInvariant() {
        new TileFixtureStub().items().add(_item);
        TileFixtureItemsStub.ITEMS.remove(_item);

        assertThrows(IllegalStateException.class, () -> _item.type());
        assertThrows(IllegalStateException.class, () -> _item.getCharges());
        assertThrows(IllegalStateException.class, () -> _item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> _item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> _item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> _item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> _item.getCharacterEquipmentSlot());
        assertThrows(IllegalStateException.class, () -> _item.getInventoryCharacter());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTile());
        assertThrows(IllegalStateException.class, () -> _item.getContainingTileFixture());
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignCharacterInventoryToItemAfterAddingToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                _item.assignTileToItemAfterAddingItemToTileItems(TILE));
        assertThrows(IllegalStateException.class, () -> _item.getName());
        assertThrows(IllegalStateException.class, () -> _item.setName(""));
        assertThrows(IllegalStateException.class, () -> _item.getPluralName());
        assertThrows(IllegalStateException.class, () -> _item.setPluralName(""));
    }

    @Test
    void testItemIsPresentElsewhere() {
        assertFalse(ItemImpl.itemIsPresentElsewhere(_item));

        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, _item);

        assertTrue(ItemImpl.itemIsPresentElsewhere(_item));

        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, null);

        assertFalse(ItemImpl.itemIsPresentElsewhere(_item));

        CHARACTER_INVENTORY.add(_item);

        assertTrue(ItemImpl.itemIsPresentElsewhere(_item));

        CHARACTER_INVENTORY.remove(_item);

        assertFalse(ItemImpl.itemIsPresentElsewhere(_item));

        TILE_FIXTURE.items().add(_item);

        assertTrue(ItemImpl.itemIsPresentElsewhere(_item));

        TILE_FIXTURE.items().remove(_item);

        assertFalse(ItemImpl.itemIsPresentElsewhere(_item));

        TILE.items().add(_item);

        assertTrue(ItemImpl.itemIsPresentElsewhere(_item));

        TILE.items().remove(_item);

        assertFalse(ItemImpl.itemIsPresentElsewhere(_item));
    }
}
