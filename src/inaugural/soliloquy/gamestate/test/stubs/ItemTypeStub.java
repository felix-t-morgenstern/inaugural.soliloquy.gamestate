package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.entities.EquipmentType;
import soliloquy.specs.ruleset.entities.ItemType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.sprites.entities.SpriteSet;

public class ItemTypeStub implements ItemType {
    public static boolean _hasCharges = true;
    public static boolean _isStackable = true;

    public static EquipmentType EQUIPMENT_TYPE = new EquipmentTypeStub();

    @Override
    public Item generateOnTile(Tile tile, Coordinate coordinate, Integer integer, GenericParamsSet genericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Item generateInInventory(Character character, GenericParamsSet genericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Item generateInEquipment(Character character, String s, GenericParamsSet genericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public EquipmentType equipmentType() {
        return EQUIPMENT_TYPE;
    }

    @Override
    public Function<Character, String> getDescriptionFunction() {
        return null;
    }

    @Override
    public void setDescriptionFunction(Function<Character, String> function) {

    }

    @Override
    public GenericParamsSet traits() {
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
    public Coordinate defaultTilePixelOffset() {
        return null;
    }

    @Override
    public Collection<ActiveAbility> activeAbilities() {
        return null;
    }

    @Override
    public Collection<ReactiveAbility> reactiveAbilities() {
        return null;
    }

    @Override
    public Collection<PassiveAbility> passiveAbilities() {
        return null;
    }

    @Override
    public SpriteSet spriteSet() {
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
