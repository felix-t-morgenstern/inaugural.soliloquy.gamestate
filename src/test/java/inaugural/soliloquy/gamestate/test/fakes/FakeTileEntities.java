package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileEntities;
import soliloquy.specs.gamestate.entities.TileEntity;

import java.util.*;
import java.util.function.Consumer;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class FakeTileEntities<TEntity extends TileEntity> implements TileEntities<TEntity> {
    public final Map<TEntity, Integer> ENTITIES = mapOf();
    public final Tile TILE;
    public final List<TEntity> REMOVED_ENTITIES = listOf();

    private boolean _isDeleted;
    private Consumer<TEntity> _actionAfterAdding;
    private Consumer<TEntity> _actionAfterRemoving;

    FakeTileEntities(Tile tile) {
        TILE = tile;
    }

    @Override
    public Map<TEntity, Integer> representation() {
        return null;
    }

    @Override
    public void initializeActionAfterAdding(Consumer<TEntity> actionAfterAdding) {
        _actionAfterAdding = actionAfterAdding;
    }

    @Override
    public void initializeActionAfterRemoving(Consumer<TEntity> actionAfterRemoving) {
        _actionAfterRemoving = actionAfterRemoving;
    }

    @Override
    public void add(TEntity entity) throws IllegalArgumentException {
        add(entity, 0);
        if (_actionAfterAdding != null) {
            _actionAfterAdding.accept(entity);
        }
    }

    @Override
    public void add(TEntity entity, int z) throws IllegalArgumentException {
        ENTITIES.put(entity, z);
        entity.assignTileAfterAddedToTileEntitiesOfType(TILE);
    }

    @Override
    public boolean remove(TEntity entity) throws IllegalArgumentException {
        REMOVED_ENTITIES.add(entity);
        if (_actionAfterRemoving != null) {
            _actionAfterRemoving.accept(entity);
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
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public Iterator<Pair<TEntity, Integer>> iterator() {
        Iterator<TEntity> entities = ENTITIES.keySet().iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return entities.hasNext();
            }

            @Override
            public Pair<TEntity, Integer> next() {
                TEntity entity = entities.next();
                return pairOf(entity, ENTITIES.get(entity));
            }
        };
    }
}
