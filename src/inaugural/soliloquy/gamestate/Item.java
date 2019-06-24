package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.valueobjects.IEntityUuid;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.common.valueobjects.IPair;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.entities.IItemType;

public class Item implements IItem {
    private final IEntityUuid ID;
    private final IItemType ITEM_TYPE;

    private int _charges;
    private int _numberInStack;

    public Item(IEntityUuid id, IItemType itemType) {
        ID = id;
        ITEM_TYPE = itemType;
    }

    @Override
    public IItemType itemType() throws IllegalStateException {
        return ITEM_TYPE;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        if (!ITEM_TYPE.hasCharges()) {
            return null;
        } else {
            return _charges;
        }
    }

    @Override
    public void setCharges(int charges) throws UnsupportedOperationException, IllegalStateException {
        if (!ITEM_TYPE.hasCharges()) {
            throw new UnsupportedOperationException(
                    "Item.setCharges: ItemType doesn't have charges");
        }
        if (charges < 0) {
            throw new IllegalArgumentException(String.format(
                    "Item.setCharges: charges (%d) cannot be less than 0", charges));
        }
        _charges = charges;
    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        if (!ITEM_TYPE.isStackable()) {
            return null;
        } else {
            return _numberInStack;
        }
    }

    @Override
    public void setNumberInStack(int numberInStack) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        if (!ITEM_TYPE.isStackable()) {
            throw new UnsupportedOperationException(
                    "Item.setNumberInStack: ItemType isn't stackable");
        }
        _numberInStack = numberInStack;
    }

    @Override
    public IItem takeFromStack(int numberToTake) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        // TODO: Implement and test this near the end
        return null;
    }

    @Override
    public ICharacter getInventoryCharacter() throws IllegalStateException {
        return null;
    }

    @Override
    public IPair<ICharacter, String> getCharacterEquipmentSlot() throws IllegalStateException {
        return null;
    }

    @Override
    public ITile getTile() throws IllegalStateException {
        return null;
    }

    @Override
    public ITileFixture getTileFixture() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignCharacterInventoryToItem(ICharacter iCharacter) throws IllegalStateException, IllegalArgumentException {
        // TODO: Implement and test!

    }

    @Override
    public void assignCharacterEquipmentSlotToItem(ICharacterEquipmentSlots iCharacterEquipmentSlots, String s) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileToItem(ITile iTile) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileFixtureToItem(ITileFixture iTileFixture) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public IEntityUuid id() {
        return ID;
    }

    @Override
    public IGenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getInterfaceName() {
        return IItem.class.getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IItem)) {
            return false;
        }
        IItem item = (IItem) o;
        if (item.isDeleted())
        {
            return false;
        }
        return item.id().equals(ID);
    }
}
