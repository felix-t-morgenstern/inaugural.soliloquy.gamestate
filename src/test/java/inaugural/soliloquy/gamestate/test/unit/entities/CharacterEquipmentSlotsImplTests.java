package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterEquipmentSlotsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.EquipmentType;
import soliloquy.specs.ruleset.entities.ItemType;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class CharacterEquipmentSlotsImplTests {
    private final String EQUIPMENT_SLOT_TYPE = randomString();

    @Mock private EquipmentType mockEquipmentType;
    @Mock private ItemType mockItemType;
    @Mock private Item mockItem;
    @Mock private Item mockPrevItem;
    @Mock private EquipmentType mockEquipmentTypeUnfit;
    @Mock private ItemType mockItemTypeUnfit;
    @Mock private Item mockItemUnfit;
    @Mock private Character mockCharacter;

    private CharacterEquipmentSlots characterEquipmentSlots;

    @BeforeEach
    public void setUp() {
        lenient().when(mockEquipmentType.canEquipToSlotType(EQUIPMENT_SLOT_TYPE)).thenReturn(true);
        lenient().when(mockItemType.equipmentType()).thenReturn(mockEquipmentType);
        lenient().when(mockItem.type()).thenReturn(mockItemType);
        lenient().doAnswer(
                        invocationOnMock -> setupMockItemAssignEquipmentSlot(invocationOnMock,
                                mockItem))
                .when(mockItem)
                .assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(any(), anyString());

        lenient().when(mockPrevItem.type()).thenReturn(mockItemType);
        lenient().doAnswer(invocationOnMock -> setupMockItemAssignEquipmentSlot(invocationOnMock,
                mockPrevItem))
                .when(mockPrevItem)
                .assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(any(), anyString());

        lenient().when(mockEquipmentTypeUnfit.canEquipToSlotType(anyString())).thenReturn(false);
        lenient().when(mockItemTypeUnfit.equipmentType()).thenReturn(mockEquipmentTypeUnfit);
        lenient().when(mockItemUnfit.type()).thenReturn(mockItemTypeUnfit);

        characterEquipmentSlots = new CharacterEquipmentSlotsImpl(mockCharacter);
    }

    @SuppressWarnings("rawtypes")
    private Answer setupMockItemAssignEquipmentSlot(InvocationOnMock invocation, Item mockItem) {
        Character character = invocation.getArgument(0);
        String equipmentSlotType = invocation.getArgument(1);
        when(mockItem.equipmentSlot()).thenReturn(pairOf(character, equipmentSlotType));
        return null;
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterEquipmentSlotsImpl(null));
    }

    @Test
    public void testAddCharacterEquipmentSlotAndCheckIfExists() {
        assertFalse(characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));

        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertTrue(characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    public void testEquipmentSlotExistsWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.equipmentSlotExists(null));
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.equipmentSlotExists(""));
    }

    @Test
    public void testAddCharacterEquipmentSlotWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.addCharacterEquipmentSlot(null));
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.addCharacterEquipmentSlot(""));
    }

    @Test
    public void testRemoveCharacterEquipmentSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertFalse(characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    public void testRemoveCharacterEquipmentSlotWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.removeCharacterEquipmentSlot(null));
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.removeCharacterEquipmentSlot(""));
    }

    @Test
    public void testCanEquipItemToSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertTrue(characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertFalse(characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItemUnfit));
    }

    @Test
    public void testCannotEquipItemToSlotIfAlreadyInInventory() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.inventoryCharacter()).thenReturn(mockCharacter);

        assertFalse(characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testCannotEquipItemToSlotIfAlreadyInAnotherEquipment() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.equipmentSlot()).thenReturn(pairOf(mockCharacter, EQUIPMENT_SLOT_TYPE));

        assertFalse(characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testCannotEquipItemToSlotIfAlreadyInFixture() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.tileFixture()).thenReturn(mock(TileFixture.class));

        assertFalse(characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testCannotEquipItemToSlotIfAlreadyOnTile() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.tile()).thenReturn(mock(Tile.class));

        assertFalse(characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testCanEquipItemToSlotWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot(null, mockItem));
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot("", mockItem));
        // NB: If a slot type hasn't been added yet, it is an invalid param
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, null));
    }

    @Test
    public void testEquipItemToSlotAndItemInSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);
        var itemInSlot = characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE);

        assertSame(mockItem, itemInSlot);
        verify(mockItem).assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(mockCharacter,
                EQUIPMENT_SLOT_TYPE);
    }

    @Test
    public void testEquipItemToSlotReturnsPrevItem() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);

        var prevItemInSlot = characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, null);

        assertSame(mockItem, prevItemInSlot);
    }

    @Test
    public void testItemInSlotWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.itemInSlot(null));
        assertThrows(IllegalArgumentException.class, () -> characterEquipmentSlots.itemInSlot(""));
        // NB: If a slot type hasn't been added yet, it is an invalid param
        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    public void testRemovePrevItemEquipmentSlotWhenAddingNewItemToSameSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockPrevItem);

        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);

        verify(mockPrevItem).assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(null, null);
    }

    @Test
    public void testGetCanAlterEquipmentInSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertTrue(characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    public void testSetAndGetCanAlterEquipmentInSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE, false);

        assertFalse(characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
    }

    @Test
    public void testEquipItemToUnalterableSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE, false);

        assertThrows(UnsupportedOperationException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testEquipItemUnfitType() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItemUnfit));
    }

    @Test
    public void testEquipItemToSlotIfAlreadyInInventory() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.inventoryCharacter()).thenReturn(mockCharacter);

        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testEquipItemToSlotIfAlreadyInAnotherEquipment() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.equipmentSlot()).thenReturn(pairOf(mockCharacter, EQUIPMENT_SLOT_TYPE));

        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testEquipItemToSlotIfAlreadyInFixture() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.tileFixture()).thenReturn(mock(TileFixture.class));

        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testEquipItemToSlotIfAlreadyOnTile() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        when(mockItem.tile()).thenReturn(mock(Tile.class));

        assertThrows(IllegalArgumentException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testEquipItemWhichBreaksCorrectSlotInvariant() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        doAnswer(invocationOnMock -> null).when(mockItem)
                .assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(any(), anyString());

        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
    }

    @Test
    public void testRemoveCharacterEquipmentSlotReturnsItemInSlot() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);

        var prevItemInSlot =
                characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);

        assertSame(mockItem, prevItemInSlot);
    }

    @Test
    public void testRepresentation() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);

        var representation = characterEquipmentSlots.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertTrue(representation.containsKey(EQUIPMENT_SLOT_TYPE));
        assertSame(mockItem, representation.get(EQUIPMENT_SLOT_TYPE));
        assertNotSame(representation, characterEquipmentSlots.representation());
    }

    @Test
    public void testDelete() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);

        characterEquipmentSlots.delete();

        assertTrue(characterEquipmentSlots.isDeleted());
    }

    @Test
    public void testDeletedInvariant() {
        characterEquipmentSlots.delete();

        assertThrows(EntityDeletedException.class, () -> characterEquipmentSlots.representation());
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(EntityDeletedException.class,
                () -> characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE,
                        true));
    }

    @Test
    public void testBreakItemInSlotHasNoSlotInvariant() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);
        when(mockItem.equipmentSlot()).thenReturn(null);

        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE,
                        true));
    }

    @Test
    public void testBreakItemInSlotHasDiffCharacterInvariant() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);
        when(mockItem.equipmentSlot()).thenReturn(
                pairOf(mock(Character.class), EQUIPMENT_SLOT_TYPE));

        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE,
                        true));
    }

    @Test
    public void testBreakItemInSlotHasDiffSlotTypeInvariant() {
        characterEquipmentSlots.addCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE);
        characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem);
        when(mockItem.equipmentSlot()).thenReturn(pairOf(mockCharacter, "different slot type"));

        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.equipmentSlotExists(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.removeCharacterEquipmentSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.itemInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.canEquipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.equipItemToSlot(EQUIPMENT_SLOT_TYPE, mockItem));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.getCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE));
        assertThrows(IllegalStateException.class,
                () -> characterEquipmentSlots.setCanAlterEquipmentInSlot(EQUIPMENT_SLOT_TYPE,
                        true));
    }
}
