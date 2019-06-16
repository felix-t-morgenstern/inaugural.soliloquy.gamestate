package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.gamestate.entities.ICharacterEvent;
import soliloquy.specs.ruleset.entities.ICharacterEventType;

public class CharacterEventArchetype implements ICharacterEvent {
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
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return ICharacterEvent.class.getCanonicalName();
    }
}
