package inaugural.soliloquy.gamestate.test.fakes;

import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeCharacterEvents implements CharacterEvents {
    private final HashMap<String, ArrayList<GameCharacterEvent>> EVENTS = new HashMap<>();

    @Override
    public void addEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
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
    public List<String> getTriggersForEvent(GameCharacterEvent gameCharacterEvent)
            throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public boolean removeEvent(String s, GameCharacterEvent gameCharacterEvent)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean containsEvent(String s, GameCharacterEvent gameCharacterEvent)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public void fire(String s) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public Map<String, List<GameCharacterEvent>> representation() throws IllegalStateException {
        HashMap<String, List<GameCharacterEvent>> representation = new HashMap<>();
        EVENTS.forEach((t, e) -> representation.put(t, new ArrayList<>(e)));
        return representation;
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
