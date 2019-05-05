package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterEquipmentSlot;
import soliloquy.gamestate.specs.IItem;
import soliloquy.gamestate.specs.ITile;
import soliloquy.ruleset.gameentities.abilities.specs.IActiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IPassiveAbility;
import soliloquy.ruleset.gameentities.abilities.specs.IReactiveAbility;
import soliloquy.ruleset.gameentities.specs.IEquipmentType;
import soliloquy.ruleset.gameentities.specs.IItemType;
import soliloquy.sprites.specs.ISprite;
import soliloquy.sprites.specs.ISpriteSet;

public class ItemTypeStub implements IItemType {
    public static boolean _hasCharges = true;
    public static boolean _isStackable = true;

    @Override
    public IItem generateOnTile(ITile iTile, ICoordinate iCoordinate, Integer integer, IGenericParamsSet iGenericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public IItem generateInInventory(ICharacter iCharacter, IGenericParamsSet iGenericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public IItem generateInEquipment(ICharacterEquipmentSlot iCharacterEquipmentSlot, IGenericParamsSet iGenericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public IEquipmentType getEquipmentType() {
        return null;
    }

    @Override
    public IFunction<ICharacter, String> getDescriptionFunction() {
        return null;
    }

    @Override
    public void setDescriptionFunction(IFunction<ICharacter, String> iFunction) {

    }

    @Override
    public IGenericParamsSet traits() {
        return null;
    }

    @Override
    public boolean isStackable() {
        return _isStackable;
    }

    @Override
    public int defaultNumberInStack() throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public boolean hasCharges() {
        return _hasCharges;
    }

    @Override
    public int defaultCharges() throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public ICoordinate defaultTilePixelOffset() {
        return null;
    }

    @Override
    public ICollection<IActiveAbility> activeAbilities() {
        return null;
    }

    @Override
    public ICollection<IReactiveAbility> reactiveAbilities() {
        return null;
    }

    @Override
    public ICollection<IPassiveAbility> passiveAbilities() {
        return null;
    }

    @Override
    public ISpriteSet spriteSet() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
