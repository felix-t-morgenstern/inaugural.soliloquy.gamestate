package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.specs.IItemType;

public class Item implements IItem {
    @Override
    public IItemType itemType() throws IllegalStateException {
        return null;
    }

    @Override
    public Integer getCharges() throws IllegalStateException {
        return null;
    }

    @Override
    public Integer getNumberInStack() throws IllegalStateException {
        return null;
    }

    @Override
    public IItem takeFromStack(int i) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public ICharacter getInventoryCharacter() throws IllegalStateException {
        return null;
    }

    @Override
    public ICharacterEquipmentSlot getCharacterEquipmentSlot() throws IllegalStateException {
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
    public void assignCharacterEquipmentSlotToItem(ICharacterEquipmentSlot iCharacterEquipmentSlot) throws IllegalStateException, IllegalArgumentException {
        // TODO: Implement and test!

    }

    @Override
    public void assignTileToItem(ITile iTile) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void assignTileFixtureToItem(ITileFixture iTileFixture) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public IEntityUuid id() {
        return null;
    }

    @Override
    public IGameZone gameZone() throws IllegalStateException {
        return null;
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
        return "soliloquy.gamestate.specs.IItem";
    }
}
