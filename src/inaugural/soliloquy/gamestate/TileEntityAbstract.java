package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
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

    TileEntityAbstract(CollectionFactory collectionFactory) {
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
    public Collection<GameMovementEvent> movementEvents() throws IllegalStateException {
        if (!(this instanceof TileFixture)) {
            throw new UnsupportedOperationException();
        }
        return super.movementEvents();
    }

    @Override
    public Collection<GameAbilityEvent> abilityEvents() throws IllegalStateException {
        if (!(this instanceof TileFixture)) {
            throw new UnsupportedOperationException();
        }
        return super.abilityEvents();
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceCorrectTileInvariant(methodName);
        enforceDeletionInvariants(methodName);
    }

    @SuppressWarnings("unchecked")
    void enforceCorrectTileInvariant(String methodName) {
        if (_tile != null && !getTileAggregation().contains((TEntity)this)) {
            throw new IllegalStateException(className() + methodName +
                    ": this is not present on its specified Tile");
        }
    }

    protected abstract TileEntities<TEntity> getTileAggregation();
}
