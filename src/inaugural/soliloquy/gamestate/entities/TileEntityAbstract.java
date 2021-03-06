package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameAbilityEvent;
import soliloquy.specs.gamestate.entities.gameevents.GameMovementEvent;

// NB: TileEntityAbstract extends GameEventTargetEntityAbstract, since TileFixture implements both
// GameEventTargetEntity and TileEntity, but all other Tile entities do not. This is why there are
// two constructors--one for TileFixture, and one for the others--and why the two methods from
// GameEventTargetEntity throw UnsupportedOperationException when the implementation of this class
// is not a TileFixture.
public abstract class TileEntityAbstract<TEntity extends TileEntity>
        extends GameEventTargetEntityAbstract implements TileEntity {
    protected Tile _tile;

    TileEntityAbstract() {
        super();
    }

    TileEntityAbstract(ListFactory collectionFactory) {
        super(collectionFactory);
    }

    @Override
    protected String containingClassName() {
        return "Tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return _tile;
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
        if (_tile != null && !getTileAggregation().contains((TEntity)this)) {
            throw new IllegalStateException(className() + methodName +
                    ": this is not present on its specified Tile");
        }
    }

    protected abstract String className();

    protected abstract TileEntities<TEntity> getTileAggregation();
}
