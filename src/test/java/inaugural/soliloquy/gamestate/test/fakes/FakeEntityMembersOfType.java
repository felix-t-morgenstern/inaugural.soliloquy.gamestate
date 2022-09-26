package inaugural.soliloquy.gamestate.test.fakes;

import inaugural.soliloquy.gamestate.test.stubs.VariableCacheStub;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.EntityMemberOfType;
import soliloquy.specs.gamestate.entities.EntityMembersOfType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class FakeEntityMembersOfType<TEntityType extends HasId,
        TCharacterEntity extends EntityMemberOfType<TEntityType>, TEntity extends Deletable>
        implements EntityMembersOfType<TEntityType, TCharacterEntity, TEntity> {
    public TEntity CONTAINING_ENTITY;
    public HashMap<TEntityType, TCharacterEntity> ENTITIES;
    public Function<TEntity, Function<TEntityType, Function<VariableCache, TCharacterEntity>>>
            FACTORY;

    public boolean _isDeleted;

    public FakeEntityMembersOfType(TEntity containingEntity,
                                   Function<TEntity, Function<TEntityType,
                                           Function<VariableCache, TCharacterEntity>>>
                                           factory) {
        CONTAINING_ENTITY = containingEntity;
        FACTORY = factory;
        ENTITIES = new HashMap<>();
    }

    @Override
    public void add(TEntityType entityType) throws IllegalArgumentException {
        add(entityType, new VariableCacheStub());
    }

    @Override
    public void add(TEntityType entityType, VariableCache data) throws IllegalArgumentException {
        if (!ENTITIES.containsKey(entityType)) {
            ENTITIES.put(entityType,
                    FACTORY.apply(CONTAINING_ENTITY).apply(entityType).apply(data));
        }
    }

    @Override
    public TCharacterEntity get(TEntityType entityType) throws IllegalArgumentException {
        return ENTITIES.get(entityType);
    }

    @Override
    public int size() {
        return ENTITIES.size();
    }

    @Override
    public boolean remove(TEntityType entityType) throws IllegalArgumentException {
        return ENTITIES.remove(entityType) != null;
    }

    @Override
    public boolean contains(TEntityType entityType) throws IllegalArgumentException {
        return ENTITIES.containsKey(entityType);
    }

    @Override
    public void clear() {
        ENTITIES.clear();
    }

    @Override
    public List<TCharacterEntity> representation() {
        List<TCharacterEntity> representation = new FakeList<>();
        representation.addAll(ENTITIES.values());
        return representation;
    }

    @Override
    public Iterator<TCharacterEntity> iterator() {
        return ENTITIES.values().iterator();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}
