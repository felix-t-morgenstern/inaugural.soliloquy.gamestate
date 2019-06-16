package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.ICoordinate;
import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.IItem;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.ruleset.entities.IEquipmentType;
import soliloquy.specs.ruleset.entities.IItemType;
import soliloquy.specs.ruleset.entities.abilities.IActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.IPassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.IReactiveAbility;
import soliloquy.specs.sprites.entities.ISpriteSet;

public class ItemTypeStub implements IItemType {
    public static boolean _hasCharges = true;
    public static boolean _isStackable = true;

    public static IEquipmentType EQUIPMENT_TYPE = new EquipmentTypeStub();

    @Override
    public IItem generateOnTile(ITile iTile, ICoordinate iCoordinate, Integer integer, IGenericParamsSet iGenericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public IItem generateInInventory(ICharacter iCharacter, IGenericParamsSet iGenericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public IItem generateInEquipment(ICharacter iCharacter, String s, IGenericParamsSet iGenericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public IEquipmentType equipmentType() {
        return EQUIPMENT_TYPE;
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
