package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
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
    public void setCharges(int i) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {

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
    public void assignCharacterInventoryToItem(Character character) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignCharacterEquipmentSlotToItem(CharacterEquipmentSlots characterEquipmentSlots, String s) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileToItem(Tile iTile) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileFixtureToItem(TileFixture iTileFixture) throws IllegalArgumentException, IllegalStateException {

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
