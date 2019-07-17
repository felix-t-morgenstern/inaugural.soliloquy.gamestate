package inaugural.soliloquy.gamestate.archetypes;

import soliloquy.specs.gamestate.entities.CharacterEvent;
import soliloquy.specs.ruleset.entities.CharacterEventType;

public class CharacterEventArchetype implements CharacterEvent {
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
    public void delete() throws IllegalStateException {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getInterfaceName() {
        return CharacterEvent.class.getCanonicalName();
    }
}
