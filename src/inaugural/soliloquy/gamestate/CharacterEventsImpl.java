package inaugural.soliloquy.gamestate;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CharacterEventsImpl implements CharacterEvents {
    private final Character CHARACTER;
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final HashMap<String, ArrayList<GameCharacterEvent>> EVENTS;

    public CharacterEventsImpl(Character character, CollectionFactory collectionFactory,
                               MapFactory mapFactory) {
        CHARACTER = character;
        COLLECTION_FACTORY = collectionFactory;
        MAP_FACTORY = mapFactory;
        EVENTS = new HashMap<>();
    }

    @Override
    public void addEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void clearEvent(String trigger) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void clearAllEvents() throws IllegalStateException {

    }

    @Override
    public ReadableCollection<String> eventName(GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public boolean removeEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public boolean containsEvent(String trigger, GameCharacterEvent event)
            throws IllegalArgumentException, IllegalStateException {
        return false;
    }

    @Override
    public void fire(String trigger) throws IllegalArgumentException, IllegalStateException {

    }

    @Override
    public ReadableMap<String, ReadableCollection<GameCharacterEvent>> representation()
            throws IllegalStateException {
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
