package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.IGenericParamsSet;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ITile;
import soliloquy.specs.ruleset.entities.ICharacterType;

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
