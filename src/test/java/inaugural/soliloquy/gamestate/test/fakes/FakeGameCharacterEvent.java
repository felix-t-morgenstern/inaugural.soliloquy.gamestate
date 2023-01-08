package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

public class FakeGameCharacterEvent implements GameCharacterEvent {
    private final String ID;

    public Character _characterFired;

    public FakeGameCharacterEvent(String id) {
        ID = id;
    }

    @Override
    public Boolean fire(Character character) throws IllegalArgumentException {
        _characterFired = character;
        return null;
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
