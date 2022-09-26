package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.factories.TileWallSegmentFactory;

import java.util.ArrayList;
import java.util.List;

public class FakeTileWallSegmentFactory implements TileWallSegmentFactory {
    public final List<TileWallSegment> FROM_FACTORY = new ArrayList<>();

    @Override
    public TileWallSegment make() {
        TileWallSegment result = new FakeTileWallSegment();
        FROM_FACTORY.add(result);
        return result;
    }

    @Override
    public TileWallSegment make(VariableCache data) {
        TileWallSegment result = new FakeTileWallSegment(data);
        FROM_FACTORY.add(result);
        return result;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
