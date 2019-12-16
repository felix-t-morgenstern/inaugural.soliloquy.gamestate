package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;

import java.util.HashMap;
import java.util.Iterator;

public class TileEntitiesStub<TEntity extends TileEntity> implements TileEntities<TEntity> {
    public final HashMap<TEntity,Integer> ENTITIES = new HashMap<>();
    public final Tile TILE;
    public final Collection<TEntity> REMOVED_ENTITIES = new CollectionStub<>();

    private boolean _isDeleted;

    TileEntitiesStub(Tile tile) {
        TILE = tile;
    }

    @Override
    public ReadableMap<TEntity, Integer> representation() {
        return null;
    }

    @Override
    public void add(TEntity entity) throws IllegalArgumentException {
        add(entity,0);
    }

    @Override
    public void add(TEntity entity, int z) throws IllegalArgumentException {
        ENTITIES.put(entity,z);
        entity.assignTileAfterAddedToTileEntitiesOfType(TILE);
    }

    @Override
    public boolean remove(TEntity entity) throws IllegalArgumentException {
        REMOVED_ENTITIES.add(entity);
        return ENTITIES.remove(entity) != null;
    }

    @Override
    public int size() {
        return ENTITIES.size();
    }

    @Override
    public Integer getZIndex(TEntity entity) throws IllegalArgumentException {
        return null;
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
        return null;
    }

    @Override
    public TEntity getArchetype() {
        return null;
    }
}
