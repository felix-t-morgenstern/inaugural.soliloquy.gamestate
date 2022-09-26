package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.ruleset.entities.CharacterType;

public class FakeCharacterType implements CharacterType {
    private final String ID;

    public FakeCharacterType() {
        ID = null;
    }

    public FakeCharacterType(String id) {
        ID = id;
    }

    @Override
    public Character generate(Tile tile, VariableCache variableCache)
            throws IllegalArgumentException {
        return null;
    }

    @Override
    public VariableCache defaultData() {
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

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
