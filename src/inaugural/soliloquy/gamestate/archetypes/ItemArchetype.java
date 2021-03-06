package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.ruleset.entities.ItemType;

public class ItemArchetype implements Item {

    @Override
    public VariableCache data() throws IllegalStateException {
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
    public EntityUuid uuid() {
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

    @Override
    public ItemType type() throws IllegalStateException {
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
    public Character inventoryCharacter() throws IllegalStateException {
        return null;
    }

    @Override
    public Pair<Character, String> equipmentSlot() throws IllegalStateException {
        return null;
    }

    @Override
    public TileFixture tileFixture() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignEquipmentSlotAfterAddedToCharacterEquipmentSlot(Character character, String s) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignInventoryCharacterAfterAddedToCharacterInventory(Character character) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileFixtureAfterAddedItemToTileFixtureItems(TileFixture tileFixture) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public Tile tile() throws IllegalStateException {
        return null;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public float getXTileWidthOffset() throws IllegalStateException, EntityDeletedException {
        return 0;
    }

    @Override
    public float getYTileHeightOffset() throws IllegalStateException, EntityDeletedException {
        return 0;
    }

    @Override
    public void setXTileWidthOffset(float v) throws IllegalStateException, EntityDeletedException {

    }

    @Override
    public void setYTileHeightOffset(float v) throws IllegalStateException, EntityDeletedException {

    }
}
