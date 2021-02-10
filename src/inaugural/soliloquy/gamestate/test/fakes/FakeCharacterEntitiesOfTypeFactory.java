package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.factories.CharacterEntitiesOfTypeFactory;

import java.util.HashMap;
import java.util.function.Function;

public class FakeCharacterEntitiesOfTypeFactory extends CanGetInterfaceName
        implements CharacterEntitiesOfTypeFactory {
    private HashMap<String,Object> FACTORIES = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType extends
            CharacterEntityOfType<TEntityType>> CharacterEntitiesOfType<TEntityType,
                TCharacterEntityOfType> make(Character character, TCharacterEntityOfType archetype)
            throws IllegalArgumentException {
        return new FakeCharacterEntitiesOfType<>(character,
                ((Function<Character,Function<TEntityType,Function<VariableCache,
                        TCharacterEntityOfType>>>) FACTORIES
                        .get(getProperTypeName(archetype))));
    }

    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType
            extends CharacterEntityOfType<TEntityType>>
    void registerFactory(TCharacterEntityOfType archetype,
                         Function<Character,Function<TEntityType,Function<VariableCache,
                                 TCharacterEntityOfType>>> factory)
            throws IllegalArgumentException {
        FACTORIES.put(getProperTypeName(archetype), factory);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
