package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.Tile;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;
import soliloquy.specs.ruleset.entities.CharacterType;

public class CharacterTypeStub implements CharacterType {
    private final String ID;

    public CharacterTypeStub() {
        ID = null;
    }

    public CharacterTypeStub(String id) {
        ID = id;
    }

    @Override
    public Character generate(Tile tile, GenericParamsSet genericParamsSet) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Map<String, Collection<GameCharacterEvent>> events() {
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
