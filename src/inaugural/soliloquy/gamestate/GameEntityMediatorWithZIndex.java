package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Deletable;

import java.util.HashMap;

abstract class GameEntityMediatorWithZIndex<TEntity extends Deletable>
        extends HasDeletionInvariants {
    private final MapFactory MAP_FACTORY;
    final HashMap<TEntity,Integer> ENTITIES;

    GameEntityMediatorWithZIndex(MapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(className() + ": mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
        ENTITIES = new HashMap<>();
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        for(java.util.Map.Entry<TEntity,Integer> entry : ENTITIES.entrySet()) {
            entry.getKey().delete();
        }
    }

    protected abstract TEntity getArchetype();

    protected void enforceAssignmentInvariant(TEntity entity, String methodName) {
    }

    protected void assignEntityToAggregate(TEntity entity) {
    }

    protected void removeEntityFromAggregate(TEntity entity) {
    }

    public ReadableMap<TEntity,Integer> representation() {
        enforceDeletionInvariants("getRepresentation");
        Map<TEntity, Integer> entities = MAP_FACTORY.make(getArchetype(), 0);
        for(java.util.Map.Entry<TEntity,Integer> entry : ENTITIES.entrySet()) {
            entities.put(entry.getKey(), entry.getValue());
        }
        return entities.readOnlyRepresentation();
    }

    public void add(TEntity entity) throws IllegalArgumentException {
        add(entity, 0);
    }

    public void add(TEntity entity, int zIndex) throws IllegalArgumentException {
        enforceDeletionInvariants("add");
        enforceAssignmentInvariant(entity, "add");
        if (entity == null) {
            throw new IllegalArgumentException(className() + ".add: entity must be non-null");
        }
        if (entityIsPresentElsewhere(entity))
        {
            throw new IllegalArgumentException(className() + ".add: entity is present elsewhere");
        }
        ENTITIES.put(entity, zIndex);
        assignEntityToAggregate(entity);
    }

    abstract boolean entityIsPresentElsewhere(TEntity entity);

    public boolean remove(TEntity entity) {
        enforceDeletionInvariants("remove");
        enforceAssignmentInvariant(entity, "remove");
        if (entity == null) {
            throw new IllegalArgumentException(className() + ".remove: entity must be non-null");
        }
        boolean entityWasPresent = ENTITIES.remove(entity) != null;
        if (entityWasPresent) {
            removeEntityFromAggregate(entity);
        }
        return entityWasPresent;
    }

    public boolean contains(TEntity entity) throws IllegalArgumentException {
        enforceDeletionInvariants("contains");
        enforceAssignmentInvariant(entity, "contains");
        if (entity == null) {
            throw new IllegalArgumentException(
                    "TileFixtures.contains: tileFixture must be non-null");
        }
        return ENTITIES.containsKey(entity);
    }
}
