package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.ItemType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

// TODO: Consider extending HasDeletionInvariants
public class ItemImpl implements Item {
    private final UUID UUID;
    private final ItemType ITEM_TYPE;
    private final VariableCache DATA;
    private final PairFactory PAIR_FACTORY;
    private final Supplier<UUID> UUID_FACTORY;
    private final List<PassiveAbility> PASSIVE_ABILITIES;
    private final List<ActiveAbility> ACTIVE_ABILITIES;
    private final List<ReactiveAbility> REACTIVE_ABILITIES;

    private int _charges;
    private int _numberInStack;
    private Character _characterInventoryCharacter;
    private Character _characterEquipmentSlotsCharacter;
    private String _characterEquipmentSlotType;
    private Tile _containingTile;
    private TileFixture _containingTileFixture;
    private boolean _isDeleted;
    private String _name;
    private String _pluralName;
    private float _xTileWidthOffset;
    private float _yTileHeightOffset;

    @SuppressWarnings("ConstantConditions")
    public ItemImpl(UUID uuid, ItemType itemType, VariableCache data, PairFactory pairFactory,
                    Supplier<UUID> uuidFactory) {
        UUID = Check.ifNull(uuid, "uuid");
        ITEM_TYPE = Check.ifNull(itemType, "itemType");
        _xTileWidthOffset = ITEM_TYPE.defaultXTileWidthOffset();
        _yTileHeightOffset = ITEM_TYPE.defaultYTileHeightOffset();
        DATA = Check.ifNull(data, "data");
        PAIR_FACTORY = Check.ifNull(pairFactory, "pairFactory");
        UUID_FACTORY = Check.ifNull(uuidFactory, "uuidFactory");
        PASSIVE_ABILITIES = new ArrayList<>();
        ACTIVE_ABILITIES = new ArrayList<>();
        REACTIVE_ABILITIES = new ArrayList<>();
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
        } else {
            return _charges;
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
        _charges = Check.throwOnLtValue(charges, 0, "charges");
    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        enforceDeletionInvariant("getNumberInStack");
        enforceAssignmentInvariant("getNumberInStack");
        if (!ITEM_TYPE.isStackable()) {
            return null;
        } else {
            return _numberInStack;
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
        _numberInStack = numberInStack;
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
        if (numberToTake >= _numberInStack) {
            throw new IllegalArgumentException(
                    "ItemImpl.takeFromStack: numberToTake must be less than the number in the stack");
        }
        _numberInStack -= numberToTake;
        Item takenFromStack = new ItemImpl(UUID_FACTORY.get(),
                ITEM_TYPE, DATA.makeClone(), PAIR_FACTORY, UUID_FACTORY);
        takenFromStack.setNumberInStack(numberToTake);
        return takenFromStack;
    }

    @Override
    public Pair<Character, String> equipmentSlot()
            throws IllegalStateException {
        enforceDeletionInvariant("equipmentSlot");
        enforceAssignmentInvariant("equipmentSlot");
        return _characterEquipmentSlotsCharacter == null ?
                null :
                PAIR_FACTORY.make(_characterEquipmentSlotsCharacter, _characterEquipmentSlotType);
    }

    @Override
    public Character inventoryCharacter() throws IllegalStateException {
        enforceDeletionInvariant("inventoryCharacter");
        enforceAssignmentInvariant("inventoryCharacter");
        return _characterInventoryCharacter;
    }

    @Override
    public Tile tile() throws IllegalStateException {
        enforceDeletionInvariant("tile");
        enforceAssignmentInvariant("tile");
        return _containingTile;
    }

    @Override
    public TileFixture tileFixture() throws IllegalStateException {
        enforceDeletionInvariant("tileFixture");
        enforceAssignmentInvariant("tileFixture");
        return _containingTileFixture;
    }

    @Override
    public void assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(
            Character character, String slotType)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignEquipmentSlotAfterAddedToCharacterEquipmentSlot");
        enforceAssignmentInvariant("assignEquipmentSlotAfterAddedToCharacterEquipmentSlot");
        if (character == null || slotType == null || slotType.equals("")) {
            _characterEquipmentSlotsCharacter = null;
            _characterEquipmentSlotType = null;
        } else {
            _characterEquipmentSlotsCharacter = character;
            _characterEquipmentSlotType = slotType;
        }
        _characterInventoryCharacter = null;
        _containingTileFixture = null;
        _containingTile = null;
        enforceAssignmentInvariant("assignEquipmentSlotAfterAddedToCharacterEquipmentSlot");
    }

