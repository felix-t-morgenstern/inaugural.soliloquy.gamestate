package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.ItemArchetype;
import soliloquy.common.specs.*;
import soliloquy.gamestate.specs.*;

import java.util.HashMap;

import java.util.Map;
public class CharacterEquipmentSlots implements ICharacterEquipmentSlots {
    private final ICharacter CHARACTER;
    private final IPairFactory PAIR_FACTORY;
    private final IMapFactory MAP_FACTORY;
    private final HashMap<String, IPair<IItem,Boolean>> EQUIPMENT_SLOTS;

    private static final IItem ITEM_ARCHETYPE = new ItemArchetype();

    private boolean _isDeleted;

    public CharacterEquipmentSlots(ICharacter character, IPairFactory pairFactory,
                                   IMapFactory mapFactory) {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots: character must be non-null");
        }
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots: pairFactory must be non-null");
        }
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots: mapFactory must be non-null");
        }

        CHARACTER = character;
        PAIR_FACTORY = pairFactory;
        MAP_FACTORY = mapFactory;
        EQUIPMENT_SLOTS = new HashMap<>();
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariants("delete");
        _isDeleted = true;
        for(Map.Entry<String,IPair<IItem,Boolean>> entry : EQUIPMENT_SLOTS.entrySet()) {
            IItem item = entry.getValue().getItem1();
            if(item != null && !item.isDeleted()) {
                item.delete();
            }
        }
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return ICharacterEquipmentSlots.class.getCanonicalName();
    }

    @Override
    public IMap<String, IItem> getRepresentation()
            throws IllegalStateException {
        enforceDeletionInvariants("getRepresentation");
        IMap<String, IItem> characterEquipmentSlotsRepresentation =
                MAP_FACTORY.make("", ITEM_ARCHETYPE);
        for(Map.Entry<String,IPair<IItem,Boolean>> equipmentSlot : EQUIPMENT_SLOTS.entrySet()) {
            characterEquipmentSlotsRepresentation.put(equipmentSlot.getKey(),
                    equipmentSlot.getValue().getItem1());
        }
        return characterEquipmentSlotsRepresentation;
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
    public IItem removeCharacterEquipmentSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("removeCharacterEquipmentSlot");
        if (equipmentSlotType == null) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.removeCharacterEquipmentSlot: equipmentSlotType must be non-null");
        }
        if (equipmentSlotType.equals("")) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.removeCharacterEquipmentSlot: equipmentSlotType must be non-empty");
        }
        IItem itemInSlot = EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
        EQUIPMENT_SLOTS.remove(equipmentSlotType);
        return itemInSlot;
    }

    @Override
    public IItem itemInSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("itemInSlot");
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
        return EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
    }

    @Override
    public boolean canEquipItemToSlot(String equipmentSlotType, IItem item)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("canEquipItemToSlot");
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
        return item.itemType().equipmentType().canEquipToSlotType(equipmentSlotType);
    }

    @Override
    public IItem equipItemToSlot(String equipmentSlotType, IItem item)
            throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        enforceDeletionInvariants("equipItemToSlot");
        if (!EQUIPMENT_SLOTS.get(equipmentSlotType).getItem2()) {
            throw new UnsupportedOperationException(
                    "CharacterEquipmentSlots.equipItemToSlot: item in equipmentSlotType is set to prohibit alteration");
        }
        if (item != null &&
                !item.itemType().equipmentType().canEquipToSlotType(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.equipItemToSlot: item cannot be equiped to slot of provided type");
        }
        IItem previousItem = EQUIPMENT_SLOTS.get(equipmentSlotType).getItem1();
        EQUIPMENT_SLOTS.get(equipmentSlotType).setItem1(item);
        return previousItem;
    }

    @Override
    public boolean getCanAlterEquipmentInSlot(String equipmentSlotType)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getCanAlterEquipmentInSlot");
        if (!EQUIPMENT_SLOTS.containsKey(equipmentSlotType)) {
            throw new IllegalArgumentException(
                    "CharacterEquipmentSlots.getCanAlterEquipmentInSlot: no equipment slot of specified type");
        }
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

    private void enforceDeletionInvariants(String methodName) {
        if (_isDeleted) {
            throw new IllegalStateException("CharacterEquipmentSlots." + methodName +
                    ": object has already been deleted");
        }
        if (CHARACTER.isDeleted()) {
            throw new IllegalStateException("CharacterEquipmentSlots." + methodName +
                    ": character has already been deleted");
        }
    }
}
