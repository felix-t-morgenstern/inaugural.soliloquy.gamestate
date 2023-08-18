package inaugural.soliloquy.gamestate.test.unit.persistence;

import inaugural.soliloquy.gamestate.persistence.CharacterEventsHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.gamestate.entities.CharacterEvents;
import soliloquy.specs.gamestate.entities.CharacterEvents.CharacterEvent;
import soliloquy.specs.gamestate.factories.CharacterEventsFactory;

import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterEventsHandlerTests {
    private final String TRIGGER_1 = randomString();
    private final String TRIGGER_2 = randomString();
    private final String CHARACTER_EVENT_ID_1 = randomString();
    private final String CHARACTER_EVENT_ID_2 = randomString();
    private final String CHARACTER_EVENT_ID_3 = randomString();

    private CharacterEvent mockCharacterEvent1;
    private CharacterEvent mockCharacterEvent2;
    private CharacterEvent mockCharacterEvent3;
    @Mock private CharacterEvents mockCharacterEvents;
    @Mock private CharacterEventsFactory mockCharacterEventsFactory;

    private Function<String, CharacterEvent> mockGetCharacterEvent;

    private final String WRITTEN_DATA = String.format(
            "[{\"trigger\":\"%s\",\"eventIds\":[\"%s\"]},{\"trigger\":\"%s\"," +
                    "\"eventIds\":[\"%s\",\"%s\"]}]",
            TRIGGER_1, CHARACTER_EVENT_ID_1, TRIGGER_2, CHARACTER_EVENT_ID_2, CHARACTER_EVENT_ID_3);

    private TypeHandler<CharacterEvents> characterEventsHandler;

    @Before
    public void setUp() {
        mockCharacterEvent1 = generateMockWithId(CharacterEvent.class, CHARACTER_EVENT_ID_1);
        mockCharacterEvent2 = generateMockWithId(CharacterEvent.class, CHARACTER_EVENT_ID_2);
        mockCharacterEvent3 = generateMockWithId(CharacterEvent.class, CHARACTER_EVENT_ID_3);

        mockGetCharacterEvent =
                generateMockLookupFunction(pairOf(CHARACTER_EVENT_ID_1, mockCharacterEvent1),
                        pairOf(CHARACTER_EVENT_ID_2, mockCharacterEvent2),
                        pairOf(CHARACTER_EVENT_ID_3, mockCharacterEvent3));

        var representation = generateMockMap(
                pairOf(TRIGGER_1, listOf(mockCharacterEvent1)),
                pairOf(TRIGGER_2, listOf(mockCharacterEvent2, mockCharacterEvent3)));
        when(mockCharacterEvents.representation()).thenReturn(representation);

        when(mockCharacterEventsFactory.make(any())).thenReturn(mockCharacterEvents);

        characterEventsHandler =
                new CharacterEventsHandler(mockGetCharacterEvent, mockCharacterEventsFactory);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEventsHandler(null, mockCharacterEventsFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new CharacterEventsHandler(mockGetCharacterEvent, null));
    }

    @Test
    public void testWrite() {
        var output = characterEventsHandler.write(mockCharacterEvents);

        assertEquals(WRITTEN_DATA, output);
        verify(mockCharacterEvents).representation();
        verify(mockCharacterEvent1).id();
        verify(mockCharacterEvent2).id();
        verify(mockCharacterEvent3).id();
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterEventsHandler.write(null));
    }

    @Test
    public void testRead() {
        var output = characterEventsHandler.read(WRITTEN_DATA);

        assertNotNull(output);
        assertSame(mockCharacterEvents, output);
        verify(mockGetCharacterEvent).apply(CHARACTER_EVENT_ID_1);
        verify(mockCharacterEvents).addEvent(eq(arrayOf(TRIGGER_1)), same(mockCharacterEvent1));
        verify(mockGetCharacterEvent).apply(CHARACTER_EVENT_ID_2);
        verify(mockCharacterEvents).addEvent(eq(arrayOf(TRIGGER_2)), same(mockCharacterEvent2));
        verify(mockGetCharacterEvent).apply(CHARACTER_EVENT_ID_3);
        verify(mockCharacterEvents).addEvent(eq(arrayOf(TRIGGER_2)), same(mockCharacterEvent3));
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> characterEventsHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> characterEventsHandler.read(""));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        CharacterEvents.class.getCanonicalName() + ">",
                characterEventsHandler.getInterfaceName());
    }
}
