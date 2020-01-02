package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.TileWallSegment;
import soliloquy.specs.gamestate.factories.TileWallSegmentFactory;

public class TileWallSegmentFactoryImpl implements TileWallSegmentFactory {
    private final GenericParamsSetFactory DATA_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public TileWallSegmentFactoryImpl(GenericParamsSetFactory dataFactory) {
        if (dataFactory == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentFactoryImpl: dataFactory cannot be null");
        }
        DATA_FACTORY = dataFactory;
    }

    @Override
    public TileWallSegment make() {
        return make(DATA_FACTORY.make());
    }

    @Override
    public TileWallSegment make(GenericParamsSet data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "TileWallSegmentFactoryImpl.make: data cannot be null");
        }
        return new TileWallSegmentImpl(data);
    }

    @Override
    public String getInterfaceName() {
        return TileWallSegmentFactory.class.getCanonicalName();
    }
}
