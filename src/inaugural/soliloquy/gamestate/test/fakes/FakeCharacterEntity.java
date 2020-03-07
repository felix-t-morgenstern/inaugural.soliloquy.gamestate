package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEntityOfType;

public class FakeCharacterEntity implements CharacterEntityOfType<HasId> {
    private final Character CHARACTER;
    private final HasId TYPE;

    private boolean _isDeleted;

    public FakeCharacterEntity(Character character, HasId type) {
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
        return CharacterEntityOfType.class.getCanonicalName() + "<" +
                HasId.class.getCanonicalName() + ">";
    }

    @Override
    public VariableCache data() throws IllegalStateException {
        return null;
    }
}
