package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemArchetype implements Item {
    @Override
    public ItemType itemType() throws IllegalStateException {
        return null;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        return null;
    }

    @Override
    public void setCharges(int i)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {

    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        return null;
    }

    @Override
    public void setNumberInStack(int i)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {

    }

    @Override
    public Item takeFromStack(int i)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public CharacterInventory getCharacterInventory() throws IllegalStateException {
        return null;
    }

    @Override
    public Pair<CharacterEquipmentSlots, String> getCharacterEquipmentSlot()
            throws IllegalStateException {
        return null;
    }

    @Override
    public TileItems getTileItems() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixtureItems getTileFixtureItems() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignCharacterInventoryToItemAfterAddingToCharacterInventory(
            CharacterInventory characterInventory)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
            CharacterEquipmentSlots characterEquipmentSlots, String s)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileItemsToItemAfterAddingItemToTileItems(TileItems tileItems)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileFixtureToItemAfterAddingItemToTileFixtureItems(
            TileFixtureItems tileFixtureItems)
            throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public EntityUuid id() {
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
    public String getInterfaceName() {
        return Item.class.getCanonicalName();
    }
}
