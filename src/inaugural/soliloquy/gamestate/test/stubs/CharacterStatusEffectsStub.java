package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.ICharacterStatusEffects;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilitySource;
import soliloquy.ruleset.gameentities.specs.IElement;

public class CharacterStatusEffectsStub implements ICharacterStatusEffects {
    @Override
    public Integer getStatusEffectLevel(String s) throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    @Override
    public IMap<String, Integer> getAllStatusEffects() throws IllegalStateException {
        return null;
    }

    @Override
    public void alterStatusEffect(String s, int i, boolean b, IElement iElement, IAbilitySource iAbilitySource) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void setStatusEffectLevel(String s, int i) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void clearStatusEffects() throws IllegalStateException {

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
        return null;
    }
}
