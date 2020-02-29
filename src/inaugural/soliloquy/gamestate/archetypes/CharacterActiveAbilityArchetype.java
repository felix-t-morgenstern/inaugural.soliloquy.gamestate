package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.ruleset.entities.abilities.ActiveAbilityType;

public class CharacterActiveAbilityArchetype implements CharacterEntityOfType<ActiveAbilityType> {
    @Override
    public ActiveAbilityType type() {
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
                ActiveAbilityType.class.getCanonicalName() + ">";
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }
}
