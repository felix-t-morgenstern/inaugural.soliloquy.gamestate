package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterEventsImplTests {
    private final String event1Id = randomString();
    private final String event2Id = randomString();
    private final String event3Id = randomString();
    private final String event4Id = randomString();

    private final GameCharacterEvent event1 = generateMockCharacterEvent(event1Id);
    private final GameCharacterEvent event2 = generateMockCharacterEvent(event2Id);
    private final GameCharacterEvent event3 = generateMockCharacterEvent(event3Id);
    private final GameCharacterEvent event4 = generateMockCharacterEvent(event4Id);

    private final String trigger1 = randomString();
    private final String trigger2 = randomString();
    private final String trigger3 = randomString();

    @Mock private Character character;

    private CharacterEvents characterEvents;

    @BeforeEach
    void setUp() {
        character = mock(Character.class);

        characterEvents = new CharacterEventsImpl(character);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterEventsImpl(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CharacterEvents.class.getCanonicalName(),
                characterEvents.getInterfaceName());
    }

    @Test
    void testAddEventAndRepresentation() {
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger1, event2);
        characterEvents.addEvent(trigger1, event3);
        characterEvents.addEvent(trigger2, event4);

        Map<String, List<GameCharacterEvent>> representation = characterEvents.representation();

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
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger1, event1);

        Map<String, List<GameCharacterEvent>> representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger1).size());
        assertEquals(event1Id, representation.get(trigger1).get(0).id());
    }

    @Test
    void testRemoveEvent() {
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger2, event2);
        characterEvents.addEvent(trigger1, event3);

        assertFalse(characterEvents.removeEvent(trigger1, event2));
        assertFalse(characterEvents.removeEvent(trigger2, event1));
        assertTrue(characterEvents.removeEvent(trigger2, event2));
        assertFalse(characterEvents.removeEvent(trigger2, event2));
        assertTrue(characterEvents.removeEvent(trigger1, event3));
        assertFalse(characterEvents.removeEvent(trigger1, event3));

        Map<String, List<GameCharacterEvent>> representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger1).size());
        assertEquals(event1Id, representation.get(trigger1).get(0).id());
    }

    @Test
    void testContainsEvent() {
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger2, event2);

        assertTrue(characterEvents.containsEvent(trigger1, event1));
        assertFalse(characterEvents.containsEvent(trigger1, event2));
        assertFalse(characterEvents.containsEvent(trigger2, event1));
        assertTrue(characterEvents.containsEvent(trigger2, event2));
        assertFalse(characterEvents.containsEvent(trigger1, event3));
        assertFalse(characterEvents.containsEvent(trigger3, event1));
    }

    @Test
    void testClearTrigger() {
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger2, event2);
        characterEvents.addEvent(trigger1, event3);

        characterEvents.clearTrigger(trigger1);

        Map<String, List<GameCharacterEvent>> representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger2).size());
        assertEquals(event2Id, representation.get(trigger2).get(0).id());
    }

    @Test
    void testClearAllTriggers() {
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger2, event2);
        characterEvents.addEvent(trigger1, event3);

        characterEvents.clearAllTriggers();

        Map<String, List<GameCharacterEvent>> representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(0, representation.size());
    }

    @Test
    void testGetTriggersForEvent() {
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger2, event2);
        characterEvents.addEvent(trigger3, event1);

        List<String> event1Triggers = characterEvents.getTriggersForEvent(event1);
        List<String> event2Triggers = characterEvents.getTriggersForEvent(event2);
        List<String> event3Triggers = characterEvents.getTriggersForEvent(event3);

        assertEquals(2, event1Triggers.size());
        assertTrue(event1Triggers.contains(trigger1));
        assertTrue(event1Triggers.contains(trigger3));

        assertEquals(1, event2Triggers.size());
        assertTrue(event2Triggers.contains(trigger2));

        assertEquals(0, event3Triggers.size());
    }

    @Test
    void testFire() {
        characterEvents.addEvent(trigger1, event1);
        characterEvents.addEvent(trigger2, event2);
        characterEvents.addEvent(trigger1, event3);

        characterEvents.fire(trigger1);

        verify(event1, times(1)).fire(character);
        verify(event2, never()).fire(character);
        verify(event3, times(1)).fire(character);
    }

    @Test
    void testCopyAllTriggers() {
        CharacterEvents copyFrom = mock(CharacterEvents.class);
        Map<String, List<GameCharacterEvent>> toCopy =
                mapOf(Pair.of(trigger1, listOf(event1, event3)), Pair.of(trigger2, listOf(event2)));
        when(copyFrom.representation()).thenReturn(toCopy);

        characterEvents.copyAllTriggers(copyFrom);

        assertEquals(toCopy, characterEvents.representation());

        // This ensures deep copying
        toCopy.clear();

        assertNotEquals(toCopy, characterEvents.representation());
    }

    @Test
    void testDelete() {
        characterEvents.delete();

        assertTrue(characterEvents.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        characterEvents.delete();

        assertThrows(EntityDeletedException.class,
                () -> characterEvents.addEvent(trigger1, event1));
        assertThrows(EntityDeletedException.class,
                () -> characterEvents.clearTrigger(trigger1));
        assertThrows(EntityDeletedException.class, () -> characterEvents.clearAllTriggers());
        assertThrows(EntityDeletedException.class,
                () -> characterEvents.copyAllTriggers(characterEvents));
    }

    @Test
    void testCharacterDeletedInvariant() {
        when(character.isDeleted()).thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> characterEvents.addEvent(trigger1, event1));
        assertThrows(IllegalStateException.class,
                () -> characterEvents.clearTrigger(trigger1));
        assertThrows(IllegalStateException.class, () -> characterEvents.clearAllTriggers());
        assertThrows(IllegalStateException.class,
                () -> characterEvents.copyAllTriggers(characterEvents));
    }

    private static GameCharacterEvent generateMockCharacterEvent(String eventId) {
        GameCharacterEvent mockCharacterEvent = mock(GameCharacterEvent.class);
        when(mockCharacterEvent.id()).thenReturn(eventId);
        return mockCharacterEvent;
    }
}
