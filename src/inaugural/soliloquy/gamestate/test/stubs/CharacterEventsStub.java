package inaugural.soliloquy.gamestate.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CharacterEventsStub implements CharacterEvents {
    private final Character CHARACTER;
    private final HashMap<String, ArrayList<GameCharacterEvent>> EVENTS = new HashMap<>();

    public CharacterEventsStub(Character character) {
        CHARACTER = character;
    }

    @Override
    public void addEvent(String trigger, GameCharacterEvent event) throws IllegalArgumentException, IllegalStateException {
        if (!EVENTS.containsKey(trigger)) {
            EVENTS.put(trigger, new ArrayList<>());
        }
        EVENTS.get(trigger).add(event);
    }

    @Override
    public void clearTrigger(String s) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void clearAllTriggers() throws IllegalStateException {

    }

    @Override
    public ReadableCollection<String> getTriggersForEvent(GameCharacterEvent gameCharacterEvent) throws IllegalArgumentException, IllegalStateException {
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
        Map<String,ReadableCollection<GameCharacterEvent>> representation = new MapStub<>();
        EVENTS.forEach((t,e) -> {
            Collection<GameCharacterEvent> events = new CollectionStub<>();
            e.forEach(events::add);
            representation.put(t,events);
        });
        return representation.readOnlyRepresentation();
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
