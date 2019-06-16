package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.gamestate.entities.ICharacter;
import soliloquy.specs.gamestate.entities.ICharacterAttribute;
import soliloquy.specs.ruleset.entities.IAttributeType;

public class CharacterAttributeStub implements ICharacterAttribute {
    public boolean _isDeleted;

    @Override
    public IAttributeType attribute() {
        return null;
    }

    @Override
    public ICharacter character() throws IllegalStateException {
        return null;
    }

    @Override
    public int totalValue() throws IllegalStateException {
        return 0;
    }

    @Override
    public IMap<String, Integer> modifiers() throws IllegalStateException {
        return null;
    }

    @Override
    public void calculateValue() throws IllegalStateException {

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
}
