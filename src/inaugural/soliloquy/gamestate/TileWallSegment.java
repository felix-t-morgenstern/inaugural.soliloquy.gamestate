package inaugural.soliloquy.gamestate;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IGenericParamsSetFactory;
import soliloquy.gamestate.specs.ITileWallSegment;
import soliloquy.ruleset.gameentities.specs.IWallSegmentType;

public class TileWallSegment extends HasDeletionInvariants implements ITileWallSegment {
    private final IGenericParamsSet DATA;

    private IWallSegmentType _wallSegmentType;
    private int _height;
    private int _zIndex;
    private String _name;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegment(IGenericParamsSetFactory genericParamsSetFactory) {
        if (genericParamsSetFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegment: genericParamsSetFactory must be non-null");
        }
        DATA = genericParamsSetFactory.make();
    }

    @Override
    public IWallSegmentType getWallSegmentType() throws IllegalStateException {
        enforceDeletionInvariants("getWallSegmentType");
        return _wallSegmentType;
    }

    @Override
    public void setWallSegmentType(IWallSegmentType wallSegmentType) throws IllegalStateException {
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
    public IGenericParamsSet data() throws IllegalStateException {
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
        return ITileWallSegment.class.getCanonicalName();
    }
}
