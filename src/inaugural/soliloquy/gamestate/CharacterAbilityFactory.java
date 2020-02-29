package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.factories.CharacterEntityOfTypeFactory;
import soliloquy.specs.ruleset.entities.abilities.AbilityType;

public class CharacterAbilityFactory<TType extends AbilityType>
        implements CharacterEntityOfTypeFactory<TType, CharacterEntityOfType<TType>> {
    private final TType ARCHETYPE;
    private final VariableCacheFactory DATA_FACTORY;

    public CharacterAbilityFactory(TType archetype, VariableCacheFactory dataFactory) {
        ARCHETYPE = Check.ifNull(archetype, "archetype");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
    }

    @Override
    public CharacterEntityOfType<TType> make(Character character, TType type)
            throws IllegalArgumentException {
        return make(character, type, DATA_FACTORY.make());
    }

    // TODO: Test and implement
    @Override
    public CharacterEntityOfType<TType> make(Character character, TType type, VariableCache data)
            throws IllegalArgumentException {
        return new CharacterAbilityImpl<>(
                Check.ifNull(character, "character"),
                Check.ifNull(type, "type"),
                data);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntityOfTypeFactory.class.getCanonicalName() + "<" +
                ARCHETYPE.getInterfaceName() + "," +
                CharacterEntityOfType.class.getCanonicalName() + "<" + ARCHETYPE.getInterfaceName()
                + ">>";
    }
}
