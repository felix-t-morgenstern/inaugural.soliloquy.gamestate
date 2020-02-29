package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.ruleset.entities.abilities.ReactiveAbilityType;

public class CharacterReactiveAbilityArchetype implements CharacterEntityOfType<ReactiveAbilityType> {
    @Override
    public ReactiveAbilityType type() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfType.class.getCanonicalName() + "<" +
                ReactiveAbilityType.class.getCanonicalName() + ">";
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }
}
