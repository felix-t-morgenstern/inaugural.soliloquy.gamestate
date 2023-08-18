package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.*;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;

public class TileEntitiesImpl<TEntity extends TileEntity> extends CanTellIfItemIsPresentElsewhere
        implements TileEntities<TEntity> {
    private final Tile TILE;
    private final TEntity ARCHETYPE;
    final Map<TEntity, Integer> ENTITIES;

    private final static CanGetInterfaceName CAN_GET_INTERFACE_NAME = new CanGetInterfaceName();

    private Consumer<TEntity> actionAfterAdding;
    private Consumer<TEntity> actionAfterRemoving;

    @SuppressWarnings("ConstantConditions")
    public TileEntitiesImpl(Tile tile, TEntity archetype) {
        TILE = Check.ifNull(tile, "tile");
        ARCHETYPE = Check.ifNull(archetype, "archetype");

        ENTITIES = mapOf();
    }

    public void add(TEntity entity) throws IllegalArgumentException {
        add(entity, 0);
    }

    @SuppressWarnings("ConstantConditions")
    public void add(TEntity entity, int zIndex) throws IllegalArgumentException {
        enforceDeletionInvariants();
        enforceAssignmentInvariant(entity, "add");
        if (entity == null) {
            throw new IllegalArgumentException("TileEntitiesImpl.add: entity must be non-null");
        }
        if (entityIsPresentElsewhere(entity)) {
            throw new IllegalArgumentException(
                    "TileEntitiesImpl.add: entity is present elsewhere");
        }
        ENTITIES.put(entity, zIndex);
        entity.assignTileAfterAddedToTileEntitiesOfType(TILE);
        if (actionAfterAdding != null) {
            actionAfterAdding.accept(entity);
        }
    }

    private boolean entityIsPresentElsewhere(TEntity entity) {
        if (entity instanceof Item item) {
            return itemIsPresentElsewhere(item);
        }
        else {
            return entity.tile() != null;
        }
    }

    @Override
    public Integer getZIndex(TEntity entity)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceAssignmentInvariant(entity, "getZIndex");
        Check.ifNull(entity, "entity");
        if (!ENTITIES.containsKey(entity)) {
            return null;
        }
        return ENTITIES.get(entity);
    }

    @Override
    public void setZIndex(TEntity entity, int z)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceAssignmentInvariant(entity, "setZIndex");
        Check.ifNull(entity, "entity");
        if (!ENTITIES.containsKey(entity)) {
            throw new IllegalArgumentException(
                    "TileEntitiesImpl.getZIndex: entity is not present");
        }
        ENTITIES.put(entity, z);
    }

    @SuppressWarnings("ConstantConditions")
    public boolean remove(TEntity entity) {
        enforceDeletionInvariants();
        enforceAssignmentInvariant(entity, "remove");
        Check.ifNull(entity, "entity");
        var entityWasPresent = ENTITIES.remove(entity) != null;
        if (entityWasPresent) {
            entity.assignTileAfterAddedToTileEntitiesOfType(TILE);
            if (actionAfterRemoving != null) {
                actionAfterRemoving.accept(entity);
            }
        }
        return entityWasPresent;
    }

    @SuppressWarnings("ConstantConditions")
    public boolean contains(TEntity entity) throws IllegalArgumentException {
        enforceDeletionInvariants();
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
    public Map<TEntity, Integer> representation() throws IllegalStateException {
        enforceDeletionInvariants();
        return mapOf(ENTITIES);
    }

    @Override
    public void initializeActionAfterAdding(Consumer<TEntity> actionAfterAdding) {
        if (this.actionAfterAdding != null) {
            throw new UnsupportedOperationException(
                    "TileEntitiesImpl.initializeActionAfterAdding: actionAfterAdding " +
                            "already assigned");
        }
        this.actionAfterAdding = actionAfterAdding;
    }

    @Override
    public void initializeActionAfterRemoving(
            Consumer<TEntity> actionAfterRemoving) {
        if (this.actionAfterRemoving != null) {
            throw new UnsupportedOperationException(
                    "TileEntitiesImpl.initializeActionAfterRemoving: actionAfterRemoving " +
                            "already assigned");
        }
        this.actionAfterRemoving = actionAfterRemoving;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants();
        return TileEntities.class.getCanonicalName() + "<" +
                CAN_GET_INTERFACE_NAME.getProperTypeName(archetype()) + ">";
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
    public TEntity archetype() {
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
        for (var entry : ENTITIES.entrySet()) {
            entry.getKey().delete();
        }
    }

    @Override
    public Iterator<Pair<TEntity, Integer>> iterator() {
        enforceDeletionInvariants();
        var entities = ENTITIES.keySet().iterator();
        var zIndices = ENTITIES.values().iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return entities.hasNext();
            }

            @Override
            public Pair<TEntity, Integer> next() {
                return pairOf(entities.next(), zIndices.next(), ARCHETYPE, 0);
            }
        };
    }
}
