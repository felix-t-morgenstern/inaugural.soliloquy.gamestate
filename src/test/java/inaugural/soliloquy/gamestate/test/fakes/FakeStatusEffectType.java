package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.graphics.assets.ImageAsset;
import soliloquy.specs.ruleset.entities.StatusEffectType;

public class FakeStatusEffectType implements StatusEffectType {
    private final String ID;

    public FakeStatusEffectType() {
        ID = null;
    }

    public FakeStatusEffectType(String id) {
        ID = id;
    }

    @Override
    public boolean stopsAtZero() {
        return false;
    }

    @Override
    public String nameAtValue(int i) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public Pair<ImageAsset, Integer> getIcon(String s, Character character)
            throws IllegalArgumentException {
        return null;
    }

    @Override
    public EffectsOnCharacter onRoundEnd() {
        return null;
    }

    @Override
    public EffectsOnCharacter onTurnStart() {
        return null;
    }

    @Override
    public EffectsOnCharacter onTurnEnd() {
        return null;
    }
}
