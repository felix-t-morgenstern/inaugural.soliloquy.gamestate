package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.factories.TileWallSegmentFactory;

import java.util.ArrayList;
import java.util.List;

public class TileWallSegmentFactoryStub implements TileWallSegmentFactory {
    public final List<TileWallSegment> FROM_FACTORY = new ArrayList<>();

    @Override
    public TileWallSegment make() {
        TileWallSegment result = new TileWallSegmentStub();
        FROM_FACTORY.add(result);
        return result;
    }

    @Override
    public TileWallSegment make(GenericParamsSet data) {
        TileWallSegment result = new TileWallSegmentStub(data);
        FROM_FACTORY.add(result);
        return result;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
