package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Item;

import java.util.HashMap;

public class CharacterEquipmentSlotsImpl extends CanTellIfItemIsPresentElsewhere
        implements CharacterEquipmentSlots {
    private final Character CHARACTER;
    private final PairFactory PAIR_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<String, Pair<Item, Boolean>> EQUIPMENT_SLOTS;

    private static final Item ITEM_ARCHETYPE = new ItemArchetype();

    public CharacterEquipmentSlotsImpl(Character character, PairFactory pairFactory,
                                       MapFactory mapFactory) {
        CHARACTER = Check.ifNull(character, "character");
        PAIR_FACTORY = Check.ifNull(pairFactory, "pairFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
        EQUIPMENT_SLOTS = new HashMap<>();
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        for (java.util.Map.Entry<String, Pair<Item, Boolean>> entry : EQUIPMENT_SLOTS.entrySet()) {
            Item item = entry.getValue().getItem1();
            if (item != null && !item.isDeleted()) {
                item.delete();
            }
        }
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants();
        return CharacterEquipmentSlots.class.getCanonicalName();
    }

    @Override
    public Map<String, Item> representation()
            throws IllegalStateException {
        enforceDeletionInvariants();
        Map<String, Item> characterEquipmentSlots =
                MAP_FACTORY.make("", ITEM_ARCHETYPE);
        for (java.util.Map.Entry<String, Pair<Item, Boolean>> equipmentSlot : EQUIPMENT_SLOTS.entrySet()) {
            characterEquipmentSlots.put(equipmentSlot.getKey(),
                    equipmentSlot.getValue().getItem1());
        }
        return characterEquipmentSlots;
    }

    @Override
    public void addCharacterEquipmentSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            EQUIPMENT_SLOTS.putIfAbsent(equipmentSlotType,
                    PAIR_FACTORY.make(null, true, ITEM_ARCHETYPE, true));
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
        Item itemInSlot = EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
        EQUIPMENT_SLOTS.remove(equipmentSlotType);
        enforceItemReferencesCorrectSlotInvariant("removeCharacterEquipmentSlot",
                equipmentSlotType);
        return itemInSlot;
    }

    @Override
    public Item itemInSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceItemReferencesCorrectSlotInvariant("itemInSlot",
                equipmentSlotType);
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.itemInSlot: no equipment slot of specified type");
        }
        enforceItemReferencesCorrectSlotInvariant("itemInSlot",
                equipmentSlotType);
        return EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
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

        enforceItemReferencesCorrectSlotInvariant("canEquipItemToSlot",
                equipmentSlotType);
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
        if (!EQUIPMENT_SLOTS.get(equipmentSlotType).getItem2()) {
            throw new UnsupportedOperationException(
                    "CharacterEquipmentSlots.equipItemToSlot: item in equipmentSlotType is set to prohibit alteration");
        }
        if (item != null &&
                !item.type().equipmentType().canEquipToSlotType(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipItemToSlot: item cannot be equipped to slot of provided type");
        }
        Item previousItem = EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
        if (previousItem != null) {
            previousItem.assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(null, null);
        }
        if (item != null && itemIsPresentElsewhere(item)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipItemToSlot: item is already present elsewhere");
        }
        EQUIPMENT_SLOTS.get(equipmentSlotType).setItem1(item);
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
                    "CharacterEquipmentSlots.getCanAlterEquipmentInSlot: no equipment slot of specified type");
        }
        enforceItemReferencesCorrectSlotInvariant("getCanAlterEquipmentInSlot",
                equipmentSlotType);
        return EQUIPMENT_SLOTS.get(equipmentSlotType).getItem2();
    }

    @Override
    public void setCanAlterEquipmentInSlot(String equipmentSlotType,
                                           boolean canAlterEquipmentInSlot)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        Check.ifNullOrEmpty(equipmentSlotType, "equipmentSlotType");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.setCanAlterEquipmentInSlot: no equipment slot of specified type");
        }
        EQUIPMENT_SLOTS.get(equipmentSlotType).setItem2(canAlterEquipmentInSlot);
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
        Pair<Item,Boolean> slot = EQUIPMENT_SLOTS.get(equipmentSlotType);
        if (slot != null) {
            Item itemInSlot = slot.getItem1();
            if (itemInSlot != null) {
                Pair<Character,String> itemEquipmentSlot = itemInSlot.equipmentSlot();
                if (itemEquipmentSlot == null) {
                    throw new IllegalStateException("CharacterEquipmentSlotsImpl." + methodName +
                            ": Item in equipment slot (" + equipmentSlotType +
                            ") is not assigned to that slot");
                }
                if (itemEquipmentSlot.getItem1() != CHARACTER)
                {
                    throw new IllegalStateException("CharacterEquipmentSlotsImpl." + methodName +
                            ": Item is assigned to wrong Character");
                }
                if (!itemEquipmentSlot.getItem2().equals(equipmentSlotType))
                {
                    throw new IllegalStateException("CharacterEquipmentSlotsImpl." + methodName +
                            ": Item is assigned to wrong Character");
                }
            }
        }
    }
}
