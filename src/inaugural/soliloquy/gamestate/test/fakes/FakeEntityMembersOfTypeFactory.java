package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.EntityMemberOfType;
import soliloquy.specs.gamestate.entities.EntityMembersOfType;
import soliloquy.specs.gamestate.factories.EntityMembersOfTypeFactory;

import java.util.HashMap;
import java.util.function.Function;

public class FakeEntityMembersOfTypeFactory extends CanGetInterfaceName
        implements EntityMembersOfTypeFactory {
    private HashMap<String, Object> FACTORIES = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <TEntityMemberType extends HasId,
            TEntityMember extends EntityMemberOfType<TEntityMemberType>,
            TEntity extends Deletable>
    EntityMembersOfType<TEntityMemberType, TEntityMember, TEntity> make(
            TEntity containingEntity,
            TEntityMemberType entityMemberTypeArchetype,
            TEntityMember entityMemberArchetype)
            throws IllegalArgumentException, UnsupportedOperationException {
        return new FakeEntityMembersOfType<>(containingEntity,
                ((Function<TEntity, Function<TEntityMemberType, Function<VariableCache,
                        TEntityMember>>>) FACTORIES
                        .get(getProperTypeName(entityMemberArchetype))));
    }

    @Override
    public <TEntityMemberType extends HasId,
            TEntityMember extends EntityMemberOfType<TEntityMemberType>,
            TEntity extends Deletable>
    void registerFactory(Function<TEntity, Function<TEntityMemberType, Function<VariableCache,
            TEntityMember>>> factory,
                         TEntityMemberType entityMemberTypeArchetype,
                         TEntityMember entityMemberArchetype,
                         TEntity entityArchetype)
            throws IllegalArgumentException {
        FACTORIES.put(getProperTypeName(entityMemberArchetype), factory);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
