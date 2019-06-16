package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.ruleset.entities.IElement;
import soliloquy.specs.sprites.entities.ISpriteSet;

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
