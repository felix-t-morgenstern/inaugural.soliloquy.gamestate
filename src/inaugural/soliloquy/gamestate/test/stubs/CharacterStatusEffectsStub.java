package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.IReadOnlyMap;
import soliloquy.specs.gamestate.entities.ICharacterStatusEffects;
import soliloquy.specs.ruleset.entities.IElement;
import soliloquy.specs.ruleset.entities.abilities.IAbilitySource;

public class CharacterStatusEffectsStub implements ICharacterStatusEffects {
    public boolean _isDeleted;

    @Override
    public Integer getStatusEffectLevel(String s) throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    @Override
    public IReadOnlyMap<String, Integer> allStatusEffectsRepresentation() throws IllegalStateException {
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
