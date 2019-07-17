package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.ruleset.entities.WallSegmentType;

public class TileWallSegmentImpl extends HasDeletionInvariants implements TileWallSegment {
    private final GenericParamsSet DATA;

    private WallSegmentType _wallSegmentType;
    private int _height;
    private int _zIndex;
    private String _name;

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
        return _wallSegmentType;
    }

    @Override
    public void setWallSegmentType(WallSegmentType wallSegmentType) throws IllegalStateException {
        enforceDeletionInvariants("setWallSegmentType");
        _wallSegmentType = wallSegmentType;
    }

    @Override
    public int getHeight() throws IllegalStateException {
        enforceDeletionInvariants("getHeight");
        return _height;
    }

    @Override
    public void setHeight(int height) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("setHeight");
        _height = height;
    }

    @Override
    public int getZIndex() throws IllegalStateException {
        enforceDeletionInvariants("getZIndex");
        return _zIndex;
    }

    @Override
    public void setZIndex(int zIndex) throws IllegalStateException {
        enforceDeletionInvariants("setZIndex");
        _zIndex = zIndex;
    }

    @Override
    public GenericParamsSet data() throws IllegalStateException {
        enforceDeletionInvariants("data");
        return DATA;
    }

    @Override
    public String getName() {
        enforceDeletionInvariants("getName");
        return _name;
    }

    @Override
    public void setName(String name) {
        enforceDeletionInvariants("setName");
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
}
