package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.ICharacterStatusEffects;
import soliloquy.ruleset.gameentities.abilities.specs.IAbilitySource;
import soliloquy.ruleset.gameentities.specs.IElement;

public class CharacterStatusEffectsStub implements ICharacterStatusEffects {
    public boolean _isDeleted;

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
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}
