package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;
import soliloquy.specs.gamestate.entities.gameevents.GameCharacterEvent;

import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterEventsImplTests {
    private final String event1Id = randomString();
    private final String event2Id = randomString();
    private final String event3Id = randomString();
    private final String event4Id = randomString();

    private final GameCharacterEvent mockEvent1 = generateMockCharacterEvent(event1Id);
    private final GameCharacterEvent mockEvent2 = generateMockCharacterEvent(event2Id);
    private final GameCharacterEvent mockEvent3 = generateMockCharacterEvent(event3Id);
    private final GameCharacterEvent mockEvent4 = generateMockCharacterEvent(event4Id);

    private final String trigger1 = randomString();
    private final String trigger2 = randomString();

    @Mock private VariableCache mockEventData;
    @Mock private Character mockCharacter;

    private CharacterEvents characterEvents;

    @BeforeEach
    void setUp() {
        mockEventData = mock(VariableCache.class);

        mockCharacter = mock(Character.class);

        characterEvents = new CharacterEventsImpl(mockCharacter);
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
        characterEvents.addEvent(arrayOf(trigger1), mockEvent1);
        characterEvents.addEvent(arrayOf(trigger1), mockEvent2);
        characterEvents.addEvent(arrayOf(trigger1), mockEvent3);
        characterEvents.addEvent(arrayOf(trigger2), mockEvent4);

        var representation = characterEvents.representation();

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
        characterEvents.addEvent(arrayOf(trigger1), mockEvent1);
        characterEvents.addEvent(arrayOf(trigger1), mockEvent1);

        var representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger1).size());
        assertEquals(event1Id, representation.get(trigger1).get(0).id());
    }

    @Test
    void testAddEventWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEvents.addEvent(null, mockEvent1));
        assertThrows(IllegalArgumentException.class,
                () -> characterEvents.addEvent(arrayOf((String) null), mockEvent1));
        assertThrows(IllegalArgumentException.class,
                () -> characterEvents.addEvent(arrayOf(trigger1), null));
    }

    @Test
    void testRemoveEvent() {
        characterEvents.addEvent(arrayOf(trigger1), mockEvent1);
        characterEvents.addEvent(arrayOf(trigger2), mockEvent2);
        characterEvents.addEvent(arrayOf(trigger1), mockEvent3);

        assertTrue(characterEvents.removeEvent(mockEvent2));
        assertFalse(characterEvents.removeEvent(mockEvent2));
        assertTrue(characterEvents.removeEvent(mockEvent3));
        assertFalse(characterEvents.removeEvent(mockEvent3));

        var representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger1).size());
        assertSame(mockEvent1, representation.get(trigger1).get(0));

        assertTrue(characterEvents.removeEvent(mockEvent1));
        assertFalse(characterEvents.removeEvent(mockEvent1));

        assertTrue(characterEvents.representation().isEmpty());
    }

    @Test
    void testRemoveEventWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterEvents.removeEvent(null));
    }

    @Test
    void testClearTrigger() {
        characterEvents.addEvent(arrayOf(trigger1), mockEvent1);
        characterEvents.addEvent(arrayOf(trigger2), mockEvent2);
        characterEvents.addEvent(arrayOf(trigger1), mockEvent3);

        characterEvents.clearTrigger(trigger1);

        var representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger2).size());
        assertEquals(event2Id, representation.get(trigger2).get(0).id());
    }

    @Test
    void testClearTriggerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterEvents.clearTrigger(null));
        assertThrows(IllegalArgumentException.class, () -> characterEvents.clearTrigger(""));
    }

    @Test
    void testClearAllEvents() {
        characterEvents.addEvent(arrayOf(trigger1), mockEvent1);
        characterEvents.addEvent(arrayOf(trigger2), mockEvent2);
        characterEvents.addEvent(arrayOf(trigger1), mockEvent3);

        characterEvents.clearAllEvents();

        var representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(0, representation.size());
    }

    @Test
    void testFire() {
        characterEvents.addEvent(arrayOf(trigger1), mockEvent1);
        characterEvents.addEvent(arrayOf(trigger2), mockEvent2);
        characterEvents.addEvent(arrayOf(trigger1), mockEvent3);

        characterEvents.fire(trigger1, mockEventData);

        verify(mockEvent1, times(1)).fire(mockCharacter, mockEventData);
        verify(mockEvent2, never()).fire(mockCharacter, mockEventData);
        verify(mockEvent3, times(1)).fire(mockCharacter, mockEventData);
    }

    @Test
    void testFireWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEvents.fire(null, mockEventData));
        assertThrows(IllegalArgumentException.class, () -> characterEvents.fire("", mockEventData));
        assertThrows(IllegalArgumentException.class, () -> characterEvents.fire(trigger1, null));
    }

    @Test
    void testCopyAllTriggers() {
        CharacterEvents copyFrom = mock(CharacterEvents.class);
        Map<String, List<GameCharacterEvent>> toCopy =
                mapOf(Pair.of(trigger1, listOf(mockEvent1, mockEvent3)), Pair.of(trigger2, listOf(
                        mockEvent2)));
        when(copyFrom.representation()).thenReturn(toCopy);

        characterEvents.copyAllTriggers(copyFrom);

        assertEquals(toCopy, characterEvents.representation());

        // This ensures deep copying
        toCopy.clear();

        assertNotEquals(toCopy, characterEvents.representation());
    }

    @Test
    void testCopyAllTriggersWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterEvents.copyAllTriggers(null));
    }

    @Test
    void testDelete() {
        characterEvents.delete();

        assertTrue(characterEvents.isDeleted());
    }

    @Test
    void testDeletedInvariant() {
        characterEvents.delete();

        assertThrows(EntityDeletedException.class, () -> characterEvents.addEvent(arrayOf(trigger1),
                mockEvent1));
        assertThrows(EntityDeletedException.class, () -> characterEvents.removeEvent(mockEvent1));
        assertThrows(EntityDeletedException.class, () -> characterEvents.clearTrigger(trigger1));
        assertThrows(EntityDeletedException.class, () -> characterEvents.clearAllEvents());
        assertThrows(EntityDeletedException.class,
                () -> characterEvents.copyAllTriggers(characterEvents));
        assertThrows(EntityDeletedException.class,
                () -> characterEvents.fire(trigger1, mockEventData));
        assertThrows(EntityDeletedException.class, () -> characterEvents.representation());
    }

    @Test
    void testCharacterDeletedInvariant() {
        when(mockCharacter.isDeleted()).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> characterEvents.addEvent(arrayOf(trigger1),
                mockEvent1));
        assertThrows(IllegalStateException.class, () -> characterEvents.removeEvent(mockEvent1));
        assertThrows(IllegalStateException.class, () -> characterEvents.clearTrigger(trigger1));
        assertThrows(IllegalStateException.class, () -> characterEvents.clearAllEvents());
        assertThrows(IllegalStateException.class,
                () -> characterEvents.copyAllTriggers(characterEvents));
        assertThrows(IllegalStateException.class,
                () -> characterEvents.fire(trigger1, mockEventData));
        assertThrows(IllegalStateException.class, () -> characterEvents.representation());
    }

    private static GameCharacterEvent generateMockCharacterEvent(String eventId) {
        GameCharacterEvent mockCharacterEvent = mock(GameCharacterEvent.class);
        when(mockCharacterEvent.id()).thenReturn(eventId);
        return mockCharacterEvent;
    }
}
