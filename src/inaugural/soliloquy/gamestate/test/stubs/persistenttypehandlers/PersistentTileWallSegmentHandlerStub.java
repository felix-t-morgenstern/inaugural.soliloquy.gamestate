package inaugural.soliloquy.gamestate.test.stubs.persistenttypehandlers;

import inaugural.soliloquy.gamestate.test.stubs.TileWallSegmentStub;
import soliloquy.specs.gamestate.entities.TileWallSegment;

public class PersistentTileWallSegmentHandlerStub
        extends PersistentValueTypeHandlerStub<TileWallSegment> {
    @Override
    public String typeName() {
        return "TileWallSegment";
    }

    @Override
    protected TileWallSegment generateInstance() {
        return new TileWallSegmentStub();
    }
}
