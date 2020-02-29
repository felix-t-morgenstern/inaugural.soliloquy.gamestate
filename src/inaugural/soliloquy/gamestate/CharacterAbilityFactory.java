package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityFactory<TType extends AbilityType>
        implements CharacterEntityOfTypeFactory<TType, CharacterAbility<TType>> {
    private final TType ARCHETYPE;

    public CharacterAbilityFactory(TType archetype) {
        ARCHETYPE = Check.ifNull(archetype, "archetype");
    }

    @Override
    public CharacterAbility<TType> make(Character character, TType type)
            throws IllegalArgumentException {
        return new CharacterAbilityImpl<>(
                Check.ifNull(character, "character"),
                Check.ifNull(type, "type"));
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                ARCHETYPE.getInterfaceName() + "," + CharacterAbility.class.getCanonicalName() +
                "<" + ARCHETYPE.getInterfaceName() + ">>";
    }
}
