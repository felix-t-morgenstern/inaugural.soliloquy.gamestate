package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;

import java.util.List;

// NB: AbstractTileEntity extends AbstractGameEventTargetEntity, since TileFixture implements both
// GameEventTargetEntity and TileEntity, but all other Tile entities do not. This is why there are
// two constructors--one for TileFixture, and one for the others--and why the two methods from
// GameEventTargetEntity throw UnsupportedOperationException when the implementation of this class
// is not a TileFixture.
public abstract class AbstractTileEntity<TEntity extends TileEntity>
        extends AbstractGameEventTargetEntity
        implements TileEntity {
    protected Tile tile;

    AbstractTileEntity() {
        super();
    }

    @Override
    protected String containingClassName() {
        return "Tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return tile;
    }

    @Override
    public List<GameMovementEvent> movementEvents() throws IllegalStateException {
        if (!(this instanceof TileFixture)) {
            throw new UnsupportedOperationException();
        }
        return super.movementEvents();
    }

    @Override
    public List<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        if (!(this instanceof TileFixture)) {
            throw new UnsupportedOperationException();
        }
        return super.abilityEvents();
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceCorrectTileInvariant(methodName);
        enforceDeletionInvariants();
    }

    @SuppressWarnings("unchecked")
    void enforceCorrectTileInvariant(String methodName) {
        if (tile != null && !getTileAggregation().contains((TEntity) this)) {
            throw new IllegalStateException(className() + methodName +
                    ": this is not present on its specified Tile");
        }
    }

    protected abstract String className();

    protected abstract TileEntities<TEntity> getTileAggregation();
}
