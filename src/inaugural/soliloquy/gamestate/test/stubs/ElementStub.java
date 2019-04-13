package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.gamestate.specs.ICharacter;
import soliloquy.ruleset.gameentities.specs.IElement;
import soliloquy.sprites.specs.ISpriteSet;

public class ElementStub implements IElement {
    @Override
    public double getResistance(ICharacter iCharacter) {
        return 0;
    }

    @Override
    public ISpriteSet spriteSet() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }
}