    @Override
    public void assignInventoryCharacterAfterAddedToCharacterInventory(Character character)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignInventoryCharacterAfterAddedToCharacterInventory");
        enforceAssignmentInvariant("assignInventoryCharacterAfterAddedToCharacterInventory");
        _characterInventoryCharacter = character;
        _characterEquipmentSlotsCharacter = null;
        _characterEquipmentSlotType = null;
        _containingTileFixture = null;
        _containingTile = null;
        enforceAssignmentInvariant("assignInventoryCharacterAfterAddedToCharacterInventory");
    }

    @Override
    public void assignTileFixtureAfterAddedItemToTileFixtureItems(TileFixture tileFixture)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignTileFixtureAfterAddedItemToTileFixtureItems");
        enforceAssignmentInvariant("assignTileFixtureAfterAddedItemToTileFixtureItems");
        _containingTileFixture = tileFixture;
        _characterEquipmentSlotsCharacter = null;
        _characterEquipmentSlotType = null;
        _characterInventoryCharacter = null;
        _containingTile = null;
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
        _containingTile = tile;
        _characterEquipmentSlotsCharacter = null;
        _characterEquipmentSlotType = null;
        _characterInventoryCharacter = null;
        _containingTileFixture = null;
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
        if (_characterEquipmentSlotsCharacter != null) {
            // TODO: Attempt to test whether unassign-then-remove pattern is followed here
            _characterEquipmentSlotsCharacter.equipmentSlots()
                    .equipItemToSlot(_characterEquipmentSlotType, null);
        }
        if (_characterInventoryCharacter != null) {
            // TODO: Attempt to test whether unassign-then-remove pattern is followed here
            CharacterInventory characterInventory = _characterInventoryCharacter.inventory();
            _characterInventoryCharacter = null;
            characterInventory.remove(this);
        }
        if (_containingTileFixture != null) {
            TileFixtureItems tileFixtureItems = _containingTileFixture.items();
            _containingTileFixture = null;
            tileFixtureItems.remove(this);
        }
        if (_containingTile != null) {
            TileEntities<Item> tileItems = _containingTile.items();
            _containingTile = null;
            tileItems.remove(this);
        }
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getName() {
        enforceDeletionInvariant("getName");
        enforceAssignmentInvariant("getName");
        if (_name == null || _name.equals("")) {
            return ITEM_TYPE.getName();
        } else {
            return _name;
        }
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariant("setName");
        enforceAssignmentInvariant("setName");
        _name = name;
    }

    @Override
    public String getPluralName() {
        enforceDeletionInvariant("getPluralName");
        enforceAssignmentInvariant("getPluralName");
        if (_pluralName == null || _pluralName.equals("")) {
            return ITEM_TYPE.getPluralName();
        } else {
            return _pluralName;
        }
    }

    @Override
    public void setPluralName(String pluralName) throws IllegalArgumentException {
        enforceDeletionInvariant("setPluralName");
        enforceAssignmentInvariant("setPluralName");
        _pluralName = pluralName;
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
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        if (_isDeleted || item.isDeleted())
        {
            return false;
        }
        return item.uuid().equals(UUID);
    }

    private void enforceDeletionInvariant(String methodName) {
        if (_isDeleted) {
            throw new EntityDeletedException("ItemImpl." + methodName + ": Item is deleted");
        }
    }

    private void enforceAssignmentInvariant(String methodName) {
        if (_characterEquipmentSlotsCharacter != null &&
                _characterEquipmentSlotsCharacter.equipmentSlots()
                        .itemInSlot(_characterEquipmentSlotType) != this) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned CharacterEquipmentSlot does not contain this Item");
        }
        if (_characterInventoryCharacter != null &&
                !_characterInventoryCharacter.inventory().contains(this)) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned CharacterInventory does not contain this Item");
        }
        if (_containingTile != null && !_containingTile.items().contains(this))
        {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned TileItems does not contain this Item");
        }
        if (_containingTileFixture != null && !_containingTileFixture.items().contains(this))
        {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned TileItems does not contain this Item");
        }
    }

    @Override
    public float getXTileWidthOffset() throws IllegalStateException, EntityDeletedException {
        enforceDeletionInvariant("getXTileWidthOffset");
        enforceAssignmentInvariant("getXTileWidthOffset");
        return _xTileWidthOffset;
    }

    @Override
    public float getYTileHeightOffset() throws IllegalStateException, EntityDeletedException {
        enforceDeletionInvariant("getYTileHeightOffset");
        enforceAssignmentInvariant("getYTileHeightOffset");
        return _yTileHeightOffset;
    }

    @Override
    public void setXTileWidthOffset(float xTileWidthOffset)
            throws IllegalStateException, EntityDeletedException {
        enforceDeletionInvariant("setXTileWidthOffset");
        enforceAssignmentInvariant("setXTileWidthOffset");
        _xTileWidthOffset = xTileWidthOffset;
    }

    @Override
    public void setYTileHeightOffset(float yTileHeightOffset)
            throws IllegalStateException, EntityDeletedException {
        enforceDeletionInvariant("setYTileHeightOffset");
        enforceAssignmentInvariant("setYTileHeightOffset");
        _yTileHeightOffset = yTileHeightOffset;
    }
}
