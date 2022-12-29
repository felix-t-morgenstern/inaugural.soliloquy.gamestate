package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.ItemImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.ItemTypeStub;
import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemImplTests {
    private final UUID UUID = java.util.UUID.randomUUID();
    private final VariableCache DATA = new VariableCacheStub();

    private final Character CHARACTER = new FakeCharacter();
    private final CharacterEquipmentSlots CHARACTER_EQUIPMENT_SLOTS =
            ((FakeCharacter) CHARACTER).EQUIPMENT;
    private final String CHARACTER_EQUIPMENT_SLOT_TYPE = randomString();
    private final CharacterInventory CHARACTER_INVENTORY = ((FakeCharacter) CHARACTER).INVENTORY;
    private final Tile TILE = new FakeTile();
    private final TileFixture TILE_FIXTURE = new FakeTileFixture();

    private ItemTypeStub itemTypeStub;

    private Item item;

    @BeforeEach
    void setUp() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = null;
        FakeCharacterInventory.OVERRIDE_CONTAINS = null;
        itemTypeStub = new ItemTypeStub();
        item = new ItemImpl(UUID, itemTypeStub, DATA);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(null, itemTypeStub, DATA));
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(UUID, null, DATA));
        assertThrows(IllegalArgumentException.class, () -> new ItemImpl(UUID, itemTypeStub, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Item.class.getCanonicalName(), item.getInterfaceName());
    }

    @Test
    void testEquals() {
        Item item2 = new ItemImpl(UUID, itemTypeStub, DATA);

        assertEquals(item, item2);
    }

    @Test
    void testType() {
        assertSame(itemTypeStub, item.type());
    }

    @Test
    void testSetAndGetCharges() {
        int charges = randomIntWithInclusiveFloor(1);

        item.setCharges(charges);

        assertEquals(charges, (int) item.getCharges());
    }

    @Test
    void testSetAndGetChargesWhenHasChargesIsFalse() {
        itemTypeStub.HasCharges = false;

        assertThrows(UnsupportedOperationException.class, () ->
                item.setCharges(randomIntWithInclusiveFloor(1)));
        assertNull(item.getCharges());
    }

    @Test
    void testSetNegativeCharges() {
        assertThrows(IllegalArgumentException.class, () -> item.setCharges(-1));
    }

    @Test
    void testSetAndGetItemsInStack() {
        itemTypeStub.IsStackable = true;
        int itemsInStack = randomIntWithInclusiveFloor(1);
        item.setNumberInStack(itemsInStack);

        assertEquals(itemsInStack, (int) item.getNumberInStack());
    }

    @Test
    void testSetAndGetNumberInStackWhenIsStackableIsFalse() {
        itemTypeStub.IsStackable = false;

        assertThrows(UnsupportedOperationException.class, () -> item.setNumberInStack(123));
        assertNull(item.getNumberInStack());
    }

    @Test
    void testUuid() {
        assertEquals(UUID, item.uuid());
    }

    @Test
    void testData() {
        assertSame(DATA, item.data());
    }

    @Test
    void testInvalidNumberFromStack() {
        itemTypeStub.IsStackable = true;
        item.setNumberInStack(2);

        assertThrows(IllegalArgumentException.class, () -> item.takeFromStack(0));
        assertThrows(IllegalArgumentException.class, () -> item.takeFromStack(2));
    }

    @Test
    void testTakeFromStackWhenNotStackable() {
        itemTypeStub.IsStackable = false;

        assertThrows(UnsupportedOperationException.class, () -> item.takeFromStack(1));
    }

    @Test
    void testTakeFromStack() {
        itemTypeStub.IsStackable = true;
        int numberInStack = randomIntWithInclusiveFloor(2);
        int numberToTake = randomIntInRange(1, numberInStack - 1);
        item.setNumberInStack(numberInStack);

        Item takenFromStack = item.takeFromStack(numberToTake);

        assertNotNull(takenFromStack);
        assertNotEquals(item.uuid(), takenFromStack.uuid());
        assertSame(((VariableCacheStub) DATA)._cloneResult, takenFromStack.data());
        assertEquals((Integer) numberToTake, takenFromStack.getNumberInStack());
        assertEquals((Integer) (numberInStack - numberToTake), item.getNumberInStack());
    }

    @Test
    void testAssignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = item;

        Character character = new FakeCharacter();

        item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                character, CHARACTER_EQUIPMENT_SLOT_TYPE);

        Pair<Character, String> equipmentSlot = item.equipmentSlot();

        assertNotNull(equipmentSlot);
        assertSame(character, equipmentSlot.getItem1());
        assertSame(CHARACTER_EQUIPMENT_SLOT_TYPE, equipmentSlot.getItem2());
    }

    @Test
    void testAssignCharacterInventoryToItemAfterAddingToCharacterInventory() {
        FakeCharacterInventory.OVERRIDE_CONTAINS = true;

        item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER);

        assertSame(CHARACTER, item.inventoryCharacter());
    }

    @Test
    void testAssignTileAfterAddedToTileEntitiesOfType() {
        TILE.items().add(item);

        item.assignTileAfterAddedToTileEntitiesOfType(TILE);

        assertSame(TILE, item.tile());
    }

    @Test
    void testAssignTileFixtureAfterAddedItemToTileFixtureItems() {
        FakeTileFixtureItems tileFixtureItems = new FakeTileFixtureItems(null);

        item.assignTileFixtureAfterAddedItemToTileFixtureItems(
                tileFixtureItems.TILE_FIXTURE);

        assertSame(tileFixtureItems.TILE_FIXTURE, item.tileFixture());
    }

    @Test
    void testPassiveAbilities() {
        assertNotNull(item.passiveAbilities());
    }

    @Test
    void testActiveAbilities() {
        assertNotNull(item.activeAbilities());
    }

    @Test
    void testReactiveAbilities() {
        assertNotNull(item.reactiveAbilities());
    }

    @Test
    void testAssignEquipmentSlotToItemAfterAddedToEquipmentSlotNullifiesOtherAssignments() {
        CHARACTER_INVENTORY.add(item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);

        assertNull(item.inventoryCharacter());

        TILE_FIXTURE.items().add(item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);

        assertNull(item.tileFixture());

        TILE.items().add(item);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);

        assertNull(item.tile());
    }

    @Test
    void testAssignInventoryCharacterToItemAfterAddedToInventoryNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);
        CHARACTER_INVENTORY.add(item);

        assertNull(item.equipmentSlot());

        TILE_FIXTURE.items().add(item);
        CHARACTER_INVENTORY.add(item);

        assertNull(item.equipmentSlot());

        TILE.items().add(item);
        CHARACTER_INVENTORY.add(item);

        assertNull(item.equipmentSlot());
    }

    @Test
    void testAssignTileFixtureToItemAfterAddingItemToTileFixtureItemsNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);
        TILE_FIXTURE.items().add(item);
        assertNull(item.equipmentSlot());

        CHARACTER_INVENTORY.add(item);
        TILE_FIXTURE.items().add(item);
        assertNull(item.inventoryCharacter());

        TILE.items().add(item);
        TILE_FIXTURE.items().add(item);
        assertNull(item.tile());
    }

    @Test
    void testAssignTileToItemAfterAddedToTileEntitiesNullifiesOtherAssignments() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);
        TILE.items().add(item);
        assertNull(item.equipmentSlot());

        CHARACTER_INVENTORY.add(item);
        TILE.items().add(item);
        assertNull(item.inventoryCharacter());

        TILE_FIXTURE.items().add(item);
        TILE.items().add(item);
        assertNull(item.tileFixture());
    }

    @Test
    void testAssignToNullCharacterEquipmentSlotsNullifiesGetEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = item;

        item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                null, CHARACTER_EQUIPMENT_SLOT_TYPE);

        assertNull(item.equipmentSlot());
    }

    @Test
    void testAssignToNullEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = item;

        item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, null);

        assertNull(item.equipmentSlot());
    }

    @Test
    void testAssignToEmptyEquipmentSlotTypeNullifiesGetEquipmentSlot() {
        FakeCharacterEquipmentSlots.ITEM_IN_SLOT_RESULT_OVERRIDE = item;

        item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE);

        item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                CHARACTER, "");

        assertNull(item.equipmentSlot());
    }

    @Test
    void testCharacterEquipmentSlotAssignmentInvariant() {
        CHARACTER_EQUIPMENT_SLOTS.addCharacterEquipmentSlot(CHARACTER_EQUIPMENT_SLOT_TYPE);
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);
    }

    @Test
    void testDelete() {
        item.delete();

        assertTrue(item.isDeleted());
    }

    @Test
    void testSetAndGetName() {
        String name = randomString();

        item.setName(name);

        assertEquals(name, item.getName());
    }

    @Test
    void testGetNameWithNullItemImplNameReturnsItemTypeName() {
        item.setName(null);

        assertEquals(ItemTypeStub.ITEM_TYPE_NAME, item.getName());
    }

    @Test
    void testGetNameWithEmptyItemImplNameReturnsItemTypeName() {
        item.setName("");

        assertEquals(ItemTypeStub.ITEM_TYPE_NAME, item.getName());
    }

    @Test
    void testSetAndGetPluralName() {
        String pluralName = randomString();

        item.setPluralName(pluralName);

        assertEquals(pluralName, item.getPluralName());
    }

    @Test
    void testCreatedItemTakesDefaultOffsets() {
        assertEquals(Vertex.of(ItemTypeStub.DEFAULT_X_TILE_WIDTH_OFFSET,
                ItemTypeStub.DEFAULT_Y_TILE_HEIGHT_OFFSET), item.getTileOffset());
    }

    @Test
    void testSetAndGetTileOffset() {
        Vertex tileOffset = Vertex.of(randomFloat(), randomFloat());

        item.setTileOffset(tileOffset);

        assertSame(tileOffset, item.getTileOffset());
    }

    @Test
    void testGetNameWithNullItemImplPluralNameReturnsItemTypePluralName() {
        item.setPluralName(null);

        assertEquals(ItemTypeStub.ITEM_TYPE_PLURAL_NAME, item.getPluralName());
    }

    @Test
    void testGetNameWithEmptyItemImplPluralNameReturnsItemTypePluralName() {
        item.setPluralName("");

        assertEquals(ItemTypeStub.ITEM_TYPE_PLURAL_NAME, item.getPluralName());
    }

    @Test
    void testDeleteRemovesItemFromCharacterEquipmentSlot() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);
        assertSame(item, CHARACTER_EQUIPMENT_SLOTS.itemInSlot(CHARACTER_EQUIPMENT_SLOT_TYPE));

        item.delete();

        assertNull(CHARACTER_EQUIPMENT_SLOTS.itemInSlot(CHARACTER_EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testDeleteRemovesItemFromCharacterInventory() {
        CHARACTER_INVENTORY.add(item);
        assertTrue(CHARACTER_INVENTORY.contains(item));
        int originalCharacterInventorySize =
                ((FakeCharacterInventory) CHARACTER_INVENTORY).ITEMS.size();

        item.delete();

        assertFalse(CHARACTER_INVENTORY.contains(item));
        assertFalse(((FakeCharacterInventory) CHARACTER_INVENTORY).ITEMS.contains(item));
        assertEquals(originalCharacterInventorySize - 1,
                ((FakeCharacterInventory) CHARACTER_INVENTORY).ITEMS.size());
    }

    @Test
    void testDeleteRemovesItemFromTileFixtureItems() {
        TILE_FIXTURE.items().add(item);
        assertTrue(TILE_FIXTURE.items().contains(item));
        int originalTileFixtureSize = ((FakeTileFixtureItems) TILE_FIXTURE.items())._items.size();

        item.delete();

        assertFalse(TILE_FIXTURE.items().contains(item));
        assertFalse(((FakeTileFixtureItems) TILE_FIXTURE.items())._items.contains(item));
        assertEquals(originalTileFixtureSize - 1,
                ((FakeTileFixtureItems) TILE_FIXTURE.items())._items.size());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testDeleteRemovesItemFromTileItems() {
        TILE.items().add(item);
        assertTrue(TILE.items().contains(item));
        int originalTileFixtureSize = ((FakeTileEntities) TILE.items()).ENTITIES.size();

        item.delete();

        assertFalse(TILE.items().contains(item));
        assertFalse(((FakeTileEntities) TILE.items()).ENTITIES.containsKey(item));
        assertEquals(originalTileFixtureSize - 1,
                ((FakeTileEntities) TILE.items()).ENTITIES.size());
    }

    @Test
    void testDeletionInvariant() {
        item.delete();

        assertThrows(EntityDeletedException.class, () -> item.type());
        assertThrows(EntityDeletedException.class, () -> item.getCharges());
        assertThrows(EntityDeletedException.class, () -> item.setCharges(1));
        assertThrows(EntityDeletedException.class, () -> item.getNumberInStack());
        assertThrows(EntityDeletedException.class, () -> item.setNumberInStack(1));
        assertThrows(EntityDeletedException.class, () -> item.takeFromStack(1));
        assertThrows(EntityDeletedException.class, () -> item.equipmentSlot());
        assertThrows(EntityDeletedException.class, () -> item.inventoryCharacter());
        assertThrows(EntityDeletedException.class, () -> item.tile());
        assertThrows(EntityDeletedException.class, () -> item.tileFixture());
        assertThrows(EntityDeletedException.class, () ->
                item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class, () ->
                item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(EntityDeletedException.class, () ->
                item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(EntityDeletedException.class, () ->
                item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(EntityDeletedException.class, () -> item.passiveAbilities());
        assertThrows(EntityDeletedException.class, () -> item.activeAbilities());
        assertThrows(EntityDeletedException.class, () -> item.reactiveAbilities());
        assertThrows(EntityDeletedException.class, () -> item.getName());
        assertThrows(EntityDeletedException.class, () -> item.setName(""));
        assertThrows(EntityDeletedException.class, () -> item.getPluralName());
        assertThrows(EntityDeletedException.class, () -> item.setPluralName(""));
        assertThrows(EntityDeletedException.class, () -> item.getTileOffset());
        assertThrows(EntityDeletedException.class, () -> item.setTileOffset(Vertex.of(0f, 0f)));
        assertThrows(EntityDeletedException.class, () -> item.delete());
    }

    @Test
    void testItemNotFoundInCharacterEquipmentSlotInvariant() {
        CHARACTER_EQUIPMENT_SLOTS.equipItemToSlot(CHARACTER_EQUIPMENT_SLOT_TYPE, item);
        FakeCharacterEquipmentSlots.EQUIPMENT_SLOTS.remove(CHARACTER_EQUIPMENT_SLOT_TYPE);

        assertThrows(IllegalStateException.class, () -> item.type());
        assertThrows(IllegalStateException.class, () -> item.getCharges());
        assertThrows(IllegalStateException.class, () -> item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> item.tile());
        assertThrows(IllegalStateException.class, () -> item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> item.passiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.activeAbilities());
        assertThrows(IllegalStateException.class, () -> item.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.getName());
        assertThrows(IllegalStateException.class, () -> item.setName(""));
        assertThrows(IllegalStateException.class, () -> item.getPluralName());
        assertThrows(IllegalStateException.class, () -> item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> item.getTileOffset());
        assertThrows(IllegalStateException.class, () -> item.setTileOffset(Vertex.of(0f, 0f)));
        assertThrows(IllegalStateException.class, () -> item.delete());
    }

    @Test
    void testItemNotFoundInCharacterInventoryInvariant() {
        CHARACTER_INVENTORY.add(item);
        ((FakeCharacterInventory) CHARACTER_INVENTORY).ITEMS.remove(item);

        assertThrows(IllegalStateException.class, () -> item.type());
        assertThrows(IllegalStateException.class, () -> item.getCharges());
        assertThrows(IllegalStateException.class, () -> item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> item.tile());
        assertThrows(IllegalStateException.class, () -> item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> item.passiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.activeAbilities());
        assertThrows(IllegalStateException.class, () -> item.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.getName());
        assertThrows(IllegalStateException.class, () -> item.setName(""));
        assertThrows(IllegalStateException.class, () -> item.getPluralName());
        assertThrows(IllegalStateException.class, () -> item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> item.getTileOffset());
        assertThrows(IllegalStateException.class, () -> item.setTileOffset(Vertex.of(0f, 0f)));
        assertThrows(IllegalStateException.class, () -> item.delete());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testItemNotFoundInTileItemsInvariant() {
        TILE.items().add(item);
        ((FakeTileEntities) TILE.items()).ENTITIES.remove(item);

        assertThrows(IllegalStateException.class, () -> item.type());
        assertThrows(IllegalStateException.class, () -> item.getCharges());
        assertThrows(IllegalStateException.class, () -> item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> item.tile());
        assertThrows(IllegalStateException.class, () -> item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> item.passiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.activeAbilities());
        assertThrows(IllegalStateException.class, () -> item.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.getName());
        assertThrows(IllegalStateException.class, () -> item.setName(""));
        assertThrows(IllegalStateException.class, () -> item.getPluralName());
        assertThrows(IllegalStateException.class, () -> item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> item.getTileOffset());
        assertThrows(IllegalStateException.class, () -> item.setTileOffset(Vertex.of(0f, 0f)));
        assertThrows(IllegalStateException.class, () -> item.delete());
    }

    @Test
    void testItemNotFoundInTileFixtureItemsInvariant() {
        FakeTileFixture tileFixture = new FakeTileFixture();
        tileFixture.items().add(item);
        ((FakeTileFixtureItems) tileFixture.items())._items.remove(item);

        assertThrows(IllegalStateException.class, () -> item.type());
        assertThrows(IllegalStateException.class, () -> item.getCharges());
        assertThrows(IllegalStateException.class, () -> item.setCharges(1));
        assertThrows(IllegalStateException.class, () -> item.getNumberInStack());
        assertThrows(IllegalStateException.class, () -> item.setNumberInStack(1));
        assertThrows(IllegalStateException.class, () -> item.takeFromStack(1));
        assertThrows(IllegalStateException.class, () -> item.equipmentSlot());
        assertThrows(IllegalStateException.class, () -> item.inventoryCharacter());
        assertThrows(IllegalStateException.class, () -> item.tile());
        assertThrows(IllegalStateException.class, () -> item.tileFixture());
        assertThrows(IllegalStateException.class, () ->
                item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
                        CHARACTER, CHARACTER_EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () ->
                item.assignInventoryCharacterAfterAddedToCharacterInventory(CHARACTER));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileFixtureAfterAddedItemToTileFixtureItems(TILE_FIXTURE));
        assertThrows(IllegalStateException.class, () ->
                item.assignTileAfterAddedToTileEntitiesOfType(TILE));
        assertThrows(IllegalStateException.class, () -> item.passiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.activeAbilities());
        assertThrows(IllegalStateException.class, () -> item.reactiveAbilities());
        assertThrows(IllegalStateException.class, () -> item.getName());
        assertThrows(IllegalStateException.class, () -> item.setName(""));
        assertThrows(IllegalStateException.class, () -> item.getPluralName());
        assertThrows(IllegalStateException.class, () -> item.setPluralName(""));
        assertThrows(IllegalStateException.class, () -> item.getTileOffset());
        assertThrows(IllegalStateException.class, () -> item.setTileOffset(Vertex.of(0f, 0f)));
        assertThrows(IllegalStateException.class, () -> item.delete());
    }
}
