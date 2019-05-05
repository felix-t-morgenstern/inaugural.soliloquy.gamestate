package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IEntityUuid;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.*;
import soliloquy.ruleset.gameentities.specs.IItemType;

public class ItemStub implements IItem {
    private boolean _deleted;

    @Override
    public IItemType itemType() throws IllegalStateException {
        return null;
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

    }

    @Override
    public void assignCharacterEquipmentSlotToItem(ICharacterEquipmentSlot iCharacterEquipmentSlot) throws IllegalStateException, IllegalArgumentException {

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
