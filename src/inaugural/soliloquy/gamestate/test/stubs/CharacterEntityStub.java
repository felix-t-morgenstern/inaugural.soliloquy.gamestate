package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;

public class CharacterEntityStub implements CharacterEntityOfType<HasId> {
    private final Character CHARACTER;
    private final HasId TYPE;

    private boolean _isDeleted;

    public CharacterEntityStub(Character character, HasId type) {
        CHARACTER = character;
        TYPE = type;
    }

    @Override
    public HasId type() {
        return TYPE;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }
}
