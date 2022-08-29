package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.gamestate.entities.abilities.AbilitySource;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;

public class FakeCharacterStatusEffects implements CharacterStatusEffects {
    public boolean _isDeleted;

    public Map<StatusEffectType, Integer> _representation = new FakeMap<>();

    @Override
    public Integer getStatusEffectLevel(StatusEffectType statusEffectType)
            throws IllegalStateException, IllegalArgumentException {
        return _representation.get(statusEffectType);
    }

    @Override
    public Map<StatusEffectType, Integer> representation() throws IllegalStateException {
        return _representation.makeClone();
    }

    @Override
    public void alterStatusEffect(StatusEffectType statusEffectType, int i, boolean b,
                                  Element element, AbilitySource abilitySource)
            throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void setStatusEffectLevel(StatusEffectType statusEffectType, int i)
            throws IllegalStateException, IllegalArgumentException {
        _representation.put(statusEffectType, i);
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
