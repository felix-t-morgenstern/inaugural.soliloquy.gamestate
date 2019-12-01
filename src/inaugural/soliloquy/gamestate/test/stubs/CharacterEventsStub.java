package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

public class CharacterEventsStub implements CharacterEvents {
    private final Character CHARACTER;

    public CharacterEventsStub(Character character) {
        CHARACTER = character;
    }

    @Override
    public void addEvent(String s, GameCharacterEvent gameCharacterEvent) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void clearEvent(String s) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void clearAllEvents() throws IllegalStateException {

    }

    @Override
    public ReadableCollection<String> eventName(GameCharacterEvent gameCharacterEvent) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public boolean removeEvent(String s, GameCharacterEvent gameCharacterEvent) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean containsEvent(String s, GameCharacterEvent gameCharacterEvent) throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public void fire(String s) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public ReadableMap<String, ReadableCollection<GameCharacterEvent>> representation() throws IllegalStateException {
        return null;
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
        return null;
    }
}
