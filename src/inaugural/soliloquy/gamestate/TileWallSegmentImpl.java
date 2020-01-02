package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentImpl extends HasDeletionInvariants implements TileWallSegment {
    private final GenericParamsSet DATA;

    private WallSegmentType _type;
    private String _name;
    private Tile _tile;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentImpl(GenericParamsSet data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "TileWallSegment: data must be non-null");
        }
        DATA = data;
    }

    @Override
    public WallSegmentType getType() throws IllegalStateException {
        enforceDeletionInvariants("getType");
        enforceAggregateAssignmentInvariant("getType");
        return _type;
    }

    @Override
    public void setType(WallSegmentType wallSegmentType) throws IllegalStateException {
        enforceDeletionInvariants("setType");
        enforceAggregateAssignmentInvariant("setType");
        _type = wallSegmentType;
    }

    @Override
    public Tile tile() {
        enforceDeletionInvariants("tile");
        enforceAggregateAssignmentInvariant("tile");
        return _tile;
    }

    @Override
    public void assignTileAfterAddedToTileEntitiesOfType(Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("assignTileAfterAddedToTileEntitiesOfType");
        enforceAggregateAssignmentInvariant(
                "assignTileAfterAddedToTileEntitiesOfType");
        _tile = tile;
        enforceAggregateAssignmentInvariant("assignTileAfterAddedToTileEntitiesOfType");
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        enforceDeletionInvariants("data");
        enforceAggregateAssignmentInvariant("data");
        return DATA;
    }

    @Override
    public String getName() {
        enforceDeletionInvariants("getName");
        enforceAggregateAssignmentInvariant("getName");
        return _name;
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariants("setName");
        enforceAggregateAssignmentInvariant("setName");
        _name = name;
    }

    @Override
    protected String className() {
        return "TileWallSegment";
    }

    @Override
    protected String containingClassName() {
        return null;
    }

    @Override
    protected Deletable getContainingObject() {
        return null;
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
}
