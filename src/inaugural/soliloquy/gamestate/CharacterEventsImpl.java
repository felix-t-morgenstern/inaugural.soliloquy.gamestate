package inaugural.soliloquy.gamestate;

import inaugural.soliloquy.gamestate.archetypes.GameCharacterEventArchetype;
import inaugural.soliloquy.gamestate.archetypes.GameCharacterEventReadableCollectionArchetype;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.Deletable;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CharacterEventsImpl extends HasDeletionInvariants implements CharacterEvents {
    private final Character CHARACTER;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<String, ArrayList<GameCharacterEvent>> EVENTS;

    private final GameCharacterEvent EVENT_ARCHETYPE = new GameCharacterEventArchetype();
    private final ReadableCollection<GameCharacterEvent> EVENTS_ARCHETYPE =
            new GameCharacterEventReadableCollectionArchetype();

    @SuppressWarnings("ConstantConditions")
    public CharacterEventsImpl(Character character, CollectionFactory collectionFactory,
                               MapFactory mapFactory) {
        if (character == null) {
            throw new IllegalArgumentException("CharacterEventsImpl: character cannot be null");
        }
        CHARACTER = character;
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "CharacterEventsImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
        if (mapFactory == null) {
            throw new IllegalArgumentException("CharacterEventsImpl: mapFactory cannot be null");
        }
        MAP_FACTORY = mapFactory;
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
    public ReadableCollection<String> getTriggersForEvent(GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        enforceDeletionInvariants("getTriggersForEvent");
        Collection<String> triggersForEvent = COLLECTION_FACTORY.make("");
        EVENTS.forEach((trigger,events) -> {
            if (events.contains(event)) {
                triggersForEvent.add(trigger);
            }
        });
        return triggersForEvent.representation();
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
    public ReadableMap<String, ReadableCollection<GameCharacterEvent>> representation()
            throws IllegalStateException {
        enforceDeletionInvariants("_representation");
        Map<String,ReadableCollection<GameCharacterEvent>> representation =
                MAP_FACTORY.make("", EVENTS_ARCHETYPE);
        EVENTS.forEach((t,e) -> {
            Collection<GameCharacterEvent> events = COLLECTION_FACTORY.make(EVENT_ARCHETYPE);
            e.forEach(events::add);
            representation.put(t, events.representation());
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
