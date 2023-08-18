package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.ItemType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

import java.util.List;
import java.util.UUID;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;

// TODO: Consider extending HasDeletionInvariants
public class ItemImpl implements Item {
    private final UUID UUID;
    private final ItemType ITEM_TYPE;
    private final VariableCache DATA;
    private final List<PassiveAbility> PASSIVE_ABILITIES;
    private final List<ActiveAbility> ACTIVE_ABILITIES;
    private final List<ReactiveAbility> REACTIVE_ABILITIES;

    private int charges;
    private int numberInStack;
    private Character characterInventoryCharacter;
    private Character characterEquipmentSlotsCharacter;
    private String characterEquipmentSlotType;
    private Tile containingTile;
    private TileFixture containingTileFixture;
    private boolean isDeleted;
    private String name;
    private String pluralName;
    private Vertex tileOffset;

    public ItemImpl(UUID uuid,
                    ItemType itemType,
                    VariableCache data) {
        UUID = Check.ifNull(uuid, "uuid");
        ITEM_TYPE = Check.ifNull(itemType, "itemType");
        tileOffset = ITEM_TYPE.defaultTileOffset();
        DATA = Check.ifNull(data, "data");

        PASSIVE_ABILITIES = listOf();
        ACTIVE_ABILITIES = listOf();
        REACTIVE_ABILITIES = listOf();
    }

    @Override
    public ItemType type() throws IllegalStateException {
        enforceDeletionInvariant("itemType");
        enforceAssignmentInvariant("itemType");
        return ITEM_TYPE;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        enforceDeletionInvariant("getCharges");
        enforceAssignmentInvariant("getCharges");
        if (!ITEM_TYPE.hasCharges()) {
            return null;
        }
        else {
            return charges;
        }
    }

    @Override
    public void setCharges(int charges)
            throws UnsupportedOperationException, IllegalStateException {
        enforceDeletionInvariant("setCharges");
        enforceAssignmentInvariant("setCharges");
        if (!ITEM_TYPE.hasCharges()) {
            throw new UnsupportedOperationException(
                    "ItemImpl.setCharges: ItemType doesn't have charges");
        }
        this.charges = Check.throwOnLtValue(charges, 0, "charges");
    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        enforceDeletionInvariant("getNumberInStack");
        enforceAssignmentInvariant("getNumberInStack");
        if (!ITEM_TYPE.isStackable()) {
            return null;
        }
        else {
            return numberInStack;
        }
    }

