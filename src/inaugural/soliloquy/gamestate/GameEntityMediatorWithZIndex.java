package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadOnlyMap;
import soliloquy.specs.gamestate.entities.Deletable;

import java.util.HashMap;

abstract class GameEntityMediatorWithZIndex<TEntity extends Deletable>
        extends HasDeletionInvariants {
    private final MapFactory MAP_FACTORY;
    private final HashMap<TEntity,Integer> ENTITIES;

    GameEntityMediatorWithZIndex(MapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(className() + ": mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
        ENTITIES = new HashMap<>();
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;

        for(java.util.Map.Entry<TEntity,Integer> entry : ENTITIES.entrySet()) {
            entry.getKey().delete();
        }
    }

    protected abstract TEntity getArchetype();

    public ReadOnlyMap<TEntity,Integer> representation() {
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
        if (entity == null) {
            throw new IllegalArgumentException(className() + ".add: entity must be non-null");
        }
        ENTITIES.put(entity, zIndex);
    }

    public boolean remove(TEntity entity) {
        enforceDeletionInvariants("remove");
        if (entity == null) {
            throw new IllegalArgumentException(className() + ".remove: entity must be non-null");
        }
        return ENTITIES.remove(entity) != null;
    }

    public boolean contains(TEntity entity) throws IllegalArgumentException {
        enforceDeletionInvariants("contains");
        if (entity == null) {
            throw new IllegalArgumentException(
                    "TileFixtures.contains: tileFixture must be non-null");
        }
        return ENTITIES.containsKey(entity);
    }
}
