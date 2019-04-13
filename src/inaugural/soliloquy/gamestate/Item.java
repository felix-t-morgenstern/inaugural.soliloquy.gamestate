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
    public String getInventoryCharacterId() throws IllegalStateException {
        return null;
    }

    @Override
    public void setInventoryCharacterId(String s) throws IllegalStateException, UnsupportedOperationException {

    }

    @Override
    public ICharacterEquipmentSlot getCharacterEquipmentSlot() throws IllegalStateException {
        return null;
    }

    @Override
    public void setCharacterEquipmentSlot(ICharacterEquipmentSlot iCharacterEquipmentSlot) throws IllegalStateException, UnsupportedOperationException {

    }

    @Override
    public ITile getTile() throws IllegalStateException {
        return null;
    }

    @Override
    public void setTile(ITile iTile) throws IllegalStateException, UnsupportedOperationException {

    }

    @Override
    public ITileFixture getTileFixture() throws IllegalStateException {
        return null;
    }

    @Override
    public void setTileFixture(ITileFixture iTileFixture) throws IllegalStateException, UnsupportedOperationException {

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
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.gamestate.specs.IItem";
    }
}
