package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.common.CanGetInterfaceName;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntitiesOfType;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;
import soliloquy.specs.gamestate.factories.CharacterEntitiesOfTypeFactory;

import java.util.HashMap;
import java.util.function.Function;

public class CharacterEntitiesOfTypeFactoryImpl extends CanGetInterfaceName
        implements CharacterEntitiesOfTypeFactory {
    private final CollectionFactory COLLECTION_FACTORY;
    private final HashMap<String,Object> ENTITY_FACTORIES = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    public CharacterEntitiesOfTypeFactoryImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEntitiesOfTypeFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType extends
            CharacterEntityOfType<TEntityType>> CharacterEntitiesOfType<TEntityType,
            TCharacterEntityOfType> make(Character character, TCharacterEntityOfType archetype)
            throws IllegalArgumentException {
        if (!ENTITY_FACTORIES.containsKey(getProperTypeName(archetype))) {
            throw new IllegalArgumentException(
                    "CharacterEntitiesOfTypeFactoryImpl.make: no factory registered for " +
                            "archetype");
        }
        return new CharacterEntitiesOfTypeImpl<>(
                character,
                ((Function<TEntityType,Function<Character,TCharacterEntityOfType>>)
                        ENTITY_FACTORIES.get(getProperTypeName(archetype))),
                COLLECTION_FACTORY,
                archetype);
    }

    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType extends
            CharacterEntityOfType<TEntityType>> void
                registerFactory(TCharacterEntityOfType archetype,
                                Function<TEntityType, Function<Character,
                                        TCharacterEntityOfType>> function)
            throws IllegalArgumentException {
        ENTITY_FACTORIES.put(getProperTypeName(archetype), function);
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntitiesOfTypeFactory.class.getCanonicalName();
    }
}
