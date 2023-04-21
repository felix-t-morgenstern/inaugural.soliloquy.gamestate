package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class CharacterEventsImpl extends HasDeletionInvariants implements CharacterEvents {
    private final Character CHARACTER;

    private Map<String, List<CharacterEvent>> eventsPerTrigger;

    public CharacterEventsImpl(Character character) {
        CHARACTER = Check.ifNull(character, "character");
        eventsPerTrigger = mapOf();
    }

    @Override
    public void addEvent(String[] triggers, CharacterEvent event)
            throws IllegalArgumentException, EntityDeletedException {
        enforceDeletionInvariants();
        Check.ifNull(triggers, "triggers");
        Check.ifNull(event, "event");
        for (var trigger : triggers) {
            Check.ifNullOrEmpty(trigger, "trigger within addEvent");
            if (!eventsPerTrigger.containsKey(trigger)) {
                eventsPerTrigger.put(trigger, listOf());
            }
            if (!eventsPerTrigger.get(trigger).contains(event)) {
                eventsPerTrigger.get(trigger).add(event);
            }
        }
    }

    @Override
    public boolean removeEvent(CharacterEvent event)
            throws IllegalArgumentException, EntityDeletedException {
        enforceDeletionInvariants();
        Check.ifNull(event, "event");

        var eventWasPresent = false;
        List<String> triggersToRemove = listOf();

        for(var entry : eventsPerTrigger.entrySet()) {
            if (entry.getValue().remove(event)) {
                eventWasPresent = true;
                if (entry.getValue().isEmpty()) {
                    triggersToRemove.add(entry.getKey());
                }
            }
        }

        triggersToRemove.forEach(eventsPerTrigger::remove);

        return eventWasPresent;
    }

    @Override
    public void clearTrigger(String trigger)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants();
        Check.ifNullOrEmpty(trigger, "trigger");
        eventsPerTrigger.remove(trigger);
    }

    @Override
    public void clearAllEvents() throws EntityDeletedException {
        enforceDeletionInvariants();
        eventsPerTrigger.clear();
    }

    @Override
    public void copyAllTriggers(CharacterEvents characterEvents) throws IllegalArgumentException {
        enforceDeletionInvariants();
        Check.ifNull(characterEvents, "characterEvents");
        eventsPerTrigger = mapOf(characterEvents.representation());
    }

    @Override
    public Map<String, List<CharacterEvent>> representation()
            throws IllegalStateException {
        enforceDeletionInvariants();
        Map<String, List<CharacterEvent>> representation = mapOf();
        eventsPerTrigger.forEach((t, e) -> representation.put(t, listOf(e)));
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
