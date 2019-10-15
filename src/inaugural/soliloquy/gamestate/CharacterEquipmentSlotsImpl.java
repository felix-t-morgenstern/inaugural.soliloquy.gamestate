package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEquipmentSlots;
import soliloquy.specs.gamestate.entities.Item;

import java.util.HashMap;
import java.util.function.Predicate;

public class CharacterEquipmentSlotsImpl extends HasDeletionInvariants
        implements CharacterEquipmentSlots {
    private final Character CHARACTER;
    private final PairFactory PAIR_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<String, Pair<Item, Boolean>> EQUIPMENT_SLOTS;
    private final Predicate<Item> ITEM_IS_PRESENT_ELSEWHERE;

    private static final Item ITEM_ARCHETYPE = new ItemArchetype();

    @SuppressWarnings("ConstantConditions")
    public CharacterEquipmentSlotsImpl(Character character, PairFactory pairFactory,
                                       MapFactory mapFactory,
                                       Predicate<Item> itemIsPresentElsewhere) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots: character must be non-null");
        }
        CHARACTER = character;
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots: pairFactory must be non-null");
        }
        PAIR_FACTORY = pairFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
        EQUIPMENT_SLOTS = new HashMap<>();
        if (itemIsPresentElsewhere == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots: itemIsPresentElsewhere must be non-null");
        }
        ITEM_IS_PRESENT_ELSEWHERE = itemIsPresentElsewhere;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        _isDeleted = true;
        for (java.util.Map.Entry<String, Pair<Item, Boolean>> entry : EQUIPMENT_SLOTS.entrySet()) {
            Item item = entry.getValue().getItem1();
            if (item != null && !item.isDeleted()) {
                item.delete();
            }
        }
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return CharacterEquipmentSlots.class.getCanonicalName();
    }

    @Override
    public ReadableMap<String, Item> representation()
            throws IllegalStateException {
        enforceDeletionInvariants("getRepresentation");
        Map<String, Item> characterEquipmentSlots =
                MAP_FACTORY.make("", ITEM_ARCHETYPE);
        for (java.util.Map.Entry<String, Pair<Item, Boolean>> equipmentSlot : EQUIPMENT_SLOTS.entrySet()) {
            characterEquipmentSlots.put(equipmentSlot.getKey(),
                    equipmentSlot.getValue().getItem1());
        }
        return characterEquipmentSlots.readOnlyRepresentation();
    }

    @Override
    public void addCharacterEquipmentSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("addCharacterEquipmentSlot");
        if (equipmentSlotType == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.addCharacterEquipmentSlot: equipmentSlotType must be non-null");
        }
        if (equipmentSlotType.equals("")) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.addCharacterEquipmentSlot: equipmentSlotType must be non-empty");
        }
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            EQUIPMENT_SLOTS.putIfAbsent(equipmentSlotType,
                    PAIR_FACTORY.make(null, true, ITEM_ARCHETYPE, true));
        }
    }

    @Override
    public boolean equipmentSlotExists(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("equipmentSlotExists");
        enforceItemReferencesCorrectSlotInvariant("equipmentSlotExists",
                equipmentSlotType);
        if (equipmentSlotType == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipmentSlotExists: equipmentSlotType must be non-null");
        }
        if (equipmentSlotType.equals("")) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipmentSlotExists: equipmentSlotType must be non-empty");
        }
        return EQUIPMENT_SLOTS.containsKey(equipmentSlotType);
    }

    @Override
    public Item removeCharacterEquipmentSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("removeCharacterEquipmentSlot");
        enforceItemReferencesCorrectSlotInvariant("removeCharacterEquipmentSlot",
                equipmentSlotType);
        if (equipmentSlotType == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.removeCharacterEquipmentSlot: equipmentSlotType must be non-null");
        }
        if (equipmentSlotType.equals("")) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.removeCharacterEquipmentSlot: equipmentSlotType must be non-empty");
        }
        Item itemInSlot = EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
        EQUIPMENT_SLOTS.remove(equipmentSlotType);
        enforceItemReferencesCorrectSlotInvariant("removeCharacterEquipmentSlot",
                equipmentSlotType);
        return itemInSlot;
    }

    @Override
    public Item itemInSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("itemInSlot");
        enforceItemReferencesCorrectSlotInvariant("itemInSlot",
                equipmentSlotType);
        if (equipmentSlotType == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.itemInSlot: equipmentSlotType must be non-null");
        }
        if (equipmentSlotType.equals("")) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.itemInSlot: equipmentSlotType must be non-empty");
        }
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
        enforceDeletionInvariants("canEquipItemToSlot");
        enforceItemReferencesCorrectSlotInvariant("canEquipItemToSlot",
                equipmentSlotType);
        if (equipmentSlotType == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.canEquipItemToSlot: equipmentSlotType must be non-null");
        }
        if (equipmentSlotType.equals("")) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.canEquipItemToSlot: equipmentSlotType must be non-empty");
        }
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.canEquipToSlot: " +
                            equipmentSlotType +
                            " is not an equipment slot present for this Character");
        }
        if (item == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.canEquipItemToSlot: item must be non-null");
        }

        enforceItemReferencesCorrectSlotInvariant("canEquipItemToSlot",
                equipmentSlotType);
        return !ITEM_IS_PRESENT_ELSEWHERE.test(item) &&
                item.itemType().equipmentType().canEquipToSlotType(equipmentSlotType);
    }

    @Override
    public Item equipItemToSlot(String equipmentSlotType, Item item)
            throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        enforceDeletionInvariants("equipItemToSlot");
        enforceItemReferencesCorrectSlotInvariant("equipItemToSlot",
                equipmentSlotType);
        if (!EQUIPMENT_SLOTS.get(equipmentSlotType).getItem2()) {
            throw new UnsupportedOperationException(
                    "CharacterEquipmentSlots.equipItemToSlot: item in equipmentSlotType is set to prohibit alteration");
        }
        if (item != null &&
                !item.itemType().equipmentType().canEquipToSlotType(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipItemToSlot: item cannot be equiped to slot of provided type");
        }
        Item previousItem = EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
        if (previousItem != null) {
            previousItem.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
                    null, null);
        }
        if (item != null && ITEM_IS_PRESENT_ELSEWHERE.test(item)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipItemToSlot: item is already present elsewhere");
        }
        EQUIPMENT_SLOTS.get(equipmentSlotType).setItem1(item);
        if (item != null) {
            item.assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(CHARACTER,
                    equipmentSlotType);
        }
        enforceItemReferencesCorrectSlotInvariant("equipItemToSlot",
                equipmentSlotType);
        return previousItem;
    }

    @Override
    public boolean getCanAlterEquipmentInSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getCanAlterEquipmentInSlot");
        enforceItemReferencesCorrectSlotInvariant("getCanAlterEquipmentInSlot",
                equipmentSlotType);
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
        enforceDeletionInvariants("setCanAlterEquipmentInSlot");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.setCanAlterEquipmentInSlot: no equipment slot of specified type");
        }
        EQUIPMENT_SLOTS.get(equipmentSlotType).setItem2(canAlterEquipmentInSlot);
    }

    @Override
    protected String className() {
        return "CharacterEquipmentSlots";
    }

    @Override
    protected String containingClassName() {
        return "character";
    }

    @Override
    protected boolean containingObjectIsDeleted() {
        return CHARACTER.isDeleted();
    }

    private void enforceItemReferencesCorrectSlotInvariant(String methodName,
                                                           String equipmentSlotType) {
        Pair<Item,Boolean> slot = EQUIPMENT_SLOTS.get(equipmentSlotType);
        if (slot != null) {
            Item itemInSlot = slot.getItem1();
            if (itemInSlot != null) {
                Pair<Character,String> itemEquipmentSlot = itemInSlot.getCharacterEquipmentSlot();
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
