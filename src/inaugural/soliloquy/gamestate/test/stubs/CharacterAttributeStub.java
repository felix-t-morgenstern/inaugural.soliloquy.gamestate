package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.gamestate.specs.ICharacter;
import soliloquy.gamestate.specs.ICharacterAttribute;
import soliloquy.ruleset.gameentities.specs.IAttribute;

public class CharacterAttributeStub implements ICharacterAttribute {
    public boolean _isDeleted;

    @Override
    public IAttribute attribute() {
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
