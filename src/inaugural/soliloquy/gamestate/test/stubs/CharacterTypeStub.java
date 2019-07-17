package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.entities.CharacterType;

public class CharacterTypeStub implements CharacterType {
    @Override
    public Character generate(Tile tile, GenericParamsSet genericParamsSet) throws IllegalArgumentException {
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
