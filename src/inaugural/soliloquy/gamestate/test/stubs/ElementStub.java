package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.IGameZone;
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
    public IGameZone getGameZone() throws IllegalStateException {
        return null;
    }

    @Override
    public IGenericParamsSet params() throws IllegalStateException {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void read(String s, boolean b) throws IllegalArgumentException {

    }

    @Override
    public String write() throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
