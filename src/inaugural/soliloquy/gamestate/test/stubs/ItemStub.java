package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.IGenericParamsSet;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.valueobjects.IEntityUuid;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.ruleset.entities.IItemType;

public class ItemStub implements IItem {
    private final IItemType ITEM_TYPE = new ItemTypeStub();

    private boolean _deleted;

    @Override
    public IItemType itemType() throws IllegalStateException {
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
    public IItem takeFromStack(int i) throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException {
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
