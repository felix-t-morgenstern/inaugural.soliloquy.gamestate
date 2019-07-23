package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemStub implements Item {
    private final ItemType ITEM_TYPE = new ItemTypeStub();

    private boolean _deleted;

    @Override
    public ItemType itemType() throws IllegalStateException {
        return ITEM_TYPE;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        return null;
    }

    @Override
    public void setCharges(int integer) throws UnsupportedOperationException, IllegalStateException {

    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        return null;
    }

    @Override
    public void setNumberInStack(int i) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {

    }

    @Override
    public Item takeFromStack(int i) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
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
        return null;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _deleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _deleted;
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
        return null;
    }
}
