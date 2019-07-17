package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadOnlyMap;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;

public class CharacterStatusEffectsStub implements CharacterStatusEffects {
    public boolean _isDeleted;

    @Override
    public Integer getStatusEffectLevel(String s) throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    @Override
    public ReadOnlyMap<String, Integer> allStatusEffectsRepresentation() throws IllegalStateException {
        return null;
    }

    @Override
    public void alterStatusEffect(String s, int i, boolean b, Element element, AbilitySource abilitySource) throws IllegalStateException, IllegalArgumentException {

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
