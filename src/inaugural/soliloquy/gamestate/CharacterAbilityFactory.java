package inaugural.soliloquy.gamestate;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterAbility;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityFactory<TType extends AbilityType>
        implements CharacterEntityOfTypeFactory<TType, CharacterAbility<TType>> {
    private final TType ARCHETYPE;

    @SuppressWarnings("ConstantConditions")
    public CharacterAbilityFactory(TType archetype) {
        if (archetype == null) {
            throw new IllegalArgumentException(
                    "CharacterAbilityFactory: archetype cannot be null");
        }
        ARCHETYPE = archetype;
    }

    @Override
    public CharacterAbility<TType> make(Character character, TType type)
            throws IllegalArgumentException {
        if (character == null) {
            throw new IllegalArgumentException(
                    "CharacterAbilityFactory.make: character cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException(
                    "CharacterAbilityFactory.make: type cannot be null");
        }
        return new CharacterAbilityImpl<>(character, type);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                ARCHETYPE.getInterfaceName() + "," + CharacterAbility.class.getCanonicalName() +
                "<" + ARCHETYPE.getInterfaceName() + ">>";
    }
}
