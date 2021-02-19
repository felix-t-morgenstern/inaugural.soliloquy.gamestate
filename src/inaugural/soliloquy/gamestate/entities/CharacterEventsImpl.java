package inaugural.soliloquy.gamestate.entities;

import inaugural.soliloquy.gamestate.archetypes.GameCharacterEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameCharacterEventReadableCollectionArchetype;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CharacterEventsImpl extends HasDeletionInvariants implements CharacterEvents {
    private final Character CHARACTER;
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<String, ArrayList<GameCharacterEvent>> EVENTS;

    private final GameCharacterEvent EVENT_ARCHETYPE = new GameCharacterEventArchetype();
    private final List<GameCharacterEvent> EVENTS_ARCHETYPE =
            new GameCharacterEventReadableCollectionArchetype();

    public CharacterEventsImpl(Character character, ListFactory listFactory,
                               MapFactory mapFactory) {
        CHARACTER = Check.ifNull(character, "character");
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
        EVENTS = new HashMap<>();
    }

    @Override
    public void addEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("addEvent");
        if(!EVENTS.containsKey(trigger)) {
            EVENTS.put(trigger, new ArrayList<>());
        }
        if (!EVENTS.get(trigger).contains(event)) {
            EVENTS.get(trigger).add(event);
        }
    }

    @Override
    public void clearTrigger(String trigger)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("clearTrigger");
        EVENTS.remove(trigger);
    }

    @Override
    public void clearAllTriggers() throws IllegalStateException {
        enforceDeletionInvariants("clearAllTriggers");
        EVENTS.clear();
    }

    @Override
    public List<String> getTriggersForEvent(GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getTriggersForEvent");
        List<String> triggersForEvent = LIST_FACTORY.make("");
        EVENTS.forEach((trigger,events) -> {
            if (events.contains(event)) {
                triggersForEvent.add(trigger);
            }
        });
        return triggersForEvent;
    }

    @Override
    public boolean removeEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("removeEvent");
        ArrayList<GameCharacterEvent> triggerEvents = EVENTS.get(trigger);
        if (triggerEvents == null) {
            return false;
        }
        boolean contained = triggerEvents.remove(event);
        if (triggerEvents.size() == 0) {
            EVENTS.remove(trigger);
        }
        return contained;
    }

    @Override
    public boolean containsEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("containsEvent");
        ArrayList<GameCharacterEvent> triggerEvents = EVENTS.get(trigger);
        return triggerEvents != null && triggerEvents.contains(event);
    }

    @Override
    public void fire(String trigger) throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("fire");
        if (EVENTS.containsKey(trigger)) {
            EVENTS.get(trigger).forEach(event -> event.fire(CHARACTER));
        }
    }

    @Override
    public Map<String, List<GameCharacterEvent>> representation()
            throws IllegalStateException {
        enforceDeletionInvariants("_representation");
        Map<String,List<GameCharacterEvent>> representation =
                MAP_FACTORY.make("", EVENTS_ARCHETYPE);
        EVENTS.forEach((t,e) -> {
            List<GameCharacterEvent> events = LIST_FACTORY.make(EVENT_ARCHETYPE);
            events.addAll(e);
            representation.put(t, events);
        });
        return representation;
    }

    @Override
    public String getInterfaceName() {
        return CharacterEvents.class.getCanonicalName();
    }

    @Override
    protected String className() {
        return "CharacterEventsImpl";
    }

    @Override
    protected String containingClassName() {
        return "Character";
    }

    @Override
    Deletable getContainingObject() {
        return CHARACTER;
    }
}
