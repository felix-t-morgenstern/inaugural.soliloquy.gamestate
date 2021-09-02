package inaugural.soliloquy.gamestate.entities;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.*;
import soliloquy.specs.gamestate.entities.gameevents.GameEventTarget;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentImpl extends AbstractGameEventTargetEntity implements TileWallSegment {
    private final VariableCache DATA;

    private WallSegmentType _type;
    private String _name;
    private Tile _tile;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentImpl(VariableCache data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "TileWallSegment: data must be non-null");
        }
        DATA = data;
    }

    @Override
    public WallSegmentType getType() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("getType");
        return _type;
    }

    @Override
    public void setType(WallSegmentType wallSegmentType) throws IllegalStateException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("setType");
        _type = wallSegmentType;
    }

    @Override
    public Tile tile() {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("tile");
        return _tile;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant(
                "assignTileAfterAddedToTileEntitiesOfType");
        _tile = tile;
        enforceAggregateAssignmentInvariant("assignTileAfterAddedToTileEntitiesOfType");
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("data");
        return DATA;
    }

    @Override
    public String getName() {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("getName");
        return _name;
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant("setName");
        _name = name;
    }

    @Override
    public GameEventTarget makeGameEventTarget() throws IllegalStateException {
        enforceInvariants("makeGameEventTarget");
        TileWallSegment tileWallSegment = this;
        return new GameEventTarget() {
            @Override
            public Tile tile() {
                return null;
            }

            @Override
            public TileFixture tileFixture() {
                return null;
            }

            @Override
            public TileWallSegment tileWallSegment() {
                return tileWallSegment;
            }

            @Override
            public String getInterfaceName() {
                return GameEventTarget.class.getCanonicalName();
            }
        };
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegment.class.getCanonicalName();
    }

    private void enforceAggregateAssignmentInvariant(String methodName) {
        if (_tile != null) {
            TileWallSegmentDirection tileWallSegmentDirection =
                    _tile.wallSegments().getDirection(this);
            if (tileWallSegmentDirection == TileWallSegmentDirection.NOT_FOUND) {
                throw new IllegalStateException("TileWallSegmentImpl." + methodName +
                        ": This TileWallSegment not found in Tile to which it was assigned");
            }
        }
    }

    @Override
    void enforceInvariants(String methodName) {
        enforceDeletionInvariants();
        enforceAggregateAssignmentInvariant(methodName);
    }

    @Override
    protected String containingClassName() {
        return "Tile";
    }

    @Override
    protected Deletable getContainingObject() {
        return _tile;
    }
}
