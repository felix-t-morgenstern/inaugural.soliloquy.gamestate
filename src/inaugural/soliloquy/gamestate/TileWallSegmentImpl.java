package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentImpl extends HasDeletionInvariants implements TileWallSegment {
    private final GenericParamsSet DATA;

    private WallSegmentType _wallSegmentType;
    private int _height;
    private int _zIndex;
    private String _name;
    private Tile _tile;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentImpl(GenericParamsSetFactory genericParamsSetFactory) {
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegment: genericParamsSetFactory must be non-null");
        }
        DATA = genericParamsSetFactory.make();
    }

    @Override
    public WallSegmentType getWallSegmentType() throws IllegalStateException {
        enforceDeletionInvariants("getWallSegmentType");
        enforceAggregateAssignmentInvariant("getWallSegmentType");
        return _wallSegmentType;
    }

    @Override
    public void setWallSegmentType(WallSegmentType wallSegmentType) throws IllegalStateException {
        enforceDeletionInvariants("setWallSegmentType");
        enforceAggregateAssignmentInvariant("setWallSegmentType");
        _wallSegmentType = wallSegmentType;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        enforceDeletionInvariants("getHeight");
        enforceAggregateAssignmentInvariant("getHeight");
        return _height;
    }

    @Override
    public void setHeight(int height) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("setHeight");
        enforceAggregateAssignmentInvariant("setHeight");
        _height = height;
    }

    @Override
    public int getZIndex() throws IllegalStateException {
        enforceDeletionInvariants("getZIndex");
        enforceAggregateAssignmentInvariant("getZIndex");
        return _zIndex;
    }

    @Override
    public void setZIndex(int zIndex) throws IllegalStateException {
        enforceDeletionInvariants("setZIndex");
        enforceAggregateAssignmentInvariant("setZIndex");
        _zIndex = zIndex;
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
