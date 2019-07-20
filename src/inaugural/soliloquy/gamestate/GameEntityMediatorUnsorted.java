package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.gamestate.entities.Deletable;

import java.util.HashSet;

abstract class GameEntityMediatorUnsorted<TEntity extends Deletable>
        extends HasDeletionInvariants {
    private final CollectionFactory COLLECTION_FACTORY;
    private final HashSet<TEntity> ENTITIES;

    GameEntityMediatorUnsorted(CollectionFactory collectionFactory) {
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

    public ReadableCollection<TEntity> representation() {
        enforceDeletionInvariants("getRepresentation");
        Collection<TEntity> representation = COLLECTION_FACTORY.make(getArchetype());
        ENTITIES.forEach(representation::add);
        return representation.readOnlyRepresentation();
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
