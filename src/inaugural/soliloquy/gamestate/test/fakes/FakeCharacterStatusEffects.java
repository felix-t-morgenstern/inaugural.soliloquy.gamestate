package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;

public class FakeCharacterStatusEffects implements CharacterStatusEffects {
    public boolean _isDeleted;

    public final StatusEffectType _type1 = new FakeStatusEffectType("statusEffectType1");
    public final StatusEffectType _type2 = new FakeStatusEffectType("statusEffectType2");
    public final StatusEffectType _type3 = new FakeStatusEffectType("statusEffectType3");
    public Map<StatusEffectType, Integer> _representation = new FakeMap<>();

    public FakeCharacterStatusEffects() {
    }

    public FakeCharacterStatusEffects(boolean addStuff) {
        _representation.put(_type1, 123);
        _representation.put(_type2, 456);
        _representation.put(_type3, 789);
    }

    @Override
    public Integer getStatusEffectLevel(StatusEffectType statusEffectType) throws IllegalStateException, IllegalArgumentException {
        return _representation.get(statusEffectType);
    }

    @Override
    public Map<StatusEffectType, Integer> representation() throws IllegalStateException {
        return _representation.makeClone();
    }

    @Override
    public void alterStatusEffect(StatusEffectType statusEffectType, int i, boolean b, Element element, AbilitySource abilitySource) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void setStatusEffectLevel(StatusEffectType statusEffectType, int i) throws IllegalStateException, IllegalArgumentException {
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
