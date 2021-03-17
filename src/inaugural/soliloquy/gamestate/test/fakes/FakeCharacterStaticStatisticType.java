package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.CharacterStaticStatisticType;

public class FakeCharacterStaticStatisticType implements CharacterStaticStatisticType {
    private final String ID;

    public FakeCharacterStaticStatisticType() {
        ID = null;
    }

    public FakeCharacterStaticStatisticType(String id) {
        ID = id;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String s) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public ImageAssetSet imageAssetSet() {
        return null;
    }
}
