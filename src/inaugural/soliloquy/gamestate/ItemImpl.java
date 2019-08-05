package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemImpl implements Item {
    private final EntityUuid ID;
    private final ItemType ITEM_TYPE;
    private final GenericParamsSet DATA;
    private final PairFactory PAIR_FACTORY;
    private final EntityUuidFactory ENTITY_UUID_FACTORY;

    private int _charges;
    private int _numberInStack;
    private CharacterInventory _characterInventory;
    private CharacterEquipmentSlots _characterEquipmentSlots;
    private String _characterEquipmentSlotType;
    private TileItems _tileItems;
    private TileFixtureItems _tileFixtureItems;
    private boolean _isDeleted;
    private String _name;
    private String _pluralName;

    public ItemImpl(EntityUuid id, ItemType itemType, GenericParamsSet data,
                    PairFactory pairFactory, EntityUuidFactory entityUuidFactory) {
        ID = id;
        ITEM_TYPE = itemType;
        DATA = data;
        PAIR_FACTORY = pairFactory;
        ENTITY_UUID_FACTORY = entityUuidFactory;
    }

    @Override
    public ItemType itemType() throws IllegalStateException {
        enforceDeletionInvariant("itemType", true);
        enforceAssignmentInvariant("itemType");
        return ITEM_TYPE;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        enforceDeletionInvariant("getCharges", true);
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
        enforceDeletionInvariant("setCharges", true);
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
        enforceDeletionInvariant("getNumberInStack", true);
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
        enforceDeletionInvariant("setNumberInStack", true);
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
        enforceDeletionInvariant("takeFromStack", true);
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
    public Pair<CharacterEquipmentSlots, String> getCharacterEquipmentSlot()
            throws IllegalStateException {
        enforceDeletionInvariant("getCharacterEquipmentSlot", true);
        enforceAssignmentInvariant("getCharacterEquipmentSlot");
        return _characterEquipmentSlots == null ?
                null :
                PAIR_FACTORY.make(_characterEquipmentSlots, _characterEquipmentSlotType);
    }

    @Override
    public CharacterInventory getCharacterInventory() throws IllegalStateException {
        enforceDeletionInvariant("getCharacterInventory", true);
        enforceAssignmentInvariant("getCharacterInventory");
        return _characterInventory;
    }

    @Override
    public TileFixtureItems getTileFixtureItems() throws IllegalStateException {
        enforceDeletionInvariant("getTileFixtureItems", true);
        enforceAssignmentInvariant("getTileFixtureItems");
        return _tileFixtureItems;
    }

    @Override
    public TileItems getTileItems() throws IllegalStateException {
        enforceDeletionInvariant("getTileItems", true);
        enforceAssignmentInvariant("getTileItems");
        return _tileItems;
    }

    @Override
    public void assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
            CharacterEquipmentSlots characterEquipmentSlots, String slotType)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant(
                "assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot", true);
        enforceAssignmentInvariant(
                "assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot");
        if (characterEquipmentSlots == null || slotType == null || slotType.equals("")) {
            _characterEquipmentSlots = null;
            _characterEquipmentSlotType = null;
        } else {
            _characterEquipmentSlots = characterEquipmentSlots;
            _characterEquipmentSlotType = slotType;
        }
        _characterInventory = null;
        _tileFixtureItems = null;
        _tileItems = null;
    }

    @Override
    public void assignCharacterInventoryToItemAfterAddingToCharacterInventory(
            CharacterInventory characterInventory)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignCharacterInventoryToItemAfterAddingToCharacterInventory",
                true);
        enforceAssignmentInvariant(
                "assignCharacterInventoryToItemAfterAddingToCharacterInventory");
        _characterInventory = characterInventory;
        _characterEquipmentSlots = null;
        _characterEquipmentSlotType = null;
        _tileFixtureItems = null;
        _tileItems = null;
    }

    @Override
    public void assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
            TileFixtureItems tileFixtureItems)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignTileFixtureToItemAfterAddingItemToTileFixtureItems", true);
        enforceAssignmentInvariant("assignTileFixtureToItemAfterAddingItemToTileFixtureItems");
        _tileFixtureItems = tileFixtureItems;
        _characterEquipmentSlots = null;
        _characterEquipmentSlotType = null;
        _characterInventory = null;
        _tileItems = null;
    }

    @Override
    public void assignTileItemsToItemAfterAddingItemToTileItems(TileItems tileItems)
            throws IllegalStateException, IllegalArgumentException {
        enforceDeletionInvariant("assignTileItemsToItemAfterAddingItemToTileItems", true);
        enforceAssignmentInvariant("assignTileFixtureToItemAfterAddingItemToTileFixtureItems");
        _tileItems = tileItems;
        _characterEquipmentSlots = null;
        _characterEquipmentSlotType = null;
        _characterInventory = null;
        _tileFixtureItems = null;
        enforceAssignmentInvariant("assignTileItemsToItemAfterAddingItemToTileItems");
    }

    @Override
    public EntityUuid id() {
        enforceDeletionInvariant("id", true);
        return ID;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        enforceDeletionInvariant("data", true);
        enforceAssignmentInvariant("data");
        return DATA;
    }

    @Override
    public void delete() throws IllegalStateException {
        enforceDeletionInvariant("delete", true);
        if (_characterEquipmentSlots != null) {
            _characterEquipmentSlots.equipItemToSlot(_characterEquipmentSlotType, null);
        }
        if (_characterInventory != null) {
            _characterInventory.remove(this);
        }
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getName() {
        enforceDeletionInvariant("getName", true);
        enforceAssignmentInvariant("getName");
        if (_name == null || _name.equals("")) {
            return ITEM_TYPE.getName();
        } else {
            return _name;
        }
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariant("setName", true);
        enforceAssignmentInvariant("setName");
        _name = name;
    }

    @Override
    public String getPluralName() {
        enforceDeletionInvariant("getPluralName", true);
        enforceAssignmentInvariant("getPluralName");
        if (_pluralName == null || _pluralName.equals("")) {
            return ITEM_TYPE.getPluralName();
        } else {
            return _pluralName;
        }
    }

    @Override
    public void setPluralName(String pluralName) throws IllegalArgumentException {
        enforceDeletionInvariant("setPluralName", true);
        enforceAssignmentInvariant("setPluralName");
        _pluralName = pluralName;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariant("getInterfaceName", true);
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

    private void enforceDeletionInvariant(String methodName, boolean cannotBeDeleted) {
        if (cannotBeDeleted && _isDeleted) {
            throw new IllegalStateException("ItemImpl." + methodName + ": Item is deleted");
        }
    }

    private void enforceAssignmentInvariant(String methodName) {
        if (_characterEquipmentSlots != null &&
                _characterEquipmentSlots.itemInSlot(_characterEquipmentSlotType) != this) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned CharacterEquipmentSlot does not contain this Item");
        }
        if (_characterInventory != null && !_characterInventory.contains(this)) {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned CharacterInventory does not contain this Item");
        }
        if (_tileItems != null && !_tileItems.contains(this))
        {
            throw new IllegalStateException("ItemImpl." + methodName +
                    ": assigned TileItems does not contain this Item");
        }
    }
}