    // TODO: Ensure that numberInStack cannot be less than 0
    @Override
    public void setNumberInStack(int numberInStack)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariant("setNumberInStack");
        enforceAssignmentInvariant("setNumberInStack");
        if (!ITEM_TYPE.isStackable()) {
            throw new UnsupportedOperationException(
                    "ItemImpl.setNumberInStack: ItemType isn't stackable");
        }
        this.numberInStack = numberInStack;
    }

    @Override
    public Item takeFromStack(int numberToTake)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariant("takeFromStack");
        enforceAssignmentInvariant("takeFromStack");
        if (!ITEM_TYPE.isStackable()) {
            throw new UnsupportedOperationException(
                    "ItemImpl.takeFromStack: ItemType isn't stackable");
        }
        if (numberToTake < 1) {
            throw new IllegalArgumentException(
                    "ItemImpl.takeFromStack: numberToTake must be 1 or more");
        }
        if (numberToTake >= numberInStack) {
            throw new IllegalArgumentException(
                    "ItemImpl.takeFromStack: numberToTake must be less than the number in the " +
                            "stack");
        }
        numberInStack -= numberToTake;
        var takenFromStack = new ItemImpl(java.util.UUID.randomUUID(), ITEM_TYPE, DATA.makeClone());
        takenFromStack.setNumberInStack(numberToTake);
        return takenFromStack;
    }

    @Override
    public Pair<Character, String> equipmentSlot()
            throws IllegalStateException {
        enforceDeletionInvariant("equipmentSlot");
        enforceAssignmentInvariant("equipmentSlot");
        return characterEquipmentSlotsCharacter != null ?
                pairOf(characterEquipmentSlotsCharacter, characterEquipmentSlotType) : null;
    }

    @Override
    public Character inventoryCharacter() throws IllegalStateException {
        enforceDeletionInvariant("inventoryCharacter");
        enforceAssignmentInvariant("inventoryCharacter");
        return characterInventoryCharacter;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        enforceDeletionInvariant("tile");
        enforceAssignmentInvariant("tile");
        return containingTile;
    }

    @Override
    public TileFixture tileFixture() throws IllegalStateException {
        enforceDeletionInvariant("tileFixture");
        enforceAssignmentInvariant("tileFixture");
        return containingTileFixture;
    }

    @Override
    public void assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
            Character character, String slotType)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignEquipmentSlotAfterAddedToCharacterEquipmentSlot");
        enforceAssignmentInvariant("assignEquipmentSlotAfterAddedToCharacterEquipmentSlot");
        if (character == null || slotType == null || slotType.equals("")) {
            characterEquipmentSlotsCharacter = null;
            characterEquipmentSlotType = null;
        }
        else {
            characterEquipmentSlotsCharacter = character;
            characterEquipmentSlotType = slotType;
        }
        characterInventoryCharacter = null;
        containingTileFixture = null;
        containingTile = null;
        enforceAssignmentInvariant("assignEquipmentSlotAfterAddedToCharacterEquipmentSlot");
    }

    @Override
    public void assignInventoryCharacterAfterAddedToCharacterInventory(Character character)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignInventoryCharacterAfterAddedToCharacterInventory");
        enforceAssignmentInvariant("assignInventoryCharacterAfterAddedToCharacterInventory");
        characterInventoryCharacter = character;
        characterEquipmentSlotsCharacter = null;
        characterEquipmentSlotType = null;
        containingTileFixture = null;
        containingTile = null;
        enforceAssignmentInvariant("assignInventoryCharacterAfterAddedToCharacterInventory");
    }

    @Override
    public void assignTileFixtureAfterAddedItemToTileFixtureItems(TileFixture tileFixture)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignTileFixtureAfterAddedItemToTileFixtureItems");
        enforceAssignmentInvariant("assignTileFixtureAfterAddedItemToTileFixtureItems");
        containingTileFixture = tileFixture;
        characterEquipmentSlotsCharacter = null;
        characterEquipmentSlotType = null;
        characterInventoryCharacter = null;
        containingTile = null;
        enforceAssignmentInvariant("assignTileFixtureAfterAddedItemToTileFixtureItems");
    }

    @Override
    public List<PassiveAbility> passiveAbilities() throws EntityDeletedException {
        enforceDeletionInvariant("passiveAbilities");
        enforceAssignmentInvariant("passiveAbilities");
        return PASSIVE_ABILITIES;
    }

    @Override
    public List<ActiveAbility> activeAbilities() throws EntityDeletedException {
        enforceDeletionInvariant("activeAbilities");
        enforceAssignmentInvariant("activeAbilities");
        return ACTIVE_ABILITIES;
    }

    @Override
    public List<ReactiveAbility> reactiveAbilities() throws EntityDeletedException {
        enforceDeletionInvariant("reactiveAbilities");
        enforceAssignmentInvariant("reactiveAbilities");
        return REACTIVE_ABILITIES;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignTileAfterAddedToTileEntitiesOfType");
        enforceAssignmentInvariant("assignTileAfterAddedToTileEntitiesOfType");
        containingTile = tile;
        characterEquipmentSlotsCharacter = null;
        characterEquipmentSlotType = null;
        characterInventoryCharacter = null;
        containingTileFixture = null;
        enforceAssignmentInvariant("assignTileAfterAddedToTileEntitiesOfType");
    }

    @Override
    public UUID uuid() {
        return UUID;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        enforceDeletionInvariant("data");
        enforceAssignmentInvariant("data");
        return DATA;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariant("delete");
        enforceAssignmentInvariant("delete");
        if (characterEquipmentSlotsCharacter != null) {
            // TODO: Attempt to test whether unassign-then-remove pattern is followed here
            characterEquipmentSlotsCharacter.equipmentSlots()
                    .equipItemToSlot(characterEquipmentSlotType, null);
        }
        if (characterInventoryCharacter != null) {
            // TODO: Attempt to test whether unassign-then-remove pattern is followed here
            CharacterInventory characterInventory = characterInventoryCharacter.inventory();
            characterInventoryCharacter = null;
            characterInventory.remove(this);
        }
        if (containingTileFixture != null) {
            TileFixtureItems tileFixtureItems = containingTileFixture.items();
            containingTileFixture = null;
            tileFixtureItems.remove(this);
        }
        if (containingTile != null) {
            TileEntities<Item> tileItems = containingTile.items();
            containingTile = null;
            tileItems.remove(this);
        }
        isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String getName() {
        enforceDeletionInvariant("getName");
        enforceAssignmentInvariant("getName");
        if (name == null || name.equals("")) {
            return ITEM_TYPE.getName();
        }
        else {
            return name;
        }
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariant("setName");
        enforceAssignmentInvariant("setName");
        this.name = name;
    }

    @Override
    public String getPluralName() {
        enforceDeletionInvariant("getPluralName");
        enforceAssignmentInvariant("getPluralName");
        if (pluralName == null || pluralName.equals("")) {
            return ITEM_TYPE.getPluralName();
        }
        else {
            return pluralName;
        }
    }

    @Override
    public void setPluralName(String pluralName) throws IllegalArgumentException {
        enforceDeletionInvariant("setPluralName");
        enforceAssignmentInvariant("setPluralName");
        this.pluralName = pluralName;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariant("getInterfaceName");
        enforceAssignmentInvariant("getInterfaceName");
        return Item.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Item item)) {
            return false;
        }
        if (isDeleted || item.isDeleted()) {
            return false;
        }
        return item.uuid().equals(UUID);
    }

    private void enforceDeletionInvariant(String methodName) {
        if (isDeleted) {
            throw new EntityDeletedException("ItemImpl." + methodName + ": Item is deleted");
        }
    }

    private void enforceAssignmentInvariant(String methodName) {
        if (characterEquipmentSlotsCharacter != null &&
                characterEquipmentSlotsCharacter.equipmentSlots()
                        .itemInSlot(characterEquipmentSlotType) != this) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned CharacterEquipmentSlot does not contain this Item");
        }
        if (characterInventoryCharacter != null &&
                !characterInventoryCharacter.inventory().contains(this)) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned CharacterInventory does not contain this Item");
        }
        if (containingTile != null && !containingTile.items().contains(this)) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned TileItems does not contain this Item");
        }
        if (containingTileFixture != null && !containingTileFixture.items().contains(this)) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned TileItems does not contain this Item");
        }
    }

    @Override
    public Vertex getTileOffset() throws IllegalStateException, EntityDeletedException {
        enforceDeletionInvariant("getTileOffset");
        enforceAssignmentInvariant("getTileOffset");
        return tileOffset;
    }

    @Override
    public void setTileOffset(Vertex tileOffset)
            throws IllegalArgumentException, IllegalStateException, EntityDeletedException {
        enforceDeletionInvariant("setTileOffset");
        enforceAssignmentInvariant("setTileOffset");
        this.tileOffset = Check.ifNull(tileOffset, "tileOffset");
    }
}
