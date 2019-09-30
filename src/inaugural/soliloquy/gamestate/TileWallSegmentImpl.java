package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.entities.TileWallSegmentDirection;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentImpl extends HasDeletionInvariants implements TileWallSegment {
    private final PairFactory PAIR_FACTORY;
    private final GenericParamsSet DATA;

    private WallSegmentType _wallSegmentType;
    private int _height;
    private int _zIndex;
    private String _name;
    private Tile _tile;
    private TileWallSegmentDirection _tileWallSegmentDirection;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentImpl(PairFactory pairFactory,
                               GenericParamsSetFactory genericParamsSetFactory) {
        if (pairFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegment: pairFactory must be non-null");
        }
        PAIR_FACTORY = pairFactory;
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
    public Pair<TileWallSegmentDirection, Tile> getTile() {
        enforceDeletionInvariants("getTile");
        enforceAggregateAssignmentInvariant("getTile");
        if (_tile != null) {
            return PAIR_FACTORY.make(_tileWallSegmentDirection, _tile);
        } else {
            return null;
        }
    }

    @Override
    public void assignTileWallSegmentsToTileAfterAddingToTileWallSegments(
            TileWallSegmentDirection tileWallSegmentDirection, Tile tile)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("assignTileWallSegmentsToTileAfterAddingToTileWallSegments");
        enforceAggregateAssignmentInvariant(
                "assignTileWallSegmentsToTileAfterAddingToTileWallSegments");
        _tile = tile;
        if (tile != null) {
            _tileWallSegmentDirection = tileWallSegmentDirection;
        } else {
            _tileWallSegmentDirection = null;
        }
        enforceAggregateAssignmentInvariant("setName");
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
    public void delete() throws IllegalStateException {
        _isDeleted = true;
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
    protected boolean containingObjectIsDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        enforceDeletionInvariants("getInterfaceName");
        return TileWallSegment.class.getCanonicalName();
    }

    private void enforceAggregateAssignmentInvariant(String methodName) {
        if (_tile != null) {
            TileWallSegmentDirection tileWallSegmentDirection =
                    _tile.tileWallSegments().getDirection(this);
            if (tileWallSegmentDirection == TileWallSegmentDirection.NOT_FOUND) {
                throw new IllegalStateException("TileWallSegmentImpl." + methodName +
                        ": This TileWallSegment not found in Tile to which it was assigned");
            }
            if (tileWallSegmentDirection != _tileWallSegmentDirection) {
                throw new IllegalStateException("TileWallSegmentImpl." + methodName +
                        ": This TileWallSegment was found, but in the wrong direction");
            }
        }
    }
}
