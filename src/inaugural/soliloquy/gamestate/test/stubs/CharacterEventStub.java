package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.ICharacterEvent;
import soliloquy.specs.ruleset.entities.ICharacterEventType;

public class CharacterEventStub implements ICharacterEvent {
    public boolean _isDeleted;

    @Override
    public ICharacterEventType characterEventType() {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int i) {

    }

    @Override
    public boolean getBlocksSubsequentEvents() {
        return false;
    }

    @Override
    public void setBlocksSubsequentEvents(boolean b) {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public void delete() throws IllegalStateException {
        _isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return _isDeleted;
    }
}
