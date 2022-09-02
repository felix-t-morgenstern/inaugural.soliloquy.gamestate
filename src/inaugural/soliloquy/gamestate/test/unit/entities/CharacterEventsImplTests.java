package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import inaugural.soliloquy.gamestate.test.fakes.FakeCharacter;
import inaugural.soliloquy.gamestate.test.fakes.FakeGameCharacterEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEventsImplTests {
    private final Character CHARACTER = new FakeCharacter();

    private CharacterEvents _characterEvents;

    @BeforeEach
    void setUp() {
        _characterEvents = new CharacterEventsImpl(CHARACTER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterEventsImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEvents.class.getCanonicalName(),
                _characterEvents.getInterfaceName());
    }

    @Test
    void testAddEventAndRepresentation() {
        String event1Id = "event1Id";
        String event2Id = "event2Id";
        String event3Id = "event3Id";
        String event4Id = "event4Id";

        GameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);
        GameCharacterEvent event2 = new FakeGameCharacterEvent(event2Id);
        GameCharacterEvent event3 = new FakeGameCharacterEvent(event3Id);
        GameCharacterEvent event4 = new FakeGameCharacterEvent(event4Id);

        String trigger1 = "trigger1";
        String trigger2 = "trigger2";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger1, event2);
        _characterEvents.addEvent(trigger1, event3);
        _characterEvents.addEvent(trigger2, event4);

        Map<String, List<GameCharacterEvent>> representation = _characterEvents.representation();

        assertNotNull(representation);
        assertEquals(2, representation.size());
        assertEquals(3, representation.get(trigger1).size());
        assertEquals(event1Id, representation.get(trigger1).get(0).id());
        assertEquals(event2Id, representation.get(trigger1).get(1).id());
        assertEquals(event3Id, representation.get(trigger1).get(2).id());
        assertEquals(1, representation.get(trigger2).size());
        assertEquals(event4Id, representation.get(trigger2).get(0).id());
    }

    @Test
    void testAddEventTwice() {
        String event1Id = "event1Id";

        GameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);

        String trigger1 = "trigger1";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger1, event1);

        Map<String, List<GameCharacterEvent>> representation = _characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger1).size());
        assertEquals(event1Id, representation.get(trigger1).get(0).id());
    }

    @Test
    void testRemoveEvent() {
        String event1Id = "event1Id";
        String event2Id = "event2Id";
        String event3Id = "event3Id";

        GameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);
        GameCharacterEvent event2 = new FakeGameCharacterEvent(event2Id);
        GameCharacterEvent event3 = new FakeGameCharacterEvent(event3Id);

        String trigger1 = "trigger1";
        String trigger2 = "trigger2";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger2, event2);
        _characterEvents.addEvent(trigger1, event3);

        assertFalse(_characterEvents.removeEvent(trigger1, event2));
        assertFalse(_characterEvents.removeEvent(trigger2, event1));
        assertTrue(_characterEvents.removeEvent(trigger2, event2));
        assertFalse(_characterEvents.removeEvent(trigger2, event2));
        assertTrue(_characterEvents.removeEvent(trigger1, event3));
        assertFalse(_characterEvents.removeEvent(trigger1, event3));

        Map<String, List<GameCharacterEvent>> representation = _characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger1).size());
        assertEquals(event1Id, representation.get(trigger1).get(0).id());
    }

    @Test
    void testContainsEvent() {
        String event1Id = "event1Id";
        String event2Id = "event2Id";
        String event3Id = "event3Id";

        GameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);
        GameCharacterEvent event2 = new FakeGameCharacterEvent(event2Id);
        GameCharacterEvent event3 = new FakeGameCharacterEvent(event3Id);

        String trigger1 = "trigger1";
        String trigger2 = "trigger2";
        String trigger3 = "trigger3";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger2, event2);

        assertTrue(_characterEvents.containsEvent(trigger1, event1));
        assertFalse(_characterEvents.containsEvent(trigger1, event2));
        assertFalse(_characterEvents.containsEvent(trigger2, event1));
        assertTrue(_characterEvents.containsEvent(trigger2, event2));
        assertFalse(_characterEvents.containsEvent(trigger1, event3));
        assertFalse(_characterEvents.containsEvent(trigger3, event1));
    }

    @Test
    void testClearTrigger() {
        String event1Id = "event1Id";
        String event2Id = "event2Id";
        String event3Id = "event3Id";

        GameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);
        GameCharacterEvent event2 = new FakeGameCharacterEvent(event2Id);
        GameCharacterEvent event3 = new FakeGameCharacterEvent(event3Id);

        String trigger1 = "trigger1";
        String trigger2 = "trigger2";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger2, event2);
        _characterEvents.addEvent(trigger1, event3);

        _characterEvents.clearTrigger(trigger1);

        Map<String, List<GameCharacterEvent>> representation = _characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger2).size());
        assertEquals(event2Id, representation.get(trigger2).get(0).id());
    }

    @Test
    void testClearAllTriggers() {
        String event1Id = "event1Id";
        String event2Id = "event2Id";
        String event3Id = "event3Id";

        GameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);
        GameCharacterEvent event2 = new FakeGameCharacterEvent(event2Id);
        GameCharacterEvent event3 = new FakeGameCharacterEvent(event3Id);

        String trigger1 = "trigger1";
        String trigger2 = "trigger2";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger2, event2);
        _characterEvents.addEvent(trigger1, event3);

        _characterEvents.clearAllTriggers();

        Map<String, List<GameCharacterEvent>> representation = _characterEvents.representation();

        assertNotNull(representation);
        assertEquals(0, representation.size());
    }

    @Test
    void testGetTriggersForEvent() {
        String event1Id = "event1Id";
        String event2Id = "event2Id";
        String event3Id = "event3Id";

        GameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);
        GameCharacterEvent event2 = new FakeGameCharacterEvent(event2Id);
        GameCharacterEvent event3 = new FakeGameCharacterEvent(event3Id);

        String trigger1 = "trigger1";
        String trigger2 = "trigger2";
        String trigger3 = "trigger3";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger2, event2);
        _characterEvents.addEvent(trigger3, event1);

        List<String> event1Triggers = _characterEvents.getTriggersForEvent(event1);
        List<String> event2Triggers = _characterEvents.getTriggersForEvent(event2);
        List<String> event3Triggers = _characterEvents.getTriggersForEvent(event3);

        assertEquals(2, event1Triggers.size());
        assertTrue(event1Triggers.contains(trigger1));
        assertTrue(event1Triggers.contains(trigger3));

        assertEquals(1, event2Triggers.size());
        assertTrue(event2Triggers.contains(trigger2));

        assertEquals(0, event3Triggers.size());
    }

    @Test
    void testFire() {
        String event1Id = "event1Id";
        String event2Id = "event2Id";
        String event3Id = "event3Id";

        FakeGameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);
        FakeGameCharacterEvent event2 = new FakeGameCharacterEvent(event2Id);
        FakeGameCharacterEvent event3 = new FakeGameCharacterEvent(event3Id);

        String trigger1 = "trigger1";
        String trigger2 = "trigger2";

        _characterEvents.addEvent(trigger1, event1);
        _characterEvents.addEvent(trigger2, event2);
        _characterEvents.addEvent(trigger1, event3);

        _characterEvents.fire(trigger1);

        assertEquals(CHARACTER, event1._characterFired);
        assertNull(event2._characterFired);
        assertEquals(CHARACTER, event3._characterFired);
    }

    @Test
    void testDelete() {
        _characterEvents.delete();

        assertTrue(_characterEvents.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        String event1Id = "event1Id";

        FakeGameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);

        String trigger1 = "trigger1";

        _characterEvents.delete();

        assertThrows(EntityDeletedException.class,
                () -> _characterEvents.addEvent(trigger1, event1));
        assertThrows(EntityDeletedException.class,
                () -> _characterEvents.clearTrigger(trigger1));
        assertThrows(EntityDeletedException.class, () -> _characterEvents.clearAllTriggers());
    }

    @Test
    void testCharacterDeletedInvariant() {
        String event1Id = "event1Id";

        FakeGameCharacterEvent event1 = new FakeGameCharacterEvent(event1Id);

        String trigger1 = "trigger1";

        CHARACTER.delete();

        assertThrows(IllegalStateException.class,
                () -> _characterEvents.addEvent(trigger1, event1));
        assertThrows(IllegalStateException.class,
                () -> _characterEvents.clearTrigger(trigger1));
        assertThrows(IllegalStateException.class, () -> _characterEvents.clearAllTriggers());
    }
}
