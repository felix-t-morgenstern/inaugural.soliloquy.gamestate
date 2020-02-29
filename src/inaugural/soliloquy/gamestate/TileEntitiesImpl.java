package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.common.infrastructure.ReadablePair;
import soliloquy.specs.gamestate.entities.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class TileEntitiesImpl<TEntity extends TileEntity> extends CanTellIfItemIsPresentElsewhere
        implements TileEntities<TEntity> {
    private final Tile TILE;
    private final TEntity ARCHETYPE;
    private final PairFactory PAIR_FACTORY;
    private final MapFactory MAP_FACTORY;
    final HashMap<TEntity,Integer> ENTITIES;

    private final static CanGetInterfaceName CAN_GET_INTERFACE_NAME = new CanGetInterfaceName();

    private Consumer<TEntity> _actionAfterAdding;
    private Consumer<TEntity> _actionAfterRemoving;

    @SuppressWarnings("ConstantConditions")
    public TileEntitiesImpl(Tile tile, TEntity archetype, PairFactory pairFactory,
                            MapFactory mapFactory) {
        if (tile == null) {
            throw new IllegalArgumentException("TileEntitiesImpl: tile must be non-null");
        }
        TILE = tile;
        if (archetype == null) {
            throw new IllegalArgumentException(
                    "TileEntitiesImpl: archetype must be non-null");
        }
        ARCHETYPE = archetype;
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "TileEntitiesImpl: pairFactory must be non-null");
        }
        PAIR_FACTORY = pairFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException(
                    "TileEntitiesImpl: mapFactory must be non-null");
        }
        MAP_FACTORY = mapFactory;

        ENTITIES = new HashMap<>();
    }

    public void add(TEntity entity) throws IllegalArgumentException {
        add(entity, 0);
    }

    @SuppressWarnings("ConstantConditions")
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
        entity.assignTileAfterAddedToTileEntitiesOfType(TILE);
        if (_actionAfterAdding != null) {
            _actionAfterAdding.accept(entity);
        }
    }

    private boolean entityIsPresentElsewhere(TEntity entity) {
        if (entity instanceof Item) {
            return itemIsPresentElsewhere((Item) entity);
        }
        else {
            return entity.tile() != null;
        }
    }

    @Override
    public Integer getZIndex(TEntity entity)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getZIndex");
        enforceAssignmentInvariant(entity, "getZIndex");
        if (entity == null) {
            throw new IllegalArgumentException(className() +
                    ".getZIndex: entity must be non-null");
        }
        if (!ENTITIES.containsKey(entity)) {
            return null;
        }
        return ENTITIES.get(entity);
    }

    @Override
    public void setZIndex(TEntity entity, int z)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("setZIndex");
        enforceAssignmentInvariant(entity, "setZIndex");
        if (entity == null) {
            throw new IllegalArgumentException(className() +
                    ".getZIndex: entity must be non-null");
        }
        if (!ENTITIES.containsKey(entity)) {
            throw new IllegalArgumentException(className() + ".getZIndex: entity is not present");
        }
        ENTITIES.put(entity, z);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean remove(TEntity entity) {
        enforceDeletionInvariants("remove");
        enforceAssignmentInvariant(entity, "remove");
        if (entity == null) {
            throw new IllegalArgumentException(className() + ".remove: entity must be non-null");
        }
        boolean entityWasPresent = ENTITIES.remove(entity) != null;
        if (entityWasPresent) {
            entity.assignTileAfterAddedToTileEntitiesOfType(TILE);
            if (_actionAfterRemoving != null) {
                _actionAfterRemoving.accept(entity);
            }
        }
        return entityWasPresent;
    }

    @SuppressWarnings("ConstantConditions")
    public boolean contains(TEntity entity) throws IllegalArgumentException {
        enforceDeletionInvariants("contains");
        enforceAssignmentInvariant(entity, "contains");
        if (entity == null) {
            throw new IllegalArgumentException(
                    "TileFixtures.contains: tileFixture must be non-null");
        }
        return ENTITIES.containsKey(entity);
    }

    @Override
    public int size() {
        return ENTITIES.size();
    }

    @Override
    public ReadableMap<TEntity, Integer> representation() throws IllegalStateException {
        enforceDeletionInvariants("representation");
        Map<TEntity, Integer> entities = MAP_FACTORY.make(ARCHETYPE, 0);
        ENTITIES.forEach(entities::put);
        return entities.readOnlyRepresentation();
    }

    @Override
    public void assignActionAfterAdding(Consumer<TEntity> actionAfterAdding) {
        _actionAfterAdding = actionAfterAdding;
    }

    @Override
    public void assignActionAfterRemoving(
            Consumer<TEntity> actionAfterRemoving) {
        _actionAfterRemoving = actionAfterRemoving;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return TileEntities.class.getCanonicalName() + "<" +
                CAN_GET_INTERFACE_NAME.getProperTypeName(getArchetype()) + ">";
    }

    @Override
    protected String className() {
        return "TileItems";
    }

    @Override
    protected String containingClassName() {
        return "tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return TILE;
    }

    @Override
    public TEntity getArchetype() {
        return ARCHETYPE;
    }

    private void enforceAssignmentInvariant(TEntity entity, String methodName) {
        if (entity != null && ENTITIES.containsKey(entity) &&
                entity.tile() != TILE) {
            throw new IllegalStateException("TileEntitiesImpl." + methodName +
                    ": entity is present in this class, but this class is not assigned to entity");
        }
    }

    @Override
    public void afterDeleted() throws IllegalStateException {
        for(java.util.Map.Entry<TEntity,Integer> entry : ENTITIES.entrySet()) {
            entry.getKey().delete();
        }
    }

    @Override
    public Iterator<ReadablePair<TEntity, Integer>> iterator() {
        enforceDeletionInvariants("iterator");
        Iterator<TEntity> entities = ENTITIES.keySet().iterator();
        Iterator<Integer> zIndices = ENTITIES.values().iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return entities.hasNext();
            }

            @Override
            public ReadablePair<TEntity, Integer> next() {
                return PAIR_FACTORY.make(entities.next(), zIndices.next()).representation();
            }
        };
    }
}
