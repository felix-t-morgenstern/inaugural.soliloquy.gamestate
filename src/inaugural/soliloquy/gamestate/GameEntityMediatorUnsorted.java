package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.gamestate.specs.IDeletable;

import java.util.HashSet;

abstract class GameEntityMediatorUnsorted<TEntity extends IDeletable>
        extends HasDeletionInvariants {
    private final ICollectionFactory COLLECTION_FACTORY;
    private final HashSet<TEntity> ENTITIES;

    protected GameEntityMediatorUnsorted(ICollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(className() +
                    ": collectionFactory must be non-null");
        }
        COLLECTION_FACTORY = collectionFactory;
        ENTITIES = new HashSet<>();
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;

        for (TEntity entity : ENTITIES) {
            entity.delete();
        }
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    protected abstract TEntity getArchetype();

    public ICollection<TEntity> getRepresentation() {
        enforceDeletionInvariants("getRepresentation");
        ICollection<TEntity> representation = COLLECTION_FACTORY.make(getArchetype());
        for (TEntity entity : ENTITIES) {
            representation.add(entity);
        }
        return representation;
    }

    public void add(TEntity entity) throws IllegalArgumentException {
        enforceDeletionInvariants("add");
        if (entity == null) {
            throw new IllegalArgumentException(className() + ".add: entity must be non-null");
        }
        ENTITIES.add(entity);
    }

    public boolean remove(TEntity entity) {
        enforceDeletionInvariants("remove");
        if (entity == null) {
            throw new IllegalArgumentException(className() + ".remove: entity must be non-null");
        }
        return ENTITIES.remove(entity);
    }

    public boolean contains(TEntity entity) throws IllegalArgumentException {
        enforceDeletionInvariants("contains");
        if (entity == null) {
            throw new IllegalArgumentException(
                    "TileFixtures.contains: tileFixture must be non-null");
        }
        return ENTITIES.contains(entity);
    }
}
