package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemImpl implements Item {
    private final EntityUuid ID;
    private final ItemType ITEM_TYPE;
    private final GenericParamsSet DATA;
    private final PairFactory PAIR_FACTORY;
    private final EntityUuidFactory ENTITY_UUID_FACTORY;

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

    @SuppressWarnings("ConstantConditions")
    public ItemImpl(EntityUuid id, ItemType itemType, GenericParamsSet data,
                    PairFactory pairFactory, EntityUuidFactory entityUuidFactory) {
        if (id == null) {
            throw new IllegalArgumentException("ItemImpl: id cannot be null");
        }
        ID = id;
        if (itemType == null) {
            throw new IllegalArgumentException("ItemImpl: itemType cannot be null");
        }
        ITEM_TYPE = itemType;
        if (data == null) {
            throw new IllegalArgumentException("ItemImpl: data cannot be null");
        }
        DATA = data;
        if (pairFactory == null) {
            throw new IllegalArgumentException("ItemImpl: pairFactory cannot be null");
        }
        PAIR_FACTORY = pairFactory;
        if (entityUuidFactory == null) {
            throw new IllegalArgumentException("ItemImpl: entityUuidFactory cannot be null");
        }
        ENTITY_UUID_FACTORY = entityUuidFactory;
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
        if (charges < 0) {
            throw new IllegalArgumentException(String.format(
                    "ItemImpl.setCharges: charges (%d) cannot be less than 0", charges));
        }
        _charges = charges;
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
        Item takenFromStack = new ItemImpl(ENTITY_UUID_FACTORY.createRandomEntityUuid(),
                ITEM_TYPE, DATA.makeClone(), PAIR_FACTORY, ENTITY_UUID_FACTORY);
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
    public EntityUuid id() {
        enforceDeletionInvariant("id");
        return ID;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
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
        return item.id().equals(ID);
    }

    private void enforceDeletionInvariant(String methodName) {
        if (_isDeleted) {
            throw new IllegalStateException("ItemImpl." + methodName + ": Item is deleted");
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
}
