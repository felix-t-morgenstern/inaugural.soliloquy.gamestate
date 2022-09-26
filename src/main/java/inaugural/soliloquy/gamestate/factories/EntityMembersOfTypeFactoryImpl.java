package inaugural.soliloquy.gamestate.factories;

import inaugural.soliloquy.gamestate.entities.EntityMembersOfTypeImpl;
import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.EntityMemberOfType;
import soliloquy.specs.gamestate.entities.EntityMembersOfType;
import soliloquy.specs.gamestate.factories.EntityMembersOfTypeFactory;

import java.util.HashMap;
import java.util.function.Function;

public class EntityMembersOfTypeFactoryImpl extends CanGetInterfaceName
        implements EntityMembersOfTypeFactory {
    private final VariableCacheFactory DATA_FACTORY;

    private final HashMap<String, Object> ENTITY_FACTORIES = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    public EntityMembersOfTypeFactoryImpl(VariableCacheFactory dataFactory) {
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <TEntityMemberType extends HasId,
            TEntityMember extends EntityMemberOfType<TEntityMemberType>,
            TEntity extends Deletable>
    EntityMembersOfType make(TEntity containingEntity,
                             TEntityMemberType entityMemberTypeArchetype,
                             TEntityMember entityMemberArchetype)
            throws IllegalArgumentException, UnsupportedOperationException {
        Check.ifNull(containingEntity, "containingEntity");
        Check.ifNull(entityMemberArchetype, "entityMemberArchetype");
        if (!ENTITY_FACTORIES.containsKey(getProperTypeName(entityMemberArchetype))) {
            throw new IllegalArgumentException(
                    "EntityMembersOfTypeFactoryImpl.make: no factory registered for " +
                            "entityMemberArchetype");
        }
        return new EntityMembersOfTypeImpl<>(
                containingEntity,
                ((Function<TEntity, Function<TEntityMemberType, Function<VariableCache,
                        TEntityMember>>>)
                        ENTITY_FACTORIES.get(getProperTypeName(entityMemberArchetype))),
                DATA_FACTORY,
                entityMemberTypeArchetype,
                entityMemberArchetype);
    }

    @Override
    public <TEntityMemberType extends HasId,
            TEntityMember extends EntityMemberOfType<TEntityMemberType>,
            TEntity extends Deletable> void registerFactory(
            Function<TEntity, Function<TEntityMemberType, Function<VariableCache,
                    TEntityMember>>> factory,
            TEntityMemberType entityMemberTypeArchetype,
            TEntityMember entityMemberArchetype,
            TEntity entityArchetype)
            throws IllegalArgumentException {
        Check.ifNull(entityMemberTypeArchetype, "entityMemberTypeArchetype");
        Check.ifNull(entityArchetype, "entityArchetype");
        ENTITY_FACTORIES.put(
                getProperTypeName(Check.ifNull(entityMemberArchetype, "entityMemberArchetype")),
                Check.ifNull(factory, "factory"));
    }

    @Override
    public String getInterfaceName() {
        return EntityMembersOfTypeFactory.class.getCanonicalName();
    }
}
