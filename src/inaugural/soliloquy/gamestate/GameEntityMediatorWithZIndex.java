package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.gamestate.specs.IDeletable;

import java.util.HashMap;
import java.util.Map;

abstract class GameEntityMediatorWithZIndex<TEntity extends IDeletable>
        extends HasDeletionInvariants {
    private final IMapFactory MAP_FACTORY;
    private final HashMap<TEntity,Integer> ENTITIES;

    GameEntityMediatorWithZIndex(IMapFactory mapFactory) {
        if (mapFactory == null) {
            throw new IllegalArgumentException(className() + ": mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;
        ENTITIES = new HashMap<>();
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;

        for(Map.Entry<TEntity,Integer> entry : ENTITIES.entrySet()) {
            entry.getKey().delete();
        }
    }

    protected abstract TEntity getArchetype();

    public IMap<TEntity,Integer> getRepresentation() {
        enforceDeletionInvariants("getRepresentation");
        IMap<TEntity, Integer> representation = MAP_FACTORY.make(getArchetype(), 0);
        for(Map.Entry<TEntity,Integer> entry : ENTITIES.entrySet()) {
            representation.put(entry.getKey(), entry.getValue());
        }
        return representation;
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
