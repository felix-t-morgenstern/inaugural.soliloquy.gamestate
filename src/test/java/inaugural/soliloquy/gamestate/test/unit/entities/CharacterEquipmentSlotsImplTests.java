package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterEquipmentSlotsImpl;
import inaugural.soliloquy.gamestate.test.fakes.*;
import inaugural.soliloquy.gamestate.test.stubs.EquipmentTypeStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEquipmentSlotsImplTests {
    private final Character CHARACTER = new FakeCharacter();
    private final Item ITEM = new FakeItem();
    private final String EQUIPMENT_SLOT_TYPE = "armor";

    private CharacterEquipmentSlots _characterEquipmentSlots;

    @BeforeEach
    void setUp() {
        ((FakeItem) ITEM).equipmentCharacter = null;
        _characterEquipmentSlots = new CharacterEquipmentSlotsImpl(CHARACTER);
        EquipmentTypeStub.VALID_EQUIPMENT_SLOTS.add(EQUIPMENT_SLOT_TYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterEquipmentSlotsImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEquipmentSlots.class.getCanonicalName(),
                _characterEquipmentSlots.getInterfaceName());
    }

    @Test
    void testAddEquipmentSlotAndCheckIfExists() {
        assertFalse(_characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));

        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertTrue(_characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testAddInvalidEquipmentSlot() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.addCharacterEquipmentSlot(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.addCharacterEquipmentSlot(""));
    }

    @Test
    void testCheckIfExistsForInvalidSlotTypes() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipmentSlotExists(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipmentSlotExists(""));
    }

    @Test
    void testCanEquipItemToSlot() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        assertTrue(_characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));

        EquipmentTypeStub.VALID_EQUIPMENT_SLOTS.clear();

        assertFalse(_characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
    }

    @Test
    void testCanEquipInvalidItemToInvalidSlot() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(null, ITEM));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot("", ITEM));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));

        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, null));
    }

    @Test
    void testGetAndSetCanAlterEquipmentInSlot() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertTrue(_characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));

        _characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE, false);

        assertFalse(_characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testGetAndSetCanAlterEquipmentInInvalidSlot() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE,
                        true));
    }

    @Test
    void testGetAndSetCanAlterEquipmentInSlotWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(""));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.setCanAlterEquipmentInSlot(null, true));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.setCanAlterEquipmentInSlot("", true));
    }

    @Test
    void testEquipItemToSlot() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM);

        assertSame(ITEM, _characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertSame(CHARACTER, ITEM.equipmentSlot().getFirstArchetype());
        assertEquals(EQUIPMENT_SLOT_TYPE, ITEM.equipmentSlot().getSecondArchetype());
    }

    @Test
    void testEquipItemToSlotWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(null, ITEM));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipItemToSlot("", ITEM));
    }

    @Test
    void testEquipItemAndCanEquipItemElsewhereInOtherLocationTypes() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        ((FakeItem) ITEM).equipmentCharacter = CHARACTER;
        ((FakeItem) ITEM).equipmentSlotType = EQUIPMENT_SLOT_TYPE;

        assertFalse(_characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));

        ((FakeItem) ITEM).equipmentCharacter = null;
        ((FakeItem) ITEM).equipmentSlotType = null;
        ((FakeItem) ITEM).inventoryCharacter = CHARACTER;

        assertFalse(_characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));

        ((FakeItem) ITEM).inventoryCharacter = null;
        ((FakeItem) ITEM).tile = new FakeTile();

        assertFalse(_characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));

        ((FakeItem) ITEM).tile = null;
        ((FakeItem) ITEM).tileFixture = new FakeTileFixture();

        assertFalse(_characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
    }

    @Test
    void testPreviouslyEquippedItemUnassignedFromSlot() {
        Item previousItem = new FakeItem();
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, previousItem);
        assertNotNull(previousItem.equipmentSlot());
        assertSame(CHARACTER, previousItem.equipmentSlot().getFirstArchetype());
        assertEquals(EQUIPMENT_SLOT_TYPE,
                previousItem.equipmentSlot().getSecondArchetype());

        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM);

        assertNull(previousItem.equipmentSlot());
    }

    @Test
    void testItemInInvalidSlot() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.itemInSlot(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.itemInSlot(""));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testItemReferencesCorrectSlotInvariant() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM);
        ((FakeItem) ITEM).equipmentCharacter = null;

        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));

        ((FakeItem) ITEM).equipmentCharacter = new FakeCharacter();

        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));

        ((FakeItem) ITEM).equipmentCharacter = CHARACTER;
        ((FakeItem) ITEM).equipmentSlotType = "NotAValidType";

        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testUnequipItemFromSlot() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM);

        assertSame(ITEM, _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, null));
        assertSame(null, _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, null));
    }

    @Test
    void testEquipItemToSlotWhichCannotBeAltered() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        _characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE, false);

        assertThrows(UnsupportedOperationException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
    }

    @Test
    void testEquipItemToInvalidSlot() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        EquipmentTypeStub.VALID_EQUIPMENT_SLOTS.clear();

        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
    }

    @Test
    void testRemoveEquipmentSlot() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM);
        assertTrue(_characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));

        assertSame(ITEM,
                _characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));

        assertFalse(_characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testRemoveInvalidEquipmentSlot() {
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(null));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(""));
        assertThrows(IllegalArgumentException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(""));
    }

    @Test
    void testGetRepresentation() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM);

        Map<String, Item> characterEquipmentSlotsRepresentation =
                _characterEquipmentSlots.representation();
        Map<String, Item> characterEquipmentSlotsRepresentationSecond =
                _characterEquipmentSlots.representation();

        assertNotNull(characterEquipmentSlotsRepresentation);
        assertNotSame(characterEquipmentSlotsRepresentation,
                characterEquipmentSlotsRepresentationSecond);
        assertEquals(1, characterEquipmentSlotsRepresentation.size());
        assertSame(ITEM, characterEquipmentSlotsRepresentation.get(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    void testDelete() {
        _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        // NB: This is added to ensure that NullPointerExceptions are being avoided
        _characterEquipmentSlots.addCharacterEquipmentSlot("Slot with null item");
        _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM);
        assertFalse(_characterEquipmentSlots.isDeleted());

        _characterEquipmentSlots.delete();

        assertTrue(_characterEquipmentSlots.isDeleted());
        assertTrue(ITEM.isDeleted());
    }

    @Test
    void testIsDeletedInvariant() {
        _characterEquipmentSlots.delete();

        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.getInterfaceName());
        assertThrows(EntityDeletedException.class, () -> _characterEquipmentSlots.representation());
        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(EntityDeletedException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class, () -> _characterEquipmentSlots
                .setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE, true));
    }

    @Test
    void testCharacterDeletedInvariant() {
        CHARACTER.delete();

        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.getInterfaceName());
        assertThrows(IllegalStateException.class, () -> _characterEquipmentSlots.representation());
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, ITEM));
        assertThrows(IllegalStateException.class,
                () -> _characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class, () -> _characterEquipmentSlots
                .setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE, true));
    }
}
