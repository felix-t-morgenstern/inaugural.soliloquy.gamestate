package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.ruleset.entities.Element;
import soliloquy.specs.sprites.entities.SpriteSet;

public class ElementStub implements Element {
    @Override
    public double getResistance(Character character) {
        return 0;
    }

    @Override
    public SpriteSet spriteSet() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
