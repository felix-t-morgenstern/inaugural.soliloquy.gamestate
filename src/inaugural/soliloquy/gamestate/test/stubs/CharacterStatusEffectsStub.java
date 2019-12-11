package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.CharacterStatusEffects;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.ruleset.entities.StatusEffectType;
import soliloquy.specs.ruleset.entities.abilities.AbilitySource;

public class CharacterStatusEffectsStub implements CharacterStatusEffects {
    public boolean _isDeleted;

    public final StatusEffectType _type1 = new StatusEffectTypeStub("statusEffectType1");
    public final StatusEffectType _type2 = new StatusEffectTypeStub("statusEffectType2");
    public final StatusEffectType _type3 = new StatusEffectTypeStub("statusEffectType3");
    public Map<StatusEffectType, Integer> _representation = new MapStub<>();

    public CharacterStatusEffectsStub() {
    }

    public CharacterStatusEffectsStub(boolean addStuff) {
        _representation.put(_type1, 123);
        _representation.put(_type2, 456);
        _representation.put(_type3, 789);
    }

    @Override
    public Integer getStatusEffectLevel(StatusEffectType statusEffectType) throws IllegalStateException, IllegalArgumentException {
        return _representation.get(statusEffectType);
    }

    @Override
    public ReadableMap<StatusEffectType, Integer> representation() throws IllegalStateException {
        return _representation.readOnlyRepresentation();
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
