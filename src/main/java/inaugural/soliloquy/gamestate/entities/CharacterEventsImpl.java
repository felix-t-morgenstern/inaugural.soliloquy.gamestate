package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterEventsImpl extends HasDeletionInvariants implements CharacterEvents {
    private final Character CHARACTER;

    private HashMap<String, List<GameCharacterEvent>> events;

    public CharacterEventsImpl(Character character) {
        CHARACTER = Check.ifNull(character, "character");
        events = new HashMap<>();
    }

    @Override
    public void addEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        if (!events.containsKey(trigger)) {
            events.put(trigger, new ArrayList<>());
        }
        if (!events.get(trigger).contains(event)) {
            events.get(trigger).add(event);
        }
    }

    @Override
    public void clearTrigger(String trigger)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        events.remove(trigger);
    }

    @Override
    public void clearAllTriggers() throws IllegalStateException {
        enforceDeletionInvariants();
        events.clear();
    }

    @Override
    public void copyAllTriggers(CharacterEvents characterEvents) throws IllegalArgumentException {
        enforceDeletionInvariants();

        events = new HashMap<>(characterEvents.representation());
    }

    @Override
    public List<String> getTriggersForEvent(GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        ArrayList<String> triggersForEvent = new ArrayList<>();
        events.forEach((trigger, events) -> {
            if (events.contains(event)) {
                triggersForEvent.add(trigger);
            }
        });
        return triggersForEvent;
    }

    @Override
    public boolean removeEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        List<GameCharacterEvent> triggerEvents = events.get(trigger);
        if (triggerEvents == null) {
            return false;
        }
        boolean contained = triggerEvents.remove(event);
        if (triggerEvents.size() == 0) {
            events.remove(trigger);
        }
        return contained;
    }

    @Override
    public boolean containsEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        List<GameCharacterEvent> triggerEvents = events.get(trigger);
        return triggerEvents != null && triggerEvents.contains(event);
    }

    @Override
    public void fire(String trigger) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        if (events.containsKey(trigger)) {
            events.get(trigger).forEach(event -> event.fire(CHARACTER));
        }
    }

    @Override
    public Map<String, List<GameCharacterEvent>> representation()
            throws IllegalStateException {
        enforceDeletionInvariants();
        HashMap<String, List<GameCharacterEvent>> representation = new HashMap<>();
        events.forEach((t, e) -> representation.put(t, new ArrayList<>(e)));
        return representation;
    }

    @Override
    public String getInterfaceName() {
        return CharacterEvents.class.getCanonicalName();
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    protected Deletable getContainingObject() {
        return CHARACTER;
    }
}
