package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.common.CanGetInterfaceName;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
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
    private final VariableCacheFactory DATA_FACTORY;

    private final HashMap<String,Object> ENTITY_FACTORIES = new HashMap<>();

    public CharacterEntitiesOfTypeFactoryImpl(CollectionFactory collectionFactory,
                                              VariableCacheFactory dataFactory) {
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "collectionFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType extends CharacterEntityOfType<TEntityType>>
    CharacterEntitiesOfType make(Character character, TCharacterEntityOfType archetype)
            throws IllegalArgumentException, UnsupportedOperationException {
        Check.ifNull(character, "character");
        Check.ifNull(archetype, "archetype");
        if (!ENTITY_FACTORIES.containsKey(getProperTypeName(archetype))) {
            throw new IllegalArgumentException(
                    "CharacterEntitiesOfTypeFactoryImpl.make: no factory registered for " +
                            "archetype");
        }
        return new CharacterEntitiesOfTypeImpl<>(
                character,
                ((Function<Character, Function<TEntityType, Function<VariableCache, TCharacterEntityOfType>>>)
                        ENTITY_FACTORIES.get(getProperTypeName(archetype))),
                COLLECTION_FACTORY,
                DATA_FACTORY,
                archetype);
    }

    @Override
    public <TEntityType extends HasId, TCharacterEntityOfType
            extends CharacterEntityOfType<TEntityType>> void registerFactory(
                    TCharacterEntityOfType archetype,
                    Function<Character,Function<TEntityType,Function<VariableCache,
                            TCharacterEntityOfType>>> factory)
            throws IllegalArgumentException {
        ENTITY_FACTORIES.put(
                getProperTypeName(Check.ifNull(archetype, "archetype")),
                Check.ifNull(factory, "factory"));
    }

    @Override
    public String getInterfaceName() {
        return CharacterEntitiesOfTypeFactory.class.getCanonicalName();
    }
}
