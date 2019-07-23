package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemImpl implements Item {
    private final EntityUuid ID;
    private final ItemType ITEM_TYPE;

    private int _charges;
    private int _numberInStack;

    public ItemImpl(EntityUuid id, ItemType itemType) {
        ID = id;
        ITEM_TYPE = itemType;
    }

    @Override
    public ItemType itemType() throws IllegalStateException {
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
    public Item takeFromStack(int numberToTake) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        // TODO: Implement and test this near the end
        return null;
    }

    @Override
    public Character getInventoryCharacter() throws IllegalStateException {
        return null;
    }

    @Override
    public Pair<Character, String> getCharacterEquipmentSlot() throws IllegalStateException {
        return null;
    }

    @Override
    public Tile getTile() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixture getTileFixture() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignCharacterInventoryToItemAfterAddingToCharacterInventory(Character character)
            throws IllegalStateException, IllegalArgumentException {
        // TODO: Implement and test!

    }

    @Override
    public void assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
            CharacterEquipmentSlots characterEquipmentSlots, String s)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileToItemAfterAddingItemToTileItems(Tile tile)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TileFixture tileFixture)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public EntityUuid id() {
        return ID;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
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
        if (item.isDeleted())
        {
            return false;
        }
        return item.id().equals(ID);
    }
}
