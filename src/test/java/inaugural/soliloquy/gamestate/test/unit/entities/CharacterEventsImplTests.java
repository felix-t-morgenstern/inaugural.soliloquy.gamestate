package inaugural.soliloquy.gamestate.test.unit.entities;

import inaugural.soliloquy.gamestate.entities.CharacterEventsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.gamestate.entities.Character;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.CharacterEvents.CharacterEvent;
import soliloquy.specs.gamestate.entities.exceptions.EntityDeletedException;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockWithId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class CharacterEventsImplTests {
    private final String event1Id = randomString();
    private final String event2Id = randomString();
    private final String event3Id = randomString();
    private final String event4Id = randomString();

    private final CharacterEvent mockEvent1 = generateMockWithId(CharacterEvent.class, event1Id);
    private final CharacterEvent mockEvent2 = generateMockWithId(CharacterEvent.class, event2Id);
    private final CharacterEvent mockEvent3 = generateMockWithId(CharacterEvent.class, event3Id);
    private final CharacterEvent mockEvent4 = generateMockWithId(CharacterEvent.class, event4Id);

    private final String trigger1 = randomString();
    private final String trigger2 = randomString();

    @Mock private Character mockCharacter;

    private CharacterEvents characterEvents;

    @BeforeEach
    public void setUp() {
        characterEvents = new CharacterEventsImpl(mockCharacter);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new CharacterEventsImpl(null));
    }

    @Test
    public void testAddEventAndRepresentation() {
        characterEvents.addEvent(mockEvent1, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent2, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent3, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent4, arrayOf(trigger2));

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
    public void testEventsForTrigger() {
        assertArrayEquals(new CharacterEvents[]{}, characterEvents.eventsForTrigger(trigger1));

        characterEvents.addEvent(mockEvent1, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent2, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent3, arrayOf(trigger1));

        assertArrayEquals(arrayOf(mockEvent1, mockEvent2, mockEvent3),
                characterEvents.eventsForTrigger(trigger1));
    }

    @Test
    public void testEventsForTriggerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> characterEvents.eventsForTrigger(null));
        assertThrows(IllegalArgumentException.class, () -> characterEvents.eventsForTrigger(""));
    }

    @Test
    public void testAddEventTwice() {
        characterEvents.addEvent(mockEvent1, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent1, arrayOf(trigger1));

        var representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger1).size());
        assertEquals(event1Id, representation.get(trigger1).get(0).id());
    }

    @Test
    public void testAddEventWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> characterEvents.addEvent(null, arrayOf(trigger1)));
        assertThrows(IllegalArgumentException.class,
                () -> characterEvents.addEvent(mockEvent1, (String) null));
        assertThrows(IllegalArgumentException.class,
                () -> characterEvents.addEvent(mockEvent1, (String[]) null));
    }

    @Test
    public void testRemoveEvent() {
        characterEvents.addEvent(mockEvent1, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent2, arrayOf(trigger2));
        characterEvents.addEvent(mockEvent3, arrayOf(trigger1));

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
    public void testRemoveEventWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> characterEvents.removeEvent(null));
    }

    @Test
    public void testClearTrigger() {
        characterEvents.addEvent(mockEvent1, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent2, arrayOf(trigger2));
        characterEvents.addEvent(mockEvent3, arrayOf(trigger1));

        characterEvents.clearTrigger(trigger1);

        var representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(1, representation.size());
        assertEquals(1, representation.get(trigger2).size());
        assertEquals(event2Id, representation.get(trigger2).get(0).id());
    }

    @Test
    public void testClearTriggerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> characterEvents.clearTrigger(null));
        assertThrows(IllegalArgumentException.class, () -> characterEvents.clearTrigger(""));
    }

    @Test
    public void testClearAllEvents() {
        characterEvents.addEvent(mockEvent1, arrayOf(trigger1));
        characterEvents.addEvent(mockEvent2, arrayOf(trigger2));
        characterEvents.addEvent(mockEvent3, arrayOf(trigger1));

        characterEvents.clearAllEvents();

        var representation = characterEvents.representation();

        assertNotNull(representation);
        assertEquals(0, representation.size());
    }

    @Test
    public void testCopyAllTriggers() {
        var copyFrom = mock(CharacterEvents.class);
        var toCopy = mapOf(pairOf(trigger1, listOf(mockEvent1, mockEvent3)),
                pairOf(trigger2, listOf(mockEvent2)));
        when(copyFrom.representation()).thenReturn(toCopy);

        characterEvents.copyAllTriggers(copyFrom);

        assertEquals(toCopy, characterEvents.representation());

        // This ensures deep copying
        toCopy.clear();

        assertNotEquals(toCopy, characterEvents.representation());
    }

    @Test
    public void testCopyAllTriggersWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> characterEvents.copyAllTriggers(null));
    }

    @Test
    public void testDelete() {
        characterEvents.delete();

        assertTrue(characterEvents.isDeleted());
    }

    @Test
    public void testDeletedInvariant() {
        characterEvents.delete();

        assertThrows(EntityDeletedException.class,
                () -> characterEvents.addEvent(mockEvent1, arrayOf(trigger1)));
        assertThrows(EntityDeletedException.class, () -> characterEvents.removeEvent(mockEvent1));
        assertThrows(EntityDeletedException.class, () -> characterEvents.clearTrigger(trigger1));
        assertThrows(EntityDeletedException.class, () -> characterEvents.clearAllEvents());
        assertThrows(EntityDeletedException.class,
                () -> characterEvents.copyAllTriggers(characterEvents));
        assertThrows(EntityDeletedException.class, () -> characterEvents.representation());
    }

    @Test
    public void testCharacterDeletedInvariant() {
        when(mockCharacter.isDeleted()).thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> characterEvents.addEvent(mockEvent1, arrayOf(trigger1)));
        assertThrows(IllegalStateException.class, () -> characterEvents.removeEvent(mockEvent1));
        assertThrows(IllegalStateException.class, () -> characterEvents.clearTrigger(trigger1));
        assertThrows(IllegalStateException.class, () -> characterEvents.clearAllEvents());
        assertThrows(IllegalStateException.class,
                () -> characterEvents.copyAllTriggers(characterEvents));
        assertThrows(IllegalStateException.class, () -> characterEvents.representation());
    }
}
