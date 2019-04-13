package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ITile;
import soliloquy.ruleset.gameentities.specs.ICharacterType;

public class CharacterTypeStub implements ICharacterType {
    @Override
    public ICharacter generate(ITile iTile, IGenericParamsSet iGenericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return null;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public void setPluralName(String s) throws IllegalArgumentException {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
