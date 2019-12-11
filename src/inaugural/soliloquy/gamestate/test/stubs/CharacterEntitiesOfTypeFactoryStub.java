package inaugural.soliloquy.gamestate.test.stubs;

import inaugural.soliloquy.common.CanGetInterfaceName;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.factories.CharacterEntitiesOfTypeFactory;

import java.util.HashMap;
import java.util.function.Function;

public class CharacterEntitiesOfTypeFactoryStub extends CanGetInterfaceName
        implements CharacterEntitiesOfTypeFactory {
    private HashMap<String,Object> FACTORIES = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType extends
            CharacterEntityOfType<TEntityType>> CharacterEntitiesOfType<TEntityType,
                TCharacterEntityOfType> make(Character character, TCharacterEntityOfType archetype)
            throws IllegalArgumentException {
        return new CharacterEntitiesOfTypeStub<>(character,
                ((Function<TEntityType, Function<Character, TCharacterEntityOfType>>) FACTORIES
                        .get(getProperTypeName(archetype))));
    }

    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType extends
            CharacterEntityOfType<TEntityType>> void registerFactory(TCharacterEntityOfType archetype,
                 Function<TEntityType, Function<Character, TCharacterEntityOfType>> factory)
            throws IllegalArgumentException {
        FACTORIES.put(getProperTypeName(archetype), factory);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
