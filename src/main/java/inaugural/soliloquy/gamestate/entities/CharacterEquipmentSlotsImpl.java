package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Item;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class CharacterEquipmentSlotsImpl extends CanTellIfItemIsPresentElsewhere
        implements CharacterEquipmentSlots {
    private final Character CHARACTER;
    // NB: The boolean value is true if and only if the item in slot can be modified
    private final Map<String, Pair<Item, Boolean>> EQUIPMENT_SLOTS;

    public CharacterEquipmentSlotsImpl(Character character) {
        CHARACTER = Check.ifNull(character, "character");
        EQUIPMENT_SLOTS = mapOf();
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        for (var entry : EQUIPMENT_SLOTS.entrySet()) {
            var item = entry.getValue().FIRST;
            if (item != null && !item.isDeleted()) {
                item.delete();
            }
        }
    }

    @Override
    public Map<String, Item> representation()
            throws IllegalStateException {
        enforceDeletionInvariants();
        Map<String, Item> characterEquipmentSlots = mapOf();
        for (var equipmentSlot : EQUIPMENT_SLOTS.entrySet()) {
            characterEquipmentSlots.put(equipmentSlot.getKey(), equipmentSlot.getValue().FIRST);
        }
        return characterEquipmentSlots;
    }

    @Override
    public void addCharacterEquipmentSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            EQUIPMENT_SLOTS.putIfAbsent(equipmentSlotType, pairOf(null, true));
        }
    }

    @Override
    public boolean equipmentSlotExists(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("equipmentSlotExists",
                equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        return EQUIPMENT_SLOTS.containsKey(equipmentSlotType);
    }

    @Override
    public Item removeCharacterEquipmentSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("removeCharacterEquipmentSlot",
                equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        var itemInSlot = EQUIPMENT_SLOTS.get(equipmentSlotType).FIRST;
        EQUIPMENT_SLOTS.remove(equipmentSlotType);
        enforceItemReferencesCorrectSlotInvariant("removeCharacterEquipmentSlot",
                equipmentSlotType);
        return itemInSlot;
    }

    @Override
    public Item itemInSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("itemInSlot", equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.itemInSlot: no equipment slot of specified type");
        }
        return EQUIPMENT_SLOTS.get(equipmentSlotType).FIRST;
    }

    @Override
    public boolean canEquipItemToSlot(String equipmentSlotType, Item item)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("canEquipItemToSlot",
                equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");

        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.canEquipToSlot: " +
                            equipmentSlotType +
                            " is not an equipment slot present for this Character");
        }
        Check.ifNull(item, "item");

        return !itemIsPresentElsewhere(item) &&
                item.type().equipmentType().canEquipToSlotType(equipmentSlotType);
    }

    @Override
    public Item equipItemToSlot(String equipmentSlotType, Item item)
            throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("equipItemToSlot",
                equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");

        var equipmentSlot = EQUIPMENT_SLOTS.get(equipmentSlotType);

        if (!equipmentSlot.SECOND) {
            throw new UnsupportedOperationException(
                    "CharacterEquipmentSlots.equipItemToSlot: item in equipmentSlotType is set to" +
                            " prohibit alteration");
        }
        if (item != null &&
                !item.type().equipmentType().canEquipToSlotType(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipItemToSlot: item cannot be equipped to slot of " +
                            "provided type");
        }
        if (item != null && itemIsPresentElsewhere(item)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipItemToSlot: item is already present elsewhere");
        }
        var previousItem = equipmentSlot.FIRST;
        if (previousItem != null) {
            previousItem.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(null, null);
        }
        EQUIPMENT_SLOTS.put(equipmentSlotType, pairOf(item, equipmentSlot.SECOND));
        if (item != null) {
            item.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(CHARACTER,
                    equipmentSlotType);
        }
        enforceItemReferencesCorrectSlotInvariant("equipItemToSlot",
                equipmentSlotType);
        return previousItem;
    }

    @Override
    public boolean getCanAlterEquipmentInSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("getCanAlterEquipmentInSlot",
                equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.getCanAlterEquipmentInSlot: no equipment slot of " +
                            "specified type");
        }
        enforceItemReferencesCorrectSlotInvariant("getCanAlterEquipmentInSlot", equipmentSlotType);
        return EQUIPMENT_SLOTS.get(equipmentSlotType).SECOND;
    }

    @Override
    public void setCanAlterEquipmentInSlot(String equipmentSlotType,
                                           boolean canAlterEquipmentInSlot)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("setCanAlterEquipmentInSlot",
                equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.setCanAlterEquipmentInSlot: no equipment slot of " +
                            "specified type");
        }
        var equipmentSlot = EQUIPMENT_SLOTS.get(equipmentSlotType);
        EQUIPMENT_SLOTS.put(equipmentSlotType,
                pairOf(equipmentSlot.FIRST, canAlterEquipmentInSlot));
    }

    @Override
    protected String containingClassName() {
        return "character";
    }

    @Override
    protected Deletable getContainingObject() {
        return CHARACTER;
    }

    private void enforceItemReferencesCorrectSlotInvariant(String methodName,
                                                           String equipmentSlotType) {
        var slot = EQUIPMENT_SLOTS.get(equipmentSlotType);
        if (slot != null) {
            var itemInSlot = slot.FIRST;
            if (itemInSlot != null) {
                var itemEquipmentSlot = itemInSlot.equipmentSlot();
                if (itemEquipmentSlot == null) {
                    throw new IllegalStateException("CharacterEquipmentSlotsImpl." + methodName +
                            ": Item in equipment slot (" + equipmentSlotType +
                            ") is not assigned to that slot");
                }
                if (itemEquipmentSlot.FIRST != CHARACTER) {
                    throw new IllegalStateException("CharacterEquipmentSlotsImpl." + methodName +
                            ": Item is assigned to wrong Character");
                }
                if (!itemEquipmentSlot.SECOND.equals(equipmentSlotType)) {
                    throw new IllegalStateException("CharacterEquipmentSlotsImpl." + methodName +
                            ": Item is assigned to wrong Character");
                }
            }
        }
    }
}
