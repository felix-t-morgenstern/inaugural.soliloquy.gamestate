package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.gamestate.entities.CharacterEvent;
import soliloquy.specs.ruleset.entities.CharacterEventType;

public class CharacterEventStub implements CharacterEvent {
    public boolean _isDeleted;

    @Override
    public CharacterEventType characterEventType() {
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
