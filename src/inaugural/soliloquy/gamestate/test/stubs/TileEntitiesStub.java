package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class TileEntitiesStub<TEntity extends TileEntity> implements TileEntities<TEntity> {
    public final HashMap<TEntity,Integer> ENTITIES = new HashMap<>();
    public final Tile TILE;
    public final Collection<TEntity> REMOVED_ENTITIES = new CollectionStub<>();

    private boolean _isDeleted;
    private Consumer<TEntity> _addToGameZone;
    private Consumer<TEntity> _removeFromGameZone;

    TileEntitiesStub(Tile tile) {
        TILE = tile;
    }

    @Override
    public ReadableMap<TEntity, Integer> representation() {
        return null;
    }

    @Override
    public void assignAddToGameZoneActionAfterAddingToGameZone(Consumer<TEntity> addToGameZone) {
        _addToGameZone = addToGameZone;
    }

    @Override
    public void assignRemoveFromGameZoneActionAfterAddingToGameZone(Consumer<TEntity> removeFromGameZone) {
        _removeFromGameZone = removeFromGameZone;
    }

    @Override
    public void add(TEntity entity) throws IllegalArgumentException {
        add(entity,0);
        if (_addToGameZone != null) {
            _addToGameZone.accept(entity);
        }
    }

    @Override
    public void add(TEntity entity, int z) throws IllegalArgumentException {
        ENTITIES.put(entity,z);
        entity.assignTileAfterAddedToTileEntitiesOfType(TILE);
    }

    @Override
    public boolean remove(TEntity entity) throws IllegalArgumentException {
        REMOVED_ENTITIES.add(entity);
        if (_removeFromGameZone != null) {
            _removeFromGameZone.accept(entity);
        }
        return ENTITIES.remove(entity) != null;
    }

    @Override
    public int size() {
        return ENTITIES.size();
    }

    @Override
    public Integer getZIndex(TEntity entity) throws IllegalArgumentException {
        return ENTITIES.get(entity);
    }

    @Override
    public void setZIndex(TEntity entity, int z) throws IllegalArgumentException {

    }

    @Override
    public boolean contains(TEntity entity) throws IllegalArgumentException {
        return ENTITIES.containsKey(entity);
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

    @Override
    public Iterator<ReadablePair<TEntity, Integer>> iterator() {
        Iterator<TEntity> entities = ENTITIES.keySet().iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return entities.hasNext();
            }

            @Override
            public ReadablePair<TEntity, Integer> next() {
                TEntity entity = entities.next();
                return new PairStub<>(entity, ENTITIES.get(entity));
            }
        };
    }

    @Override
    public TEntity getArchetype() {
        return null;
    }
}
