package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.EquipmentType;
import soliloquy.specs.ruleset.entities.ItemType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbility;
import soliloquy.specs.ruleset.entities.abilities.PassiveAbility;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbility;

public class ItemTypeStub implements ItemType {
    public boolean HasCharges = true;
    public boolean IsStackable = true;

    private static EquipmentType EQUIPMENT_TYPE = new EquipmentTypeStub();

    public static String ITEM_TYPE_ID = "ItemTypeStubId";
    public static String ITEM_TYPE_NAME = "ItemTypeStubName";
    public static String ITEM_TYPE_PLURAL_NAME = "ItemTypeStubPluralName";

    public static final float DEFAULT_X_TILE_WIDTH_OFFSET = 0.135f;
    public static final float DEFAULT_Y_TILE_HEIGHT_OFFSET = 0.246f;

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
        return IsStackable;
    }

    @Override
    public int defaultNumberInStack() throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public boolean hasCharges() {
        return HasCharges;
    }

    @Override
    public int defaultCharges() throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public List<ActiveAbility> defaultActiveAbilities() {
        return null;
    }

    @Override
    public List<ReactiveAbility> defaultReactiveAbilities() {
        return null;
    }

    @Override
    public List<PassiveAbility> defaultPassiveAbilities() {
        return null;
    }

    @Override
    public ImageAssetSet imageAssetSet() {
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

    @Override
    public float defaultXTileWidthOffset() {
        return DEFAULT_X_TILE_WIDTH_OFFSET;
    }

    @Override
    public float defaultYTileHeightOffset() {
        return DEFAULT_Y_TILE_HEIGHT_OFFSET;
    }
}
