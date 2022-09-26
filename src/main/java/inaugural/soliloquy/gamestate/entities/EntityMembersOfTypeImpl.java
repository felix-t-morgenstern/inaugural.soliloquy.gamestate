package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.EntityMemberOfType;
import soliloquy.specs.gamestate.entities.EntityMembersOfType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class EntityMembersOfTypeImpl<TEntityMemberType extends HasId,
        TEntityMember extends EntityMemberOfType<TEntityMemberType>, TEntity extends Deletable>
        extends HasDeletionInvariants
        implements EntityMembersOfType<TEntityMemberType, TEntityMember, TEntity> {
    private final TEntity CONTAINING_ENTITY;
    private final Function<TEntity, Function<TEntityMemberType, Function<VariableCache,
            TEntityMember>>>
            ENTITY_FACTORY;
    private final VariableCacheFactory DATA_FACTORY;
    private final TEntityMemberType ENTITY_MEMBER_TYPE_ARCHETYPE;
    private final TEntityMember ENTITY_MEMBER_ARCHETYPE;

    private static final CanGetInterfaceName CAN_GET_INTERFACE_NAME = new CanGetInterfaceName();

    final HashMap<TEntityMemberType, TEntityMember> ENTITIES = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    public EntityMembersOfTypeImpl(TEntity containingEntity,
                                   Function<TEntity, Function<TEntityMemberType,
                                           Function<VariableCache,
                                           TEntityMember>>> entityFactory,
                                   VariableCacheFactory dataFactory,
                                   TEntityMemberType entityMemberTypeArchetype,
                                   TEntityMember entityMemberArchetype) {
        CONTAINING_ENTITY = Check.ifNull(containingEntity, "containingEntity");
        ENTITY_FACTORY = Check.ifNull(entityFactory, "entityFactory");
        DATA_FACTORY = Check.ifNull(dataFactory, "dataFactory");
        ENTITY_MEMBER_TYPE_ARCHETYPE = Check.ifNull(entityMemberTypeArchetype,
                "entityMemberTypeArchetype");
        ENTITY_MEMBER_ARCHETYPE = Check.ifNull(entityMemberArchetype, "entityMemberArchetype");
    }

    @Override
    public void add(TEntityMemberType type) throws IllegalArgumentException {
        add(type, DATA_FACTORY.make());
    }

    @Override
    public void add(TEntityMemberType type, VariableCache data) throws IllegalArgumentException {
        enforceDeletionInvariants();
        if (!ENTITIES.containsKey(type)) {
            ENTITIES.put(Check.ifNull(type, "type"),
                    ENTITY_FACTORY.apply(CONTAINING_ENTITY).apply(type)
                            .apply(Check.ifNull(data, "data")));
        }
    }

    @Override
    public TEntityMember get(TEntityMemberType type) throws IllegalArgumentException {
        enforceDeletionInvariants();
        if (type == null) {
            throw new IllegalArgumentException(
                    "EntityMembersOfTypeImpl.get: type cannot be null");
        }
        return ENTITIES.get(type);
    }

    @Override
    public int size() {
        enforceDeletionInvariants();
        return ENTITIES.size();
    }

    @Override
    public boolean remove(TEntityMemberType type) throws IllegalArgumentException {
        enforceDeletionInvariants();
        if (type == null) {
            throw new IllegalArgumentException(
                    "EntityMembersOfTypeImpl.remove: type cannot be null");
        }
        return ENTITIES.remove(type) != null;
    }

    @Override
    public boolean contains(TEntityMemberType type) throws IllegalArgumentException {
        enforceDeletionInvariants();
        if (type == null) {
            throw new IllegalArgumentException(
                    "EntityMembersOfTypeImpl.contains: type cannot be null");
        }
        return ENTITIES.containsKey(type);
    }

    @Override
    public void clear() {
        enforceDeletionInvariants();
        ENTITIES.clear();
    }

    @Override
    public List<TEntityMember> representation() {
        enforceDeletionInvariants();
        return new ArrayList<>(ENTITIES.values());
    }

    @Override
    public Iterator<TEntityMember> iterator() {
        enforceDeletionInvariants();
        return ENTITIES.values().iterator();
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected Deletable getContainingObject() {
        return CONTAINING_ENTITY;
    }

    @Override
    public void delete() throws IllegalStateException {
        ENTITIES.values().forEach(Deletable::delete);
        ENTITIES.clear();
        super.delete();
    }

    @Override
    public String getInterfaceName() {
        return EntityMembersOfType.class.getCanonicalName() + "<" +
                CAN_GET_INTERFACE_NAME.getProperTypeName(ENTITY_MEMBER_TYPE_ARCHETYPE) + "," +
                CAN_GET_INTERFACE_NAME.getProperTypeName(ENTITY_MEMBER_ARCHETYPE) + "," +
                CAN_GET_INTERFACE_NAME.getProperTypeName(CONTAINING_ENTITY) + ">";
    }
}
