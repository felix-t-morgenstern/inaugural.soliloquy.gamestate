package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.graphics.assets.Image;
import soliloquy.specs.graphics.assets.Sprite;

public class SpriteStub implements Sprite {
    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public Image image() {
        return null;
    }

    @Override
    public int leftX() {
        return 0;
    }

    @Override
    public int topY() {
        return 0;
    }

    @Override
    public int rightX() {
        return 0;
    }

    @Override
    public int bottomY() {
        return 0;
    }
}
