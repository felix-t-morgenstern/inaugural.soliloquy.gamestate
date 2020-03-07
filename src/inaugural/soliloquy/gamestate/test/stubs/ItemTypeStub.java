package inaugural.soliloquy.gamestate.test.stubs;

import inaugural.soliloquy.gamestate.test.stubs.EquipmentTypeStub;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.EquipmentType;
import soliloquy.specs.ruleset.entities.ItemType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;
import soliloquy.specs.sprites.entities.SpriteSet;

public class ItemTypeStub implements ItemType {
    public static boolean _hasCharges = true;
    public static boolean _isStackable = true;

    private static EquipmentType EQUIPMENT_TYPE = new EquipmentTypeStub();

    public static String ITEM_TYPE_ID = "ItemTypeStubId";
    public static String ITEM_TYPE_NAME = "ItemTypeStubName";
    public static String ITEM_TYPE_PLURAL_NAME = "ItemTypeStubPluralName";

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
    public VariableCache traits() {
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
        return ITEM_TYPE_ID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String getName() {
        return ITEM_TYPE_NAME;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getPluralName() {
        return ITEM_TYPE_PLURAL_NAME;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }
}
