package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityStub<TAbilityType extends AbilityType>
        implements CharacterEntityOfType<TAbilityType> {
    public boolean _isDeleted;
    public TAbilityType _type;
    public VariableCache _data = new VariableCacheStub();

    public CharacterAbilityStub() {
    }

    public CharacterAbilityStub(Character character, TAbilityType type) {
        _type = type;
    }

    public CharacterAbilityStub(Character character, TAbilityType type, VariableCache data) {
        this(character, type);
    }

    @Override
    public TAbilityType type() {
        return _type;
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfType.class.getCanonicalName() + "<" + _type.getInterfaceName() +
                ">";
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return _data;
    }
}
