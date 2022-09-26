package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.ruleset.entities.Element;

public class ElementStub implements Element {
    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public ImageAssetSet imageAssetSet() {
        return null;
    }
}
