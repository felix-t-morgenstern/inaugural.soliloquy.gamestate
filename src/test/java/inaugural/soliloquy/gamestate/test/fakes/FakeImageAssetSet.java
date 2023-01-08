package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.graphics.assets.ImageAsset;
import soliloquy.specs.graphics.assets.ImageAssetSet;

public class FakeImageAssetSet implements ImageAssetSet {
    private final String ID;

    public FakeImageAssetSet() {
        ID = null;
    }

    public FakeImageAssetSet(String id) {
        ID = id;
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public ImageAsset getImageAssetForTypeAndDirection(String s, Direction direction)
            throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean supportsMouseEventCapturing() {
        return false;
    }
}
