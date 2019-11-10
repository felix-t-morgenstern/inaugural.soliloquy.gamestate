package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemArchetype implements Item {
    @Override
    public ItemType type() throws IllegalStateException {
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
    public Character getInventoryCharacter() throws IllegalStateException {
        return null;
    }

    @Override
    public Pair<Character, String> getCharacterEquipmentSlot()
            throws IllegalStateException {
        return null;
    }

    @Override
    public Tile getContainingTile() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixture getContainingTileFixture() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignCharacterInventoryToItemAfterAddingToCharacterInventory(Character character)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileFixtureToItemAfterAddingItemToTileFixtureItems(TileFixture tileFixture) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void assignTileToItemAfterAddingItemToTileItems(Tile tile) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignCharacterEquipmentSlotToItemAfterAddingToCharacterEquipmentSlot(
            Character character, String s)
            throws IllegalStateException, IllegalArgumentException {

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
